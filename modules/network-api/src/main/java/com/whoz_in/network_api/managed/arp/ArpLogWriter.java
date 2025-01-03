package com.whoz_in.network_api.managed.arp;


import com.whoz_in.domain.network_log.ManagedLog;
import com.whoz_in.domain.network_log.ManagedLogRepository;
import com.whoz_in.network_api.config.NetworkConfig;
import com.whoz_in.network_api.managed.ManagedInfo;
import com.whoz_in.network_api.managed.ParsedLog;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ArpLogWriter {
    private final String room;
    private final ManagedLogRepository repository;
    private final ArpLogParser parser;
    private final List<ManagedInfo> arpList;
    private final String sudoPassword;

    public ArpLogWriter(ManagedLogRepository repository,
                        ArpLogParser parser,
                        NetworkConfig config
            ) {
        this.room = config.getRoom();
        this.repository = repository;
        this.parser = parser;
        this.arpList = config.getArpList();
        this.sudoPassword = config.getSudoPassword();
    }

    //주기적으로 arp 명령어를 실행하여 로그를 저장함
    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    private void scan() {
        List<ManagedLog> logs = arpList.stream() //실행할 arp들을 스트림화
                .map(info -> new ArpLogProcess(info, sudoPassword)) //arp 실행
                .map(this::getLogsFromProcess) //arp 출력을 로그 Set으로 변환
                .flatMap(Collection::stream) //Set끼리 합침
                .toList();
        repository.saveAll(logs);
    }

    //프로세스의 출력들을 로그로 변환한다.
    private List<ManagedLog> getLogsFromProcess(ArpLogProcess process){
        Set<ParsedLog> logs = process.resultList().stream()
                .filter(parser::validate)
                .map(parser::parse)
                .collect(Collectors.toSet());//Set으로 중복 제거

        String ssid = process.getInfo().ni().getEssid();
        log.info("[managed - arp({})] log to save : {}", ssid, logs.size());
        return logs.stream()
                .map(log -> new ManagedLog(log.getMac(), log.getIp(), log.getDeviceName(), ssid, room, log.getCreatedAt()))
                .toList();
    }
}
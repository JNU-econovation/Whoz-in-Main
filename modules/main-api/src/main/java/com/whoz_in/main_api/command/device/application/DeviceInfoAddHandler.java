package com.whoz_in.main_api.command.device.application;

import com.whoz_in.domain.device.service.DeviceFinderService;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain.member.service.MemberFinderService;
import com.whoz_in.domain.network_log.ManagedLog;
import com.whoz_in.domain.network_log.ManagedLogRepository;
import com.whoz_in.domain.network_log.MonitorLogRepository;
import com.whoz_in.main_api.command.shared.application.CommandHandler;
import com.whoz_in.main_api.shared.application.Handler;
import com.whoz_in.main_api.shared.caching.device.TempDeviceInfo;
import com.whoz_in.main_api.shared.caching.device.TempDeviceInfoStore;
import com.whoz_in.main_api.shared.utils.RequesterInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Handler
@RequiredArgsConstructor
public class DeviceInfoAddHandler implements CommandHandler<DeviceInfoAdd, String> {
    private final RequesterInfo requesterInfo;
    private final TempDeviceInfoStore tempDeviceInfoStore;
    private final MemberFinderService memberFinderService;
    private final DeviceFinderService deviceFinderService;
    //얘네 도메인에서의 입지가 애매해서 일단 Repository로 다뤘습니다.
    private final ManagedLogRepository managedLogRepository;
    private final MonitorLogRepository monitorLogRepository;

    //연결 시마다 맥이 바뀌는 기기가 다시 똑같은 와이파이에 등록하려고 하는 경우는 막지 못함
    @Transactional
    @Override
    public String handle(DeviceInfoAdd req) {
        MemberId requesterId = requesterInfo.getMemberId();
        memberFinderService.mustExist(requesterId);

        //해당 룸에서 발생한 아이피로 ManagedLog를 찾으며, 오래된 맥일 경우 신뢰할 수 없으므로 무시한다.
        ManagedLog managedLog = managedLogRepository.getLatestByIpAfter(req.ip().toString(), LocalDateTime.now().minusDays(1));
        String mac = managedLog.getMac();
        //등록할 TempDeviceInfo 생성
        TempDeviceInfo deviceInfo = new TempDeviceInfo(managedLog.getRoom(), managedLog.getSsid(), mac);
        //이미 등록된 TempDeviceInfo면 완료로 취급한다. (예외를 띄워야 할수도)
        if (tempDeviceInfoStore.exists(requesterId.id(), deviceInfo)) return managedLog.getSsid();
        //모니터 로그에서 현재 접속 중인 맥이 있어야 한다. (넉넉하게 15분)
        monitorLogRepository.mustExistAfter(mac, LocalDateTime.now().minusMinutes(15));
        //해당 맥으로 이미 등록된 기기가 없어야 한다.
        deviceFinderService.mustNotExistByMac(mac);
        //마침내! DeviceInfo를 추가한다.
        tempDeviceInfoStore.add(requesterId.id(), deviceInfo);

        return managedLog.getSsid();
    }
}

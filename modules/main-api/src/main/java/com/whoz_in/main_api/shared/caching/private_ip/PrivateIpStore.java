package com.whoz_in.main_api.shared.caching.private_ip;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoz_in.main_api.config.RoomSsidConfig;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PrivateIpStore {
    //룸보단 SSID로 저장해야 할 수도 있음 (ESSID가 같다면 맥이 같을 것으로 보이기 때문)
    private static final Cache<String, Map<String, String>> store = CacheBuilder.newBuilder().build(); //Cache<방, Map<SSID, 내부 아이피>>
    private final RoomSsidConfig ssidConfig;

    public void put(String room, Map<String, String> privateIps){
        store.put(room, privateIps);
    }

    public Map<String, String> get(String room){
        Map<String, String> privateIps = store.getIfPresent(room);
        if (privateIps == null){
            throw new IllegalStateException("방에 대한 내부 아이피 정보가 없음"); //main-api 서버 시작된지 얼마 안됐을 때 or 잘못된 room을 넘겼을 때
        }
        if (privateIps.size() != ssidConfig.getSsids(room).size()){
            throw new IllegalStateException(room + "의 내부 아이피가 올바르지 않은 상태"); //해당 방의 와이파이가 잘 연결됐는지 확인해봐야 함
        }
        return privateIps;
    }
    public Map<String, String> get(){
        return ssidConfig.getRooms().stream()
                .map(this::get)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> replacement
                ));
    }
}

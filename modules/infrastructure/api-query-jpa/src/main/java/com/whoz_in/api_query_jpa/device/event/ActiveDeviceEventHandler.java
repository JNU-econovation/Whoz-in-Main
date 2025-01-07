package com.whoz_in.api_query_jpa.device.event;

import com.whoz_in.api_query_jpa.device.ActiveDeviceEntity;
import com.whoz_in.api_query_jpa.device.ActiveDeviceRepository;
import com.whoz_in.domain.device.model.Device;
import com.whoz_in.main_api.query.device.application.active.event.ActiveDeviceFinded;
import com.whoz_in.main_api.query.device.application.active.event.InActiveDeviceFinded;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ActiveDeviceEventHandler {

    private final ActiveDeviceRepository activeDeviceRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @EventListener(ActiveDeviceFinded.class)
    public void saveActiveDevices(ActiveDeviceFinded event) {
        List<Device> devices = event.getDevices();
        List<ActiveDeviceEntity> entities = devices.stream()
                        .map(device -> ActiveDeviceEntity.create(device.getId(), LocalDateTime.now())) // TODO: active Time 을 이 시점으로 설정해도 될까?
                        .toList();

        activeDeviceRepository.saveAll(entities);
    }

    // 바로 위의 이벤트 핸들러에서 데이터를 수정할 수 있으므로, 격리 수준을 가장 강하게 설정
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @EventListener(ActiveDeviceFinded.class)
    public void processInActiveDevices(InActiveDeviceFinded event) {
        // InActiveDevice 찾는 로직
        List<Device> devices = event.getDevices();
        List<ActiveDeviceEntity> entities = activeDeviceRepository.findAll();

        entities.stream()
                .filter(activeDevice -> devices.stream()
                        .anyMatch(device -> device.getId().id().equals(activeDevice.getDeviceId())))
                .forEach(activeDevice -> activeDevice.inActiveOn(LocalDateTime.now()));
    }

}
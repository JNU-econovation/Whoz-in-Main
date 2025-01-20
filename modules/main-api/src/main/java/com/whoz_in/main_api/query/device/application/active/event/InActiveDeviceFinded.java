package com.whoz_in.main_api.query.device.application.active.event;

import com.whoz_in.main_api.shared.event.Event;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class InActiveDeviceFinded implements Event {

    private final List<UUID> devices;

    public InActiveDeviceFinded(List<UUID> devices) {
        this.devices = devices;
    }

}
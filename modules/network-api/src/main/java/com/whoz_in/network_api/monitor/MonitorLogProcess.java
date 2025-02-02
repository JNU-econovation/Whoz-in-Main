package com.whoz_in.network_api.monitor;

import com.whoz_in.network_api.common.process.ContinuousProcess;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MonitorLogProcess extends ContinuousProcess {

    public MonitorLogProcess(String command, String sudoPassword) {
        super(command, sudoPassword);
    }
}

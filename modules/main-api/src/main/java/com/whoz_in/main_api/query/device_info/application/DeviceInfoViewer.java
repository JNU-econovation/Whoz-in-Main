package com.whoz_in.main_api.query.device_info.application;


import com.whoz_in.main_api.shared.application.query.Viewer;

public interface DeviceInfoViewer extends Viewer {
    DeviceInfo findByDeviceId(Long deviceId);
    DevicesInfo findAll();
}
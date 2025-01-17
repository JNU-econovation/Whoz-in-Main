package com.whoz_in.main_api.query.private_ip;

import com.whoz_in.main_api.query.shared.application.QueryHandler;
import com.whoz_in.main_api.shared.application.Handler;
import com.whoz_in.main_api.shared.caching.private_ip.PrivateIpStore;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class PrivateIpListGetHandler implements QueryHandler<PrivateIpListGet, PrivateIpList> {
    private final PrivateIpStore privateIpStore;
    @Override
    public PrivateIpList handle(PrivateIpListGet query) {
        List<String> ipList = (query.room() != null) ?
                privateIpStore.getIpList(query.room()) :
                privateIpStore.getIpList();
        return new PrivateIpList(ipList);
    }
}
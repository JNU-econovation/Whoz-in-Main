package com.whoz_in.main_api.query.device.application.active;

import com.whoz_in.main_api.query.shared.application.Query;

public record MembersInRoom(
        int page,
        int size,
        String sortType
) implements Query {
}
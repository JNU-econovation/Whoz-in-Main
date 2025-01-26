package com.whoz_in.main_api.query.badge.application;

import com.whoz_in.domain.badge.exception.NoBadgeException;
import com.whoz_in.main_api.query.shared.application.QueryHandler;
import com.whoz_in.main_api.shared.application.Handler;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class BadgeInfoViewHandler implements QueryHandler<BadgeId, BadgeInfoResponse> {
    private final BadgeViewer viewer;

    @Override
    public BadgeInfoResponse handle(BadgeId query) {
        BadgeInfo badgeInfo = viewer.findBadgeInfoByBadgeId(query.badgeId()).orElseThrow(()-> NoBadgeException.EXCEPTION);
        return new BadgeInfoResponse(badgeInfo.name(), badgeInfo.colorCode());
    }
}


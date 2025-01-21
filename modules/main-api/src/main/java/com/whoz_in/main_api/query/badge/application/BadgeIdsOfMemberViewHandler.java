package com.whoz_in.main_api.query.badge.application;

import com.whoz_in.main_api.query.shared.application.QueryHandler;
import com.whoz_in.main_api.shared.application.Handler;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class BadgeIdsOfMemberViewHandler implements QueryHandler<MemberId, BadgeIdsResponse> {
    private final BadgeViewer viewer;

    @Override
    public BadgeIdsResponse handle(MemberId query) {
        BadgeIds badgeIds = viewer.findBadgesByMemberId(query.memberId());
        return new BadgeIdsResponse(badgeIds.badgeIds());
    }
}

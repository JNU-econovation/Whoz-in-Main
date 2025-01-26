package com.whoz_in.main_api.query.badge.application;

import com.whoz_in.main_api.query.shared.application.EmptyQuery;
import com.whoz_in.main_api.query.shared.application.QueryHandler;
import com.whoz_in.main_api.shared.application.Handler;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class BadgeIdsViewHandler implements QueryHandler<EmptyQuery, BadgeIdsResponse> {
    private final BadgeViewer viewer;

    @Override
    public BadgeIdsResponse handle(EmptyQuery query) {
        BadgeIds badgeIds= viewer.findAllBadgeIds();
        return new BadgeIdsResponse(badgeIds.badgeIds());
    }
}

package com.whoz_in.main_api.query.badge.application;

import com.whoz_in.main_api.query.shared.application.Viewer;
import java.util.Optional;
import java.util.UUID;

public interface BadgeViewer extends Viewer {
    Optional<BadgeInfo> findBadgeInfoByBadgeId(UUID badgeId);
    BadgeIds findAllBadgeIds();
    BadgeIds findBadgesByMemberId(UUID memberId);
}

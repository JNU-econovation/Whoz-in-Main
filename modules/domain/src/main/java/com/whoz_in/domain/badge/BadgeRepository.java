package com.whoz_in.domain.badge;

import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.badge.model.BadgeId;
import java.util.Optional;

public interface BadgeRepository {
    void save(Badge badge);
    Optional<Badge> findByName(String name);
    Optional<Badge> findByBadgeId(BadgeId badgeId);
}

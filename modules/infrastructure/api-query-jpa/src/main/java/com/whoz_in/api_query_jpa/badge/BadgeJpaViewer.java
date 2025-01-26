package com.whoz_in.api_query_jpa.badge;

import com.whoz_in.main_api.query.badge.application.BadgeId;
import com.whoz_in.main_api.query.badge.application.BadgeIds;
import com.whoz_in.main_api.query.badge.application.BadgeInfo;
import com.whoz_in.main_api.query.badge.application.BadgeViewer;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadgeJpaViewer implements BadgeViewer {
    private final BadgeRepository bageRepo;
    private final BadgeMemberRepository badgeMemberRepo;

    @Override
    public Optional<BadgeInfo> findBadgeInfoByBadgeId(UUID badgeId) {
        return bageRepo.findById(badgeId)
                .map(badge -> new BadgeInfo(badge.getName(),badge.getColor_code()));
    }

    @Override
    public BadgeIds findAllBadgeIds() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(12);
        Set<BadgeId> badgeIdSet = bageRepo.findAllActivatedBadgeIds(threshold)
                .stream()
                .map(badgeId -> new BadgeId(badgeId))
                .collect(Collectors.toSet());

        return new BadgeIds(badgeIdSet);
    }

    @Override
    public BadgeIds findBadgesByMemberId(UUID memberId) {
        Set<BadgeId> badgeIdSet = badgeMemberRepo.findByMemberId(memberId)
                .stream()
                .map(bageId -> new BadgeId(bageId))
                .collect(Collectors.toSet());
        return new BadgeIds(badgeIdSet);
    }
}

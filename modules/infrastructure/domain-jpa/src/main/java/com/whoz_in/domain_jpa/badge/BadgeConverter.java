package com.whoz_in.domain_jpa.badge;

import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.badge.model.BadgeInfo;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain_jpa.shared.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class BadgeConverter extends BaseConverter<BadgeEntity, Badge> {
    @Override
    public BadgeEntity from(Badge badge) {
        return new BadgeEntity(
                badge.getId().id(),
                badge.getBadgeInfo().getName(),
                badge.getBadgeInfo().getBadgeType(),
                badge.getBadgeInfo().getColorCode(),
                badge.getBadgeInfo().getCreator().id()
        );
    }

    @Override
    public Badge to(BadgeEntity entity) {
        BadgeInfo badgeInfo = BadgeInfo.load(
                entity.getName(),
                entity.getBadgeType(),
                new MemberId(entity.getCreator()),
                entity.getColorCode()
        );

        return Badge.load(
                new BadgeId(entity.getId()),
                badgeInfo
        );
    }
}

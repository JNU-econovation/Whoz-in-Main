package com.whoz_in.domain.member.event;

import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.member.model.IsBadgeShown;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain.shared.event.DomainEvent;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberBadgeChanged extends DomainEvent {
    private final MemberId memberId;
    private final Map<BadgeId, IsBadgeShown> badges;
}

package com.whoz_in.domain.badge.event;

import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.shared.event.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BadgeCreated extends DomainEvent {
    private final Badge badge;
}

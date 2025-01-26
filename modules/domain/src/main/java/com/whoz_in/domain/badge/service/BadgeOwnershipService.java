package com.whoz_in.domain.badge.service;

import com.whoz_in.domain.badge.exception.InvalidBadgeRegisterException;
import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.badge.model.BadgeType;
import com.whoz_in.domain.shared.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BadgeOwnershipService {
    private final BadgeFinderService badgeFinderService;

    public void validateType(BadgeType badgeType) {
        if (badgeType != BadgeType.USERMADE) {
            throw InvalidBadgeRegisterException.EXCEPTION;
        }
    }

}

package com.whoz_in.domain.badge.service;

import com.whoz_in.domain.badge.BadgeRepository;
import com.whoz_in.domain.badge.exception.NoBadgeException;
import com.whoz_in.domain.badge.exception.SameBadgeExistException;
import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.shared.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BadgeFinderService {
    private final BadgeRepository badgeRepository;

    public void mustNotExist(String name) {
        if (badgeRepository.findByName(name).isPresent()) {
            throw SameBadgeExistException.EXCEPTION;
        }
    }

    public Badge findByName(String name) {
        return badgeRepository.findByName(name).orElseThrow(()-> NoBadgeException.EXCEPTION);
    }

    public Badge find(BadgeId badgeId) {
        return badgeRepository.findByBadgeId(badgeId).orElseThrow(()-> NoBadgeException.EXCEPTION);
    }

    public void isExist(BadgeId badgeId) {
        if (!(badgeRepository.findByBadgeId(badgeId).isPresent())) {
            throw NoBadgeException.EXCEPTION;
        }
    }
}

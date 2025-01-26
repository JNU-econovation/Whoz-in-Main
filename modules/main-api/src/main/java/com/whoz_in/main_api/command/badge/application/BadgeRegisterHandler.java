package com.whoz_in.main_api.command.badge.application;

import com.whoz_in.domain.badge.BadgeRepository;
import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.badge.model.BadgeInfo;
import com.whoz_in.domain.badge.model.BadgeType;
import com.whoz_in.domain.badge.service.BadgeFinderService;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain.member.service.MemberFinderService;
import com.whoz_in.domain.shared.event.EventBus;
import com.whoz_in.main_api.command.shared.application.CommandHandler;
import com.whoz_in.main_api.shared.application.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Handler
@RequiredArgsConstructor
public class BadgeRegisterHandler implements CommandHandler<BadgeRegister, Void> {
    private final BadgeRepository repository;
    private final BadgeFinderService badgeFinderService;
    private final EventBus eventBus;
    private final MemberFinderService memberFinderService;

    @Transactional
    @Override
    public Void handle(BadgeRegister req) {
        memberFinderService.mustExist(new MemberId(req.creator()));
        badgeFinderService.mustNotExist(req.name());
        Badge badge = Badge.create(
                BadgeInfo.create(req.name(),BadgeType.USERMADE,req.colorCode(),new MemberId(req.creator()))
        );
        repository.save(badge);
        eventBus.publish(badge.pullDomainEvents());
        return null;
    }
}

package com.whoz_in.main_api.command.badge.application;

import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.member.MemberRepository;
import com.whoz_in.domain.member.exception.NoMemberException;
import com.whoz_in.domain.member.model.Member;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain.shared.event.EventBus;
import com.whoz_in.main_api.command.shared.application.CommandHandler;
import com.whoz_in.main_api.shared.application.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Handler
@RequiredArgsConstructor
public class SwitchBadgeVisibilityHandler implements CommandHandler<SwitchBadgeVisibility,Void> {
    private final MemberRepository repository;
    private final EventBus eventBus;

    @Transactional
    @Override
    public Void handle(SwitchBadgeVisibility req) {
        Member member = repository.findByMemberId(new MemberId(req.memberId())).orElseThrow(()-> NoMemberException.EXCEPTION);
        member.changeBadgeShowOrHide(new BadgeId(req.badgeId()));
        repository.save(member);
        eventBus.publish(member.pullDomainEvents());
        return null;
    }
}

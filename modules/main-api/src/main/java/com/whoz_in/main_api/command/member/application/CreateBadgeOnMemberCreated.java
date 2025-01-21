package com.whoz_in.main_api.command.member.application;

import com.whoz_in.domain.badge.BadgeRepository;
import com.whoz_in.domain.badge.exception.NoBadgeException;
import com.whoz_in.domain.badge.model.Badge;
import com.whoz_in.domain.member.MemberRepository;
import com.whoz_in.domain.member.event.MemberCreated;
import com.whoz_in.domain.member.model.Member;
import com.whoz_in.domain.shared.event.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreateBadgeOnMemberCreated {
    private final BadgeRepository badgeRepo;
    private final MemberRepository memberRepo;
    private final EventBus eventBus;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Void handleMemberCreatedEvent(MemberCreated event) {
        Member member = event.getMember();
        String position = member.getMainPosition().getPosition();
        Badge badge = badgeRepo.findByName(position).orElseThrow(()-> new NoBadgeException());
        member.addBadge(badge.getId());
        eventBus.publish(member.pullDomainEvents());
        return null;
    }
}

package com.whoz_in.main_api.command.member.application;

import com.whoz_in.domain.member.MemberRepository;
import com.whoz_in.domain.member.model.Member;
import com.whoz_in.domain.member.model.OAuthCredentials;
import com.whoz_in.domain.shared.event.EventBus;
import com.whoz_in.main_api.command.shared.application.CommandHandler;
import com.whoz_in.main_api.shared.application.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Handler
@RequiredArgsConstructor
public class MemberOAuth2SignUpHandler implements CommandHandler<MemberOAuth2SignUp, Void> {
    private final MemberRepository repository;
    private final EventBus eventBus;

    @Transactional
    @Override
    public Void handle(MemberOAuth2SignUp cmd) {
        if (repository.existsBySocialId(cmd.socialId())) {
            throw new IllegalArgumentException("이미 소셜 가입된 사용자입니다.");
        }

        Member member = Member.create(cmd.name(), cmd.position(), cmd.generation(),
                OAuthCredentials.create(cmd.socialProvider(), cmd.socialId()),null);
        repository.save(member);
        eventBus.publish(member.pullDomainEvents());
        return null;
    }
}

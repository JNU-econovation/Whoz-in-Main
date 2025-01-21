package com.whoz_in.domain_jpa.member;

import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.member.model.AuthCredentials;
import com.whoz_in.domain.member.model.IsBadgeShown;
import com.whoz_in.domain.member.model.Member;
import com.whoz_in.domain.member.model.MemberId;
import com.whoz_in.domain.member.model.OAuthCredentials;
import com.whoz_in.domain_jpa.badge.BadgeMemberEntity;
import com.whoz_in.domain_jpa.shared.BaseConverter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter extends BaseConverter<MemberEntity, Member> {

    @Override
    public MemberEntity from(Member member) {
        AuthCredentials auth = member.getAuthCredentials().orElse(null);
        OAuthCredentials oAuth = member.getOAuthCredentials().orElse(null);
        Set<BadgeMemberEntity> badgeMembers = member.getBadges().entrySet().stream()
                .map(entry -> new BadgeMemberEntity(
                        member.getId().id(),
                        entry.getKey().id(),
                        entry.getValue()
                ))
                .collect(Collectors.toSet());
        return new MemberEntity(
                member.getId().id(),
                member.getName(),
                member.getGeneration(),
                member.getMainPosition(),
                member.getStatusMessage(),
                auth != null ? auth.getLoginId() : null,
                auth != null ? auth.getEncodedPassword() : null,
                oAuth != null ? oAuth.getSocialProvider() : null,
                oAuth != null ? oAuth.getSocialId() : null,
                badgeMembers
        );
    }

    @Override
    public Member to(MemberEntity entity) {
        Map<BadgeId, IsBadgeShown> badges = entity.getBadgeMembers().stream()
                .collect(Collectors.toMap(
                        badgeMember -> new BadgeId(badgeMember.getBadgeId()),
                        badgeMember -> badgeMember.getIsBadgeShown()
                ));
        return Member.load(
                new MemberId(entity.getId()),
                entity.getName(),
                entity.getPosition(),
                entity.getGeneration(),
                entity.getStatusMessage(),
                (entity.getLoginId() != null && entity.getPassword() != null) ?
                        AuthCredentials.load(entity.getLoginId(), entity.getPassword()) : null,
                (entity.getSocialProvider() != null && entity.getSocialId() != null) ?
                        OAuthCredentials.create(entity.getSocialProvider(), entity.getSocialId()) : null,
                badges
        );
    }
}

package com.whoz_in.domain.member.model;

import com.whoz_in.domain.badge.exception.AlreadyHasBadgeException;
import com.whoz_in.domain.badge.model.BadgeId;
import com.whoz_in.domain.member.event.MemberBadgeAdded;
import com.whoz_in.domain.member.event.MemberBadgeChanged;
import com.whoz_in.domain.member.event.MemberCreated;
import com.whoz_in.domain.member.event.MemberPasswordChanged;
import com.whoz_in.domain.member.event.MemberStatusMessageChanged;
import com.whoz_in.domain.member.exception.NotAuthMemberException;
import com.whoz_in.domain.member.service.PasswordEncoder;
import com.whoz_in.domain.shared.AggregateRoot;
import com.whoz_in.domain.shared.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Member extends AggregateRoot {
    @Getter private final MemberId id;
    @Getter private final String name; //실명
    @Getter private final Position mainPosition;
    @Getter private final int generation; //기수
    @Getter private String statusMessage; //상태 메세지
    @Nullable private AuthCredentials authCredentials;
    @Nullable private OAuthCredentials oAuthCredentials;
    private final Map<BadgeId, IsBadgeShown> badges;

    //일반 로그인이 아닐수도 있으므로 Optional
    public Optional<AuthCredentials> getAuthCredentials(){
        return Optional.ofNullable(authCredentials);
    }
    //소셜 로그인이 아닐수도 있으므로 Optional
    public Optional<OAuthCredentials> getOAuthCredentials(){
        return Optional.ofNullable(oAuthCredentials);
    }

    //일반 회원가입
    public static Member create(String name, Position mainPosition, int generation, AuthCredentials authCredentials, Set<BadgeId> badges){
        return create(name, mainPosition, generation, authCredentials, null, new HashMap<>());
    }
    //소셜 회원가입
    public static Member create(String name, Position mainPosition, int generation, OAuthCredentials oAuthCredentials, Set<BadgeId> badges){
        return create(name, mainPosition, generation, null, oAuthCredentials, new HashMap<>());
    }

    private static Member create(String name, Position mainPosition, int generation,
            AuthCredentials authCredentials, OAuthCredentials oAuthCredentials, Map<BadgeId, IsBadgeShown> badges){
        if (authCredentials == null && oAuthCredentials == null)
            throw new IllegalStateException("no auth and oauth");
        Member member = builder()
                .id(new MemberId())
                .name(name)
                .mainPosition(mainPosition)
                .generation(generation)
                .statusMessage("")
                .authCredentials(authCredentials)
                .oAuthCredentials(oAuthCredentials)
                .badges(badges)
                .build();
        member.register(new MemberCreated(member));
        return member;
    }

    public static Member load(MemberId id, String name, Position mainPosition, int generation, String statusMessage,
            AuthCredentials authCredentials, OAuthCredentials oAuthCredentials, Map<BadgeId, IsBadgeShown> badges){
        return builder()
                .id(id)
                .name(name)
                .mainPosition(mainPosition)
                .generation(generation)
                .statusMessage(statusMessage)
                .authCredentials(authCredentials)
                .oAuthCredentials(oAuthCredentials)
                .badges(badges)
                .build();
    }

    public void changePassword(String rawOldPassword, String rawNewPassword, PasswordEncoder passwordEncoder){
        if (authCredentials == null)
            throw NotAuthMemberException.EXCEPTION;
        this.authCredentials = this.authCredentials.changePassword(rawOldPassword, rawNewPassword, passwordEncoder);
        this.register(new MemberPasswordChanged(this.getId(), this.authCredentials.getEncodedPassword()));
    }

    public void changeStatusMessage(String newStatusMessage){
        this.statusMessage = newStatusMessage;
        this.register(new MemberStatusMessageChanged(this.getId(), this.statusMessage));
    }

    public void changeBadgeShowOrHide(BadgeId badgeId) {
        this.badges.computeIfPresent(badgeId, (id, isShown) -> isShown == IsBadgeShown.Y ? IsBadgeShown.N : IsBadgeShown.Y);
        this.register(new MemberBadgeChanged(this.getId(), this.badges));
    }

    public Optional<IsBadgeShown> getBadgeStatus(BadgeId badgeId) {
        return Optional.ofNullable(this.badges.get(badgeId));
    }

    public void addBadge(BadgeId badgeId) {
        if (badges.containsKey(badgeId)) {
            throw AlreadyHasBadgeException.EXCEPTION;
        }
        if (!badges.containsKey(badgeId)) {
            badges.put(badgeId, IsBadgeShown.Y);
            this.register(new MemberBadgeAdded(this.getId(), this.badges));
        }
    }

    public Map<BadgeId, IsBadgeShown> getBadges() {
        return Collections.unmodifiableMap(this.badges);
    }
}

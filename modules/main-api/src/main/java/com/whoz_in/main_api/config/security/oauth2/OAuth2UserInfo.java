package com.whoz_in.main_api.config.security.oauth2;

import com.whoz_in.domain.member.model.OAuthCredentials;
import com.whoz_in.domain.member.model.SocialProvider;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

// 우리 서비스에서 OAuth2 제공자로부터 받은 사용자 정보를 구성하는 클래스
@Getter
public class OAuth2UserInfo implements OAuth2User {

    private final boolean isRegistered;
    private final OAuthCredentials oAuthCredentials;
    private final String email;

    public OAuth2UserInfo(boolean isRegistered, SocialProvider socialProvider, String socialId, String email) {
        this.isRegistered = isRegistered;
        this.oAuthCredentials = OAuthCredentials.load(socialProvider, socialId);
        this.email = email;
    }

    @Override
    public String getName() {
        return getOAuthCredentials().getSocialId();
    }

    //없애고 싶다.........
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    // TODO: 일단 빈 리스트, 추후 권한 기능 생기면 변경
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

}
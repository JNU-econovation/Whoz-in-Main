package com.whoz_in.main_api.shared.jwt.tokens;

import com.whoz_in.main_api.shared.jwt.JwtProperties;
import com.whoz_in.main_api.shared.jwt.JwtUtil;
import com.whoz_in.main_api.shared.jwt.TokenType;
import io.jsonwebtoken.Claims;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
문자열 토큰 <-> 토큰 VO 변환을 지원한다.
문자열 토큰 -> 토큰 VO 변환 시 올바른 토큰인지 검증할 책임이 있다.
 */
@Component
@RequiredArgsConstructor
public abstract class TokenSerializer<T extends Token> {
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public final T deserialize(String token){
        Claims claims = jwtUtil.getClaims(token);
        validate(claims);
        return buildToken(claims);
    }

    public final String serialize(T jwtInfo){
        Map<String, String> claims = buildClaims(jwtInfo);
        return jwtUtil.createJwt(getTokenType(), claims, jwtProperties.getTokenExpiry(getTokenType()));
    }

    //TODO: jwt.Claims 의존성 끊기
    protected void validate(Claims claims) {
        jwtUtil.ensureNotExpired(claims);
        jwtUtil.ensureTokenTypeMatches(claims, getTokenType());
    }


    protected abstract T buildToken(Claims claims);
    /**
     buildToken()과 Claim 개수가 같도록 만들어야 한다. //TODO: 컴파일 타임에 확인할 수 있는 법 생각해보기
     */
    protected abstract Map<String, String> buildClaims(T jwtInfo);
    protected abstract TokenType getTokenType();
}

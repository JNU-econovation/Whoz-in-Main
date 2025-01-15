package com.whoz_in.domain.member.exception;

import com.whoz_in.domain.shared.BusinessException;

public class NotOAuthMemberException extends BusinessException {

    public NotOAuthMemberException() {
        super("소셜 로그인 정보가 없는 회원입니다.");
    }
}

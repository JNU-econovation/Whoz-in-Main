package com.whoz_in.domain.member.exception;

import com.whoz_in.domain.shared.BusinessException;

public class NotAuthMemberException extends BusinessException {
    public NotAuthMemberException() {
        super("일반 로그인 정보가 없는 회원입니다.");
    }
}

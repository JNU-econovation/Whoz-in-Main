package com.whoz_in.domain.member.exception;

import com.whoz_in.domain.shared.BusinessException;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException() {
        super("틀린 비밀번호입니다.");
    }
}

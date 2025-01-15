package com.whoz_in.domain.member.exception;

import com.whoz_in.domain.shared.BusinessException;

public class InvalidAuthCredentialsException extends BusinessException {

    public InvalidAuthCredentialsException() {
        super("아이디 혹은 비밀번호가 틀렸습니다.");
    }
}

package com.whoz_in.domain.member.exception;

import com.whoz_in.domain.shared.BusinessException;

public class NoMemberException extends BusinessException {

    public NoMemberException() {
        super("존재하지 않는 멤버입니다.");
    }
}

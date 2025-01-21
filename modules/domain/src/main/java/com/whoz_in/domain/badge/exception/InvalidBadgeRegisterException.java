package com.whoz_in.domain.badge.exception;

import com.whoz_in.domain.shared.BusinessException;

public class InvalidBadgeRegisterException extends BusinessException {
    public static final InvalidBadgeRegisterException EXCEPTION = new InvalidBadgeRegisterException();
    public InvalidBadgeRegisterException() {
        super("5002", "등록할 수 없는 뱃지입니다.");
    }
}

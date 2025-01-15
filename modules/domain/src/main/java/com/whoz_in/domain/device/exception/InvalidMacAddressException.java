package com.whoz_in.domain.device.exception;

import com.whoz_in.domain.shared.BusinessException;

public class InvalidMacAddressException extends BusinessException {
    public InvalidMacAddressException() {
        super("유효하지 않은 Mac 주소입니다.");
    }
}

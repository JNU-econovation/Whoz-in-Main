package com.whoz_in.domain.device.exception;

import com.whoz_in.domain.shared.BusinessException;

public class InvalidIPv4AddressException extends BusinessException {
    public InvalidIPv4AddressException() {
        super("유효하지 않은 IPv4 주소입니다.");
    }
}

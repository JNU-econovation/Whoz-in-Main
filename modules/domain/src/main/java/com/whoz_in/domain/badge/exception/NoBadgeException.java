package com.whoz_in.domain.badge.exception;

import com.whoz_in.domain.shared.BusinessException;

public class NoBadgeException extends BusinessException {
    public static final NoBadgeException EXCEPTION = new NoBadgeException();
    public NoBadgeException() {super("5001", "뱃지를 찾을 수 없습니다.");}
}

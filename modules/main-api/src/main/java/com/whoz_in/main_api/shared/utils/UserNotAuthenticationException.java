package com.whoz_in.main_api.shared.utils;

import com.whoz_in.domain.shared.BusinessException;

/*
필터에서 엔드포인트마다 적절한 인증 로직을 구현하면 발생하지 않음
 */
//TODO: 리팩토링 - 패키지 위치, 상속할 exception 등
public class UserNotAuthenticationException extends BusinessException {

    protected UserNotAuthenticationException() {
        super("유저 아이디가 없습니다");
    }
}

package com.whoz_in.domain.shared;

import lombok.Getter;

//비즈니스 내에서 발생하는 예외이므로 HttpStatus는 담지 않습니다.
// DomainException (= BusinessException) 은 에러 코드를 가지지 않는다.
@Getter
public abstract class BusinessException extends RuntimeException{

    protected BusinessException(String errorMessage) {
        super(errorMessage);
    }
    
}

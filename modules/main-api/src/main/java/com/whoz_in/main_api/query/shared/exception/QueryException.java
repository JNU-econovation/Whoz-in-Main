package com.whoz_in.main_api.query.shared.exception;

public class QueryException extends RuntimeException{

    private final String errorCode;

    public QueryException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}
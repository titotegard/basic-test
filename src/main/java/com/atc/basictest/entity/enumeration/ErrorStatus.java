package com.atc.basictest.entity.enumeration;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorStatus {
    NOT_FOUND(401, 30000, "Cannot find resource with id %d"),
    ALREADY_EXIST(409, 30001, "Record with unique value %s already exist in the system"),
    INVALID_VAL(422, 30002, "Invalid value for field %s, rejected value: %s"),
    SYS_ERR(500, 80000, "System error, we're unable to process your request at the moment");

    private final Integer code;
    private final Integer status;
    private final String message;

    ErrorStatus(Integer code, Integer status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static Optional<ErrorStatus> getErrorStatusByCode(Integer value) {
        return Arrays.stream(ErrorStatus.values())
                .filter(errStatus -> errStatus.code.equals(value))
                .findFirst();
    }
}
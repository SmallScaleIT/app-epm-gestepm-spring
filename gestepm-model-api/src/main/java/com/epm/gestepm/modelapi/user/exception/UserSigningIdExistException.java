package com.epm.gestepm.modelapi.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSigningIdExistException extends RuntimeException {

    private final Integer signingId;

}

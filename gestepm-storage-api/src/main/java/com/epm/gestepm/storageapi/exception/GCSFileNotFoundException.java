package com.epm.gestepm.storageapi.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class GCSFileNotFoundException extends RuntimeException {

    public GCSFileNotFoundException(final String budgetName, final String fileName) {
        super(format("GCS file %s not found in budget %s", fileName, budgetName));
    }
}
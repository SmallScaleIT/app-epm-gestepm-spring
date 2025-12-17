package com.epm.gestepm.storageapi.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class GCSUploadException extends RuntimeException {

    public GCSUploadException(final Throwable ex) {
        super(format("GCS upload exception: [%s]", ex.getMessage()));
    }
}

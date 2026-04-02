package com.epm.gestepm.rest.material;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.material.exception.MaterialNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class MaterialExceptionHandler extends BaseRestExceptionHandler {

    public static final int MAT_ERROR_CODE = 2100;

    public static final String MAT_NOT_FOUND = "material-not-found";

    public MaterialExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(MaterialNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(MaterialNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(MAT_ERROR_CODE, MAT_NOT_FOUND, MAT_NOT_FOUND, id);
    }

}

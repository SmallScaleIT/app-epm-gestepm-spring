package com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareFileUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

}

package com.epm.gestepm.modelapi.shares.construction.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConstructionShareFileUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

}

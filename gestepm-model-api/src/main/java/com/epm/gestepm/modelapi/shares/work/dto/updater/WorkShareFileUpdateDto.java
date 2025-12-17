package com.epm.gestepm.modelapi.shares.work.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkShareFileUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

}

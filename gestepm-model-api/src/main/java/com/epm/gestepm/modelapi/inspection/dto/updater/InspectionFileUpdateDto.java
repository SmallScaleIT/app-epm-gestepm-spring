package com.epm.gestepm.modelapi.inspection.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InspectionFileUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

}

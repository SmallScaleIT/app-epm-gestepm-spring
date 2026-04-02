package com.epm.gestepm.modelapi.material.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MaterialUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

}

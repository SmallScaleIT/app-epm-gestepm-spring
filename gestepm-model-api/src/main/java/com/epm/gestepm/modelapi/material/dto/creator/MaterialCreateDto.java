package com.epm.gestepm.modelapi.material.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MaterialCreateDto {

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

}

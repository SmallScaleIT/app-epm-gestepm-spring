package com.epm.gestepm.modelapi.material.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MaterialDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

}

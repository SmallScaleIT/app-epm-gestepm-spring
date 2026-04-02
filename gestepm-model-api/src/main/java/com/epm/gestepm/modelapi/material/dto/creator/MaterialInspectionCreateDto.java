package com.epm.gestepm.modelapi.material.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialInspectionCreateDto {

    @NotNull
    private Integer materialId;

    @NotNull
    private Integer inspectionId;

}

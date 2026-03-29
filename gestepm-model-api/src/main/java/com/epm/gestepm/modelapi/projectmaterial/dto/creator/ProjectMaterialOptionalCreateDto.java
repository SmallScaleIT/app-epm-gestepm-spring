package com.epm.gestepm.modelapi.projectmaterial.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMaterialOptionalCreateDto {

    @NotNull
    private Integer projectMaterialId;

    @NotNull
    private Integer inspectionId;

}

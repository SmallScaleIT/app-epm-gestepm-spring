package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialOptionalCreate;
import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialOptionalCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPMOToProjectMaterialOptionalCreate {

    ProjectMaterialOptionalCreate from(ProjectMaterialOptionalCreateDto createDto);

}

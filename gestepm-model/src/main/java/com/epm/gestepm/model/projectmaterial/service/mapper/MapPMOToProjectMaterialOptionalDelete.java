package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialOptionalDelete;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialOptionalDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPMOToProjectMaterialOptionalDelete {

    ProjectMaterialOptionalDelete from(ProjectMaterialOptionalDeleteDto deleteDto);

}

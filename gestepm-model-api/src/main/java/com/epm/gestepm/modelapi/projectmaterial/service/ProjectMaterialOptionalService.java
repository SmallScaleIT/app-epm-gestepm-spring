package com.epm.gestepm.modelapi.projectmaterial.service;

import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialOptionalCreateDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialOptionalDeleteDto;

import javax.validation.Valid;

public interface ProjectMaterialOptionalService {

    void create(@Valid ProjectMaterialOptionalCreateDto createDto);

    void delete(@Valid ProjectMaterialOptionalDeleteDto deleteDto);

}

package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.creator.MaterialInspectionCreate;
import com.epm.gestepm.modelapi.material.dto.creator.MaterialInspectionCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATIToMaterialInspectionCreate {

    MaterialInspectionCreate from(MaterialInspectionCreateDto createDto);

}

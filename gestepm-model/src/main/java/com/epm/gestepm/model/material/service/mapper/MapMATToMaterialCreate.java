package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.creator.MaterialCreate;
import com.epm.gestepm.modelapi.material.dto.MaterialDto;
import com.epm.gestepm.modelapi.material.dto.creator.MaterialCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialCreate {

  MaterialCreate from(MaterialCreateDto createDto);

  MaterialCreateDto from(MaterialDto dto);

}

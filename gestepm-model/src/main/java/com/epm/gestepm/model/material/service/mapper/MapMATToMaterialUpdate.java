package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.updater.MaterialUpdate;
import com.epm.gestepm.modelapi.material.dto.updater.MaterialUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialUpdate {

  MaterialUpdate from(MaterialUpdateDto updateDto);

}

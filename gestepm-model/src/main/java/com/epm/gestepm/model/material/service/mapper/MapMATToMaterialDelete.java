package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.deleter.MaterialDelete;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialDelete {

  MaterialDelete from(MaterialDeleteDto deleteDto);

}

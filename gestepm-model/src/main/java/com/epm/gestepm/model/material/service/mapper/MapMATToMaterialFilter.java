package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.filter.MaterialFilter;
import com.epm.gestepm.modelapi.material.dto.filter.MaterialFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialFilter {

  MaterialFilter from(MaterialFilterDto filterDto);

}

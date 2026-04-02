package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.finder.MaterialByIdFinder;
import com.epm.gestepm.modelapi.material.dto.finder.MaterialByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialByIdFinder {

  MaterialByIdFinder from(MaterialByIdFinderDto finderDto);

}

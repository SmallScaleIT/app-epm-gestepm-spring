package com.epm.gestepm.rest.material.mappers;

import com.epm.gestepm.modelapi.material.dto.finder.MaterialByIdFinderDto;
import com.epm.gestepm.rest.material.request.MaterialFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialByIdFinderDto {

  MaterialByIdFinderDto from(MaterialFindRestRequest req);

}

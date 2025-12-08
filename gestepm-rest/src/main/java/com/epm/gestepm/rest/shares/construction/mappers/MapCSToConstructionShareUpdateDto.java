package com.epm.gestepm.rest.shares.construction.mappers;

import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateConstructionShareV1RequestData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapCSToConstructionShareUpdateDto {

    @Mapping(target = "files", ignore = true)
    ConstructionShareUpdateDto from(UpdateConstructionShareV1RequestData req);

}

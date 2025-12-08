package com.epm.gestepm.rest.shares.work.mappers;

import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateWorkShareV1RequestData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapWSToWorkShareUpdateDto {

    @Mapping(target = "files", ignore = true)
    WorkShareUpdateDto from(UpdateWorkShareV1RequestData req);

}

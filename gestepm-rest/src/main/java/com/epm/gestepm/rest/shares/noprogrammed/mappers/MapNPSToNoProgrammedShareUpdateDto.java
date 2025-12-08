package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1RequestData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapNPSToNoProgrammedShareUpdateDto {

    @Mapping(target = "files", ignore = true)
    NoProgrammedShareUpdateDto from(UpdateNoProgrammedShareV1RequestData req);

}

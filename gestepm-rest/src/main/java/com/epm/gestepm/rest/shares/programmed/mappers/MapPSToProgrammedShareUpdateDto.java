package com.epm.gestepm.rest.shares.programmed.mappers;

import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateProgrammedShareV1RequestData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapPSToProgrammedShareUpdateDto {

    @Mapping(target = "files", ignore = true)
    ProgrammedShareUpdateDto from(UpdateProgrammedShareV1RequestData req);

}

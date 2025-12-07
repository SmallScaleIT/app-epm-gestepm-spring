package com.epm.gestepm.rest.shares.programmed.mappers;

import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateProgrammedShareV1Request;
import com.epm.gestepm.restapi.openapi.model.UpdateProgrammedShareV1RequestData;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareUpdateDto {

  ProgrammedShareUpdateDto from(UpdateProgrammedShareV1RequestData req);

}

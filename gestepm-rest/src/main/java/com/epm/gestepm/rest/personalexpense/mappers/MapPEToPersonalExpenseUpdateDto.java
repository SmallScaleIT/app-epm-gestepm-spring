package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdatePersonalExpenseV1RequestData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapPEToPersonalExpenseUpdateDto {

    @Mapping(target = "files", ignore = true)
    PersonalExpenseUpdateDto from(UpdatePersonalExpenseV1RequestData req);

}

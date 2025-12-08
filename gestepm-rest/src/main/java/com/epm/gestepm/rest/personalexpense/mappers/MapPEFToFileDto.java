package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseFileCreateDto;
import com.epm.gestepm.restapi.openapi.model.UpdatePersonalExpenseV1RequestFilesInner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPEFToFileDto {

    PersonalExpenseFileCreateDto from(UpdatePersonalExpenseV1RequestFilesInner req);

}

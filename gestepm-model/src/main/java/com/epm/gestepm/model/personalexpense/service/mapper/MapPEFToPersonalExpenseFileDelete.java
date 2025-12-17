package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseFileDelete;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseFileDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEFToPersonalExpenseFileDelete {

    PersonalExpenseFileDelete from(PersonalExpenseFileDeleteDto deleteDto);

}

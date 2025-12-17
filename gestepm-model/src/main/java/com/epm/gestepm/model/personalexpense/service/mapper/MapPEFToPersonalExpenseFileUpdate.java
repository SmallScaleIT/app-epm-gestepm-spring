package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseFileUpdate;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseFileUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEFToPersonalExpenseFileUpdate {

    PersonalExpenseFileUpdate from(PersonalExpenseFileUpdateDto updateDto);

}

package com.epm.gestepm.modelapi.personalexpense.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseFileCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseFileDeleteDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseFileByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseFileUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseFileService {

    List<@Valid PersonalExpenseFileDto> list(@Valid PersonalExpenseFileFilterDto filterDto);

    Page<@Valid PersonalExpenseFileDto> list(@Valid PersonalExpenseFileFilterDto filterDto, Long offset, Long limit);

    Optional<@Valid PersonalExpenseFileDto> find(@Valid PersonalExpenseFileByIdFinderDto finderDto);

    @Valid
    PersonalExpenseFileDto findOrNotFound(@Valid PersonalExpenseFileByIdFinderDto finderDto);

    @Valid
    PersonalExpenseFileDto create(@Valid PersonalExpenseFileCreateDto createDto);

    @Valid
    PersonalExpenseFileDto update(@Valid PersonalExpenseFileUpdateDto updateDto);

    void delete(@Valid PersonalExpenseFileDeleteDto deleteDto);

}

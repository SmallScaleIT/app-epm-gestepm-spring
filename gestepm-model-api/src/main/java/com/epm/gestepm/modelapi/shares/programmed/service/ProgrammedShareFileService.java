package com.epm.gestepm.modelapi.shares.programmed.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareFileUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProgrammedShareFileService {

    List<@Valid ProgrammedShareFileDto> list(@Valid ProgrammedShareFileFilterDto filterDto);

    Page<@Valid ProgrammedShareFileDto> list(@Valid ProgrammedShareFileFilterDto filterDto, Long offset, Long limit);

    Optional<@Valid ProgrammedShareFileDto> find(@Valid ProgrammedShareFileByIdFinderDto finderDto);

    @Valid
    ProgrammedShareFileDto findOrNotFound(@Valid ProgrammedShareFileByIdFinderDto finderDto);

    @Valid
    ProgrammedShareFileDto create(@Valid ProgrammedShareFileCreateDto createDto);

    @Valid
    ProgrammedShareFileDto update(@Valid ProgrammedShareFileUpdateDto updateDto);

    void delete(@Valid ProgrammedShareFileDeleteDto deleteDto);
    
}

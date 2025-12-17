package com.epm.gestepm.modelapi.shares.work.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareFileDto;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareFileUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface WorkShareFileService {

    List<@Valid WorkShareFileDto> list(@Valid WorkShareFileFilterDto filterDto);

    Page<@Valid WorkShareFileDto> list(@Valid WorkShareFileFilterDto filterDto, Long offset, Long limit);

    Optional<@Valid WorkShareFileDto> find(@Valid WorkShareFileByIdFinderDto finderDto);

    @Valid
    WorkShareFileDto findOrNotFound(@Valid WorkShareFileByIdFinderDto finderDto);

    @Valid
    WorkShareFileDto create(@Valid WorkShareFileCreateDto createDto);

    @Valid
    WorkShareFileDto update(@Valid WorkShareFileUpdateDto updateDto);

    void delete(@Valid WorkShareFileDeleteDto deleteDto);
    
}

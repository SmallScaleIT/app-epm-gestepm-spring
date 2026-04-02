package com.epm.gestepm.modelapi.material.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.material.dto.MaterialDto;
import com.epm.gestepm.modelapi.material.dto.creator.MaterialCreateDto;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialDeleteDto;
import com.epm.gestepm.modelapi.material.dto.filter.MaterialFilterDto;
import com.epm.gestepm.modelapi.material.dto.finder.MaterialByIdFinderDto;
import com.epm.gestepm.modelapi.material.dto.updater.MaterialUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface MaterialService {

  List<@Valid MaterialDto> list(@Valid MaterialFilterDto filterDto);

  Page<@Valid MaterialDto> list(@Valid MaterialFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid MaterialDto> find(@Valid MaterialByIdFinderDto finderDto);

  @Valid
  MaterialDto findOrNotFound(@Valid MaterialByIdFinderDto finderDto);

  @Valid
  MaterialDto create(@Valid MaterialCreateDto createDto);

  @Valid
  MaterialDto update(@Valid MaterialUpdateDto updateDto);

  void delete(@Valid MaterialDeleteDto deleteDto);

}

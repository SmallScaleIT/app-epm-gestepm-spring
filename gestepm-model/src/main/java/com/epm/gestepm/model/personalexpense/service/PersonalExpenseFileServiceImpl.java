package com.epm.gestepm.model.personalexpense.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpense.dao.PersonalExpenseFileDao;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseFileDelete;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFileFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseFileByIdFinder;
import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseFileUpdate;
import com.epm.gestepm.model.personalexpense.service.mapper.*;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseFileCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseFileDeleteDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseFileByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseFileUpdateDto;
import com.epm.gestepm.modelapi.personalexpense.exception.PersonalExpenseFileNotFoundException;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseFileService;
import com.epm.gestepm.storageapi.dto.FileResponse;
import com.epm.gestepm.storageapi.dto.deleter.FileDelete;
import com.epm.gestepm.storageapi.dto.finder.FileByNameFinder;
import com.epm.gestepm.storageapi.service.GoogleCloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_EDIT_PE;
import static com.epm.gestepm.modelapi.personalexpense.security.PersonalExpensePermission.PRMT_READ_PE;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class PersonalExpenseFileServiceImpl implements PersonalExpenseFileService {

    private final GoogleCloudStorageService googleCloudStorageService;
    
    private final PersonalExpenseFileDao personalExpenseFileDao;

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "List personal expense files")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating personal expense file files",
            msgOut = "Paginating personal expense file files OK",
            errorMsg = "Failed to paginate personal expense file files")
    public List<PersonalExpenseFileDto> list(PersonalExpenseFileFilterDto filterDto) {
        final PersonalExpenseFileFilter filter = getMapper(MapPEFToPersonalExpenseFileFilter.class).from(filterDto);

        final List<PersonalExpenseFile> list = this.personalExpenseFileDao.list(filter);
        list.forEach(this::populateFileUrl);

        return getMapper(MapPEFToPersonalExpenseFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Page personal expenses")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating personal expense file files",
            msgOut = "Paginating personal expense file files OK",
            errorMsg = "Failed to paginate personal expense file files")
    public Page<PersonalExpenseFileDto> list(PersonalExpenseFileFilterDto filterDto, Long offset, Long limit) {
        final PersonalExpenseFileFilter filter = getMapper(MapPEFToPersonalExpenseFileFilter.class).from(filterDto);

        final Page<PersonalExpenseFile> list = this.personalExpenseFileDao.list(filter, offset, limit);
        list.forEach(this::populateFileUrl);

        return getMapper(MapPEFToPersonalExpenseFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding personal expense file by ID, result can be empty",
            msgOut = "Found personal expense file by ID",
            errorMsg = "Failed to find personal expense file by ID")
    public Optional<PersonalExpenseFileDto> find(final PersonalExpenseFileByIdFinderDto finderDto) {
        final PersonalExpenseFileByIdFinder finder = getMapper(MapPEFToPersonalExpenseFileByIdFinder.class).from(finderDto);

        final Optional<PersonalExpenseFile> found = this.personalExpenseFileDao.find(finder);
        found.ifPresent(this::populateFileUrl);

        return found.map(getMapper(MapPEFToPersonalExpenseFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PE, action = "Find personal expense file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding personal expense file by ID, result is expected or will fail",
            msgOut = "Found personal expense file by ID",
            errorMsg = "No personal expense by ID not found")
    public PersonalExpenseFileDto findOrNotFound(final PersonalExpenseFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new PersonalExpenseFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PE, action = "Create new personal expense file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new personal expense file",
            msgOut = "New personal expense file OK",
            errorMsg = "Failed to create new personal expense file")
    public PersonalExpenseFileDto create(PersonalExpenseFileCreateDto createDto) {
        final PersonalExpenseFileCreate create = getMapper(MapPEFToPersonalExpenseFileCreate.class).from(createDto);

        final PersonalExpenseFile result = this.personalExpenseFileDao.create(create);
        this.populateFileUrl(result);

        return getMapper(MapPEFToPersonalExpenseFileDto.class).from(result);
    }

    @Override
    public PersonalExpenseFileDto update(PersonalExpenseFileUpdateDto updateDto) {
        final PersonalExpenseFileUpdate update = getMapper(MapPEFToPersonalExpenseFileUpdate.class).from(updateDto);

        final PersonalExpenseFile result = this.personalExpenseFileDao.update(update);
        this.populateFileUrl(result);

        return getMapper(MapPEFToPersonalExpenseFileDto.class).from(result);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PE, action = "Delete personal expense file")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting personal expense file",
            msgOut = "Personal expense file deleted OK",
            errorMsg = "Failed to delete personal expense file")
    public void delete(PersonalExpenseFileDeleteDto deleteDto) {

        final PersonalExpenseFileByIdFinderDto finderDto = new PersonalExpenseFileByIdFinderDto(deleteDto.getId());

        final PersonalExpenseFileDto fileDto = this.findOrNotFound(finderDto);

        this.googleCloudStorageService.deleteFile(new FileDelete(fileDto.getStoragePath()));

        final PersonalExpenseFileDelete delete = getMapper(MapPEFToPersonalExpenseFileDelete.class).from(deleteDto);

        this.personalExpenseFileDao.delete(delete);
    }

    private void populateFileUrl(final PersonalExpenseFile file) {
        if (file.getStoragePath() == null) { // FIXME: to remove
            return;
        }

        final FileByNameFinder finder = new FileByNameFinder(file.getStoragePath());
        final FileResponse fileResponse = this.googleCloudStorageService.getFile(finder);

        file.setUrl(fileResponse.getUrl());
    }
}

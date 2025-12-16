package com.epm.gestepm.model.shares.programmed.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileDto;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.programmed.dao.ProgrammedShareFileDao;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareFileDelete;
import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareFileByIdFinder;
import com.epm.gestepm.model.shares.programmed.dao.entity.updater.ProgrammedShareFileUpdate;
import com.epm.gestepm.model.shares.programmed.service.mapper.*;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.programmed.exception.ProgrammedShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareFileService;
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
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.PRMT_EDIT_PS;
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.PRMT_READ_PS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ProgrammedShareFileServiceImpl implements ProgrammedShareFileService {

    private final GoogleCloudStorageService googleCloudStorageService;

    private final ProgrammedShareFileDao programmedShareFileDao;

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "List programmed shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing programmed share file files",
            msgOut = "Listing programmed share file files OK",
            errorMsg = "Failed to list programmed share file files")
    public List<ProgrammedShareFileDto> list(ProgrammedShareFileFilterDto filterDto) {
        final ProgrammedShareFileFilter filter = getMapper(MapPSFToProgrammedShareFileFilter.class).from(filterDto);

        final List<ProgrammedShareFile> list = this.programmedShareFileDao.list(filter);
        list.forEach(this::populateFileUrl);

        return getMapper(MapPSFToProgrammedShareFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Page programmed shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating programmed share file files",
            msgOut = "Paginating programmed share file files OK",
            errorMsg = "Failed to paginate programmed share file files")
    public Page<ProgrammedShareFileDto> list(ProgrammedShareFileFilterDto filterDto, Long offset, Long limit) {
        final ProgrammedShareFileFilter filter = getMapper(MapPSFToProgrammedShareFileFilter.class).from(filterDto);

        final Page<ProgrammedShareFile> list = this.programmedShareFileDao.list(filter, offset, limit);
        list.forEach(this::populateFileUrl);

        return getMapper(MapPSFToProgrammedShareFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Find programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding programmed share file by ID, result can be empty",
            msgOut = "Found programmed share file by ID",
            errorMsg = "Failed to find programmed share file by ID")
    public Optional<ProgrammedShareFileDto> find(final ProgrammedShareFileByIdFinderDto finderDto) {
        final ProgrammedShareFileByIdFinder finder = getMapper(MapPSFToProgrammedShareFileByIdFinder.class).from(finderDto);

        final Optional<ProgrammedShareFile> found = this.programmedShareFileDao.find(finder);
        found.ifPresent(this::populateFileUrl);

        return found.map(getMapper(MapPSFToProgrammedShareFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Find programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding programmed share file by ID, result is expected or will fail",
            msgOut = "Found programmed share file by ID",
            errorMsg = "Programmed share by ID not found")
    public ProgrammedShareFileDto findOrNotFound(final ProgrammedShareFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new ProgrammedShareFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PS, action = "Create new programmed share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new programmed share file",
            msgOut = "New country programmed share file OK",
            errorMsg = "Failed to create new programmed share file")
    public ProgrammedShareFileDto create(ProgrammedShareFileCreateDto createDto) {
        final ProgrammedShareFileCreate create = getMapper(MapPSFToProgrammedShareFileCreate.class).from(createDto);

        final ProgrammedShareFile result = this.programmedShareFileDao.create(create);
        this.populateFileUrl(result);

        return getMapper(MapPSFToProgrammedShareFileDto.class).from(result);
    }

    @Override
    public ProgrammedShareFileDto update(ProgrammedShareFileUpdateDto updateDto) {
        final ProgrammedShareFileUpdate update = getMapper(MapPSFToProgrammedShareFileUpdate.class).from(updateDto);

        final ProgrammedShareFile result = this.programmedShareFileDao.update(update);
        this.populateFileUrl(result);

        return getMapper(MapPSFToProgrammedShareFileDto.class).from(result);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PS, action = "Delete programmed share file")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting programmed share file",
            msgOut = "Programmed share file deleted OK",
            errorMsg = "Failed to delete programmed share file")
    public void delete(ProgrammedShareFileDeleteDto deleteDto) {

        final ProgrammedShareFileByIdFinderDto finderDto = new ProgrammedShareFileByIdFinderDto(deleteDto.getId());

        final ProgrammedShareFileDto fileDto = this.findOrNotFound(finderDto);

        this.googleCloudStorageService.deleteFile(new FileDelete(fileDto.getStoragePath()));

        final ProgrammedShareFileDelete delete = getMapper(MapPSFToProgrammedShareFileDelete.class).from(deleteDto);

        this.programmedShareFileDao.delete(delete);
    }

    private void populateFileUrl(final ProgrammedShareFile file) {
        if (file.getStoragePath() == null) { // FIXME: to remove
            return;
        }

        final FileByNameFinder finder = new FileByNameFinder(file.getStoragePath());
        final FileResponse fileResponse = this.googleCloudStorageService.getFile(finder);

        file.setUrl(fileResponse.getUrl());
    }
}

package com.epm.gestepm.model.shares.construction.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.shares.construction.dao.ConstructionShareFileDao;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareFileCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareFileDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFileFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareFileByIdFinder;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.*;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareFileService;
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
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_READ_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ConstructionShareFileServiceImpl implements ConstructionShareFileService {
    
    private final ConstructionShareFileDao constructionShareFileDao;

    private final GoogleCloudStorageService googleCloudStorageService;

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "List construction shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating construction share file files",
            msgOut = "Paginating construction share file files OK",
            errorMsg = "Failed to paginate construction share file files")
    public List<ConstructionShareFileDto> list(ConstructionShareFileFilterDto filterDto) {
        final ConstructionShareFileFilter filter = getMapper(MapCSFToConstructionShareFileFilter.class).from(filterDto);

        final List<ConstructionShareFile> list = this.constructionShareFileDao.list(filter);
        list.forEach(this::populateFileUrl);

        return getMapper(MapCSFToConstructionShareFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding construction share file by ID, result can be empty",
            msgOut = "Found construction share file by ID",
            errorMsg = "Failed to find construction share file by ID")
    public Optional<ConstructionShareFileDto> find(final ConstructionShareFileByIdFinderDto finderDto) {
        final ConstructionShareFileByIdFinder finder = getMapper(MapCSFToConstructionShareFileByIdFinder.class).from(finderDto);

        final Optional<ConstructionShareFile> found = this.constructionShareFileDao.find(finder);
        found.ifPresent(this::populateFileUrl);

        return found.map(getMapper(MapCSFToConstructionShareFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding construction share file by ID, result is expected or will fail",
            msgOut = "Found construction share file by ID",
            errorMsg = "Construction share by ID not found")
    public ConstructionShareFileDto findOrNotFound(final ConstructionShareFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new ConstructionShareFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Create new construction share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new construction share file",
            msgOut = "New country construction share file OK",
            errorMsg = "Failed to create new construction share file")
    public ConstructionShareFileDto create(ConstructionShareFileCreateDto createDto) {
        final ConstructionShareFileCreate create = getMapper(MapCSFToConstructionShareFileCreate.class).from(createDto);

        final ConstructionShareFile result = this.constructionShareFileDao.create(create);
        this.populateFileUrl(result);

        return getMapper(MapCSFToConstructionShareFileDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update construction share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Updating construction share file",
            msgOut = "Construction share file OK",
            errorMsg = "Failed to update construction share file")
    public ConstructionShareFileDto update(ConstructionShareFileUpdateDto updateDto) {
        final ConstructionShareFileUpdate update = getMapper(MapCSFToConstructionShareFileUpdate.class).from(updateDto);

        final ConstructionShareFile result = this.constructionShareFileDao.update(update);
        this.populateFileUrl(result);

        return getMapper(MapCSFToConstructionShareFileDto.class).from(result);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Delete construction share file")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting construction share file",
            msgOut = "Construction share file deleted OK",
            errorMsg = "Failed to delete construction share file")
    public void delete(ConstructionShareFileDeleteDto deleteDto) {

        final ConstructionShareFileByIdFinderDto finderDto = new ConstructionShareFileByIdFinderDto(deleteDto.getId());

        final ConstructionShareFileDto fileDto = this.findOrNotFound(finderDto);

        this.googleCloudStorageService.deleteFile(new FileDelete(fileDto.getStoragePath()));

        final ConstructionShareFileDelete delete = getMapper(MapCSFToConstructionShareFileDelete.class).from(deleteDto);

        this.constructionShareFileDao.delete(delete);
    }

    private void populateFileUrl(final ConstructionShareFile file) {
        if (file.getStoragePath() == null) { // FIXME: to remove
            return;
        }

        final FileByNameFinder finder = new FileByNameFinder(file.getStoragePath());
        final FileResponse fileResponse = this.googleCloudStorageService.getFile(finder);

        file.setUrl(fileResponse.getUrl());
    }
}

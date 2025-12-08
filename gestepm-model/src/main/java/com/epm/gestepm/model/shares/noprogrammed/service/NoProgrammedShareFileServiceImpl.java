package com.epm.gestepm.model.shares.noprogrammed.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileDto;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareFileDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareFile;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareFileDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareFileByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareFileUpdate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.*;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareFileService;
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
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_EDIT_NPS;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_READ_NPS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class NoProgrammedShareFileServiceImpl implements NoProgrammedShareFileService {

    private final GoogleCloudStorageService googleCloudStorageService;

    private final NoProgrammedShareFileDao noProgrammedShareFileDao;

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "List countries")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating no programmed share file files",
            msgOut = "Paginating no programmed share file files OK",
            errorMsg = "Failed to paginate no programmed share file files")
    public List<NoProgrammedShareFileDto> list(NoProgrammedShareFileFilterDto filterDto) {
        final NoProgrammedShareFileFilter filter = getMapper(MapNPSFToNoProgrammedShareFileFilter.class).from(filterDto);

        final List<NoProgrammedShareFile> list = this.noProgrammedShareFileDao.list(filter);
        list.forEach(this::populateFileUrl);

        return getMapper(MapNPSFToNoProgrammedShareFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share file by ID, result can be empty",
            msgOut = "Found no programmed share file by ID",
            errorMsg = "Failed to find no programmed share file by ID")
    public Optional<NoProgrammedShareFileDto> find(final NoProgrammedShareFileByIdFinderDto finderDto) {
        final NoProgrammedShareFileByIdFinder finder = getMapper(MapNPSFToNoProgrammedShareFileByIdFinder.class).from(finderDto);

        final Optional<NoProgrammedShareFile> found = this.noProgrammedShareFileDao.find(finder);
        found.ifPresent(this::populateFileUrl);

        return found.map(getMapper(MapNPSFToNoProgrammedShareFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share file by ID, result is expected or will fail",
            msgOut = "Found no programmed share file by ID",
            errorMsg = "No programmed share by ID not found")
    public NoProgrammedShareFileDto findOrNotFound(final NoProgrammedShareFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new NoProgrammedShareFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Create new no programmed share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new no programmed share file",
            msgOut = "New country no programmed share file OK",
            errorMsg = "Failed to create new no programmed share file")
    public NoProgrammedShareFileDto create(NoProgrammedShareFileCreateDto createDto) {
        final NoProgrammedShareFileCreate create = getMapper(MapNPSFToNoProgrammedShareFileCreate.class).from(createDto);

        final NoProgrammedShareFile result = this.noProgrammedShareFileDao.create(create);
        this.populateFileUrl(result);

        return getMapper(MapNPSFToNoProgrammedShareFileDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update no programmed share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Updating no programmed share file",
            msgOut = "No programmed share file OK",
            errorMsg = "Failed to update no programmed share file")
    public NoProgrammedShareFileDto update(NoProgrammedShareFileUpdateDto updateDto) {
        final NoProgrammedShareFileUpdate update = getMapper(MapNPSFToNoProgrammedShareFileUpdate.class).from(updateDto);

        final NoProgrammedShareFile result = this.noProgrammedShareFileDao.update(update);
        this.populateFileUrl(result);

        return getMapper(MapNPSFToNoProgrammedShareFileDto.class).from(result);
    }
    
    @Override
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Delete no programmed share file")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting no programmed share file",
            msgOut = "No programmed share file deleted OK",
            errorMsg = "Failed to delete no programmed share file")
    public void delete(NoProgrammedShareFileDeleteDto deleteDto) {

        final NoProgrammedShareFileByIdFinderDto finderDto = new NoProgrammedShareFileByIdFinderDto(deleteDto.getId());

        final NoProgrammedShareFileDto fileDto = this.findOrNotFound(finderDto);

        this.googleCloudStorageService.deleteFile(new FileDelete(fileDto.getStoragePath()));

        final NoProgrammedShareFileDelete delete = getMapper(MapNPSFToNoProgrammedShareFileDelete.class).from(deleteDto);

        this.noProgrammedShareFileDao.delete(delete);
    }

    private void populateFileUrl(final NoProgrammedShareFile file) {
        if (file.getStoragePath() == null) { // FIXME: to remove
            return;
        }

        final FileByNameFinder finder = new FileByNameFinder(file.getStoragePath());
        final FileResponse fileResponse = this.googleCloudStorageService.getFile(finder);

        file.setUrl(fileResponse.getUrl());
    }
}

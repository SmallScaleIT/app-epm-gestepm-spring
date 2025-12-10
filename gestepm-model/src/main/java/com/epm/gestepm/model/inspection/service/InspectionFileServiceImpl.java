package com.epm.gestepm.model.inspection.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.inspection.dao.InspectionFileDao;
import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionFileDelete;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFileFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionFileByIdFinder;
import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionFileUpdate;
import com.epm.gestepm.model.inspection.service.mapper.*;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileDto;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileUpdate;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionFileDeleteDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionFileByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionFileUpdateDto;
import com.epm.gestepm.modelapi.inspection.exception.InspectionFileNotFoundException;
import com.epm.gestepm.modelapi.inspection.service.InspectionFileService;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
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
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class InspectionFileServiceImpl implements InspectionFileService {

    private final GoogleCloudStorageService googleCloudStorageService;

    private final InspectionFileDao inspectionFileDao;

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "List countries")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating inspection file files",
            msgOut = "Paginating inspection file files OK",
            errorMsg = "Failed to paginate inspection file files")
    public List<InspectionFileDto> list(InspectionFileFilterDto filterDto) {
        final InspectionFileFilter filter = getMapper(MapIFToInspectionFileFilter.class).from(filterDto);

        final List<InspectionFile> list = this.inspectionFileDao.list(filter);
        list.forEach(this::populateFileUrl);

        return getMapper(MapIFToInspectionFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding inspection file by ID, result can be empty",
            msgOut = "Found inspection file by ID",
            errorMsg = "Failed to find inspection file by ID")
    public Optional<InspectionFileDto> find(final InspectionFileByIdFinderDto finderDto) {
        final InspectionFileByIdFinder finder = getMapper(MapIFToInspectionFileByIdFinder.class).from(finderDto);

        final Optional<InspectionFile> found = this.inspectionFileDao.find(finder);
        found.ifPresent(this::populateFileUrl);

        return found.map(getMapper(MapIFToInspectionFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding inspection file by ID, result is expected or will fail",
            msgOut = "Found inspection file by ID",
            errorMsg = "No programmed share by ID not found")
    public InspectionFileDto findOrNotFound(final InspectionFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new InspectionFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_I, action = "Create new inspection file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new inspection file",
            msgOut = "New country inspection file OK",
            errorMsg = "Failed to create new inspection file")
    public InspectionFileDto create(InspectionFileCreateDto createDto) {
        final InspectionFileCreate create = getMapper(MapIFToInspectionFileCreate.class).from(createDto);

        final InspectionFile result = this.inspectionFileDao.create(create);
        this.populateFileUrl(result);

        return getMapper(MapIFToInspectionFileDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update inspection file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Updating inspection file",
            msgOut = "Inspection file OK",
            errorMsg = "Failed to update inspection file")
    public InspectionFileDto update(InspectionFileUpdateDto updateDto) {
        final InspectionFileUpdate update = getMapper(MapIFToInspectionFileUpdate.class).from(updateDto);

        final InspectionFile result = this.inspectionFileDao.update(update);
        this.populateFileUrl(result);

        return getMapper(MapIFToInspectionFileDto.class).from(result);
    }
    
    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Delete inspection file")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting inspection file",
            msgOut = "No programmed share file deleted OK",
            errorMsg = "Failed to delete inspection file")
    public void delete(InspectionFileDeleteDto deleteDto) {

        final InspectionFileByIdFinderDto finderDto = new InspectionFileByIdFinderDto(deleteDto.getId());

        final InspectionFileDto fileDto = this.findOrNotFound(finderDto);

        this.googleCloudStorageService.deleteFile(new FileDelete(fileDto.getStoragePath()));

        final InspectionFileDelete delete = getMapper(MapIFToInspectionFileDelete.class).from(deleteDto);

        this.inspectionFileDao.delete(delete);
    }

    private void populateFileUrl(final InspectionFile file) {
        if (file.getStoragePath() == null) { // FIXME: to remove
            return;
        }

        final FileByNameFinder finder = new FileByNameFinder(file.getStoragePath());
        final FileResponse fileResponse = this.googleCloudStorageService.getFile(finder);

        file.setUrl(fileResponse.getUrl());
    }
}

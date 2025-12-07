package com.epm.gestepm.model.process;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionFileService;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareFileService;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareFileService;
import com.epm.gestepm.storageapi.dto.FileResponse;
import com.epm.gestepm.storageapi.dto.creator.FileCreate;
import com.epm.gestepm.storageapi.service.GoogleCloudStorageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = DELEGATOR)
public class MigrationStorageDelegator {

    private static final Log log = LogFactory.getLog(MigrationStorageDelegator.class);

    private final ConstructionShareFileService constructionShareFileService;

    private final GoogleCloudStorageService googleCloudStorageService;

    private final InspectionFileService inspectionFileService;

    private final ProgrammedShareFileService programmedShareFileService;

    public void runMigration() {
        // this.migrateConstructionShareFiles();
        this.migrateProgrammedShareFiles();
        // this.migrateInspectionFiles();
    }

    @SneakyThrows
    private void migrateConstructionShareFiles() {
        final ConstructionShareFileFilterDto filterDto = new ConstructionShareFileFilterDto();
        filterDto.setIds(List.of(4));

        final List<ConstructionShareFileDto> files = this.constructionShareFileService.list(filterDto);

        for (final ConstructionShareFileDto file : files) {
            final Path path = Paths.get(file.getName());
            final String contentType = Files.probeContentType(path);
            final byte[] content = this.compressImageToJpegBytes(file.getContent(), 1600, 0.8f);

            final MultipartCustomFile multipartCustomFile = new MultipartCustomFile(file.getName(), contentType, content);

            final FileResponse fileResponse = this.uploadFile(multipartCustomFile, "construction-shares");

            final ConstructionShareFileUpdateDto updateDto = new ConstructionShareFileUpdateDto();
            updateDto.setId(file.getId());
            updateDto.setName(file.getName());
            updateDto.setStoragePath(fileResponse.getFileName());

            this.constructionShareFileService.update(updateDto);

            log.info(String.format("Construction share file [%s] has been migrated successfully.", file.getId()));

            break;
        }
    }

    @SneakyThrows
    private void migrateProgrammedShareFiles() {
        final ProgrammedShareFileFilterDto filterDto = new ProgrammedShareFileFilterDto();
        filterDto.setIds(List.of(6));

        final List<ProgrammedShareFileDto> files = this.programmedShareFileService.list(filterDto);

        for (final ProgrammedShareFileDto file : files) {
            final Path path = Paths.get(file.getName());
            final String contentType = Files.probeContentType(path);
            final byte[] content = this.compressImageToJpegBytes(file.getContent(), 1600, 0.8f);

            final MultipartCustomFile multipartCustomFile = new MultipartCustomFile(file.getName(), contentType, content);

            final FileResponse fileResponse = this.uploadFile(multipartCustomFile,  "programmed-shares");

            final ProgrammedShareFileUpdateDto updateDto = new ProgrammedShareFileUpdateDto();
            updateDto.setId(file.getId());
            updateDto.setName(file.getName());
            updateDto.setStoragePath(fileResponse.getFileName());

            this.programmedShareFileService.update(updateDto);

            log.info(String.format("Programmed share file [%s] has been migrated successfully.", file.getId()));

            break;
        }
    }

    @SneakyThrows
    private void migrateInspectionFiles() {

        final List<InspectionFileDto> files = this.inspectionFileService.list(new InspectionFileFilterDto());

        for (final InspectionFileDto file : files) {

            final Path path = Paths.get(file.getName());
            final String contentType = Files.probeContentType(path);
            final byte[] content = Base64.getDecoder().decode(file.getContent());

            final MultipartCustomFile multipartCustomFile = new MultipartCustomFile(file.getName(), contentType, content);

            final FileResponse fileResponse = this.uploadFile(multipartCustomFile, "inspections");

            int i = 0;
        }
    }

    private FileResponse uploadFile(final MultipartCustomFile customFile, final String path) {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("file", customFile.name, customFile.contentType, customFile.content);

        final FileCreate fileCreate = new FileCreate();
        fileCreate.setName(path + "/" + UUID.randomUUID());
        fileCreate.setFile(mockMultipartFile);

        return this.googleCloudStorageService.uploadFile(fileCreate);
    }

    private byte[] compressImageToJpegBytes(byte[] inputBytes, int maxWidth, float quality) throws IOException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(inputBytes);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Thumbnails.of(in)
                    .size(maxWidth, maxWidth)      // mantiene ratio
                    .outputFormat("jpg")
                    .outputQuality(quality)        // 0.0 - 1.0
                    .toOutputStream(baos);
            return baos.toByteArray();
        }
    }

    private static class MultipartCustomFile {
        private final String name;
        private final String contentType;
        private final byte[] content;

        public MultipartCustomFile(String name, String contentType, byte[] content) {
            this.name = name;
            this.contentType = contentType;
            this.content = content;
        }
    }
}

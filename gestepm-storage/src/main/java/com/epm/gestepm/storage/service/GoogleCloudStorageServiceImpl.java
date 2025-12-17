package com.epm.gestepm.storage.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.storageapi.dto.FileResponse;
import com.epm.gestepm.storageapi.dto.creator.FileCreate;
import com.epm.gestepm.storageapi.dto.deleter.FileDelete;
import com.epm.gestepm.storageapi.dto.finder.FileByNameFinder;
import com.epm.gestepm.storageapi.exception.GCSFileNotFoundException;
import com.epm.gestepm.storageapi.exception.GCSUploadException;
import com.epm.gestepm.storageapi.service.GoogleCloudStorageService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@Validated
@Service
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService {

    @Value("${gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;

    @Override
    public FileResponse uploadFile(final FileCreate create) {
        try {
            final BlobInfo blobInfo = BlobInfo.newBuilder(this.bucketName, create.getName())
                    .setContentType(create.getFile().getContentType())
                    .build();

            final Blob blob = this.storage.create(blobInfo, create.getFile().getBytes());

            return this.buildFileResponse(blob, create.getFile().getBytes());
        } catch (Exception ex) {
            throw new GCSUploadException(ex);
        }
    }

    @Override
    public FileResponse getFile(final FileByNameFinder finder) {
        final Blob blob = storage.get(BlobId.of(bucketName, finder.getName()));

        if (blob == null) {
            throw new GCSFileNotFoundException(bucketName, finder.getName());
        }

        return this.buildFileResponse(blob, blob.getContent());
    }

    @Override
    public boolean deleteFile(final FileDelete delete) {
        return storage.delete(BlobId.of(bucketName, delete.getName()));
    }

    private FileResponse buildFileResponse(final Blob blob, final byte[] content) {
        final URL signedUrl = this.storage.signUrl(blob, 1, java.util.concurrent.TimeUnit.HOURS);

        return new FileResponse(
                blob.getName(),
                content,
                blob.getContentType(),
                signedUrl.toString()
        );
    }
}

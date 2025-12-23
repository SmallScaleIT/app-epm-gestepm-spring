package com.epm.gestepm.model.common.gcs;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class OptimizeImageHelper {

    public static MultipartFile compressImageToMultipartFile(final MultipartFile file, int maxWidth, float quality) {
        try (ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Thumbnails.of(in)
                    .size(maxWidth, maxWidth)
                    .outputFormat("jpg")
                    .outputQuality(quality)
                    .toOutputStream(baos);

            return new MockMultipartFile(
                    file.getName(),
                    file.getOriginalFilename(),
                    "image/jpeg",
                    baos.toByteArray()
            );
        } catch (Exception ex) {
            return null;
        }
    }
}

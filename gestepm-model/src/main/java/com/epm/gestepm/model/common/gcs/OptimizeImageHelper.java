package com.epm.gestepm.model.common.gcs;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class OptimizeImageHelper {

    public static MultipartFile compressImageToMultipartFile(
            final MultipartFile file,
            int maxWidth,
            float quality
    ) {

        if (!isCompressible(file)) {
            return file;
        }

        try (
                ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {

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
            // Fallo al comprimir → devolvemos el original
            return file;
        }
    }

    private static boolean isCompressible(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }

        // Solo imágenes soportadas por Thumbnailator
        if (!contentType.startsWith("image/")) {
            return false;
        }

        // Evitar GIFs animados
        if ("image/gif".equalsIgnoreCase(contentType)) {
            return false;
        }

        // Evitar imágenes muy pequeñas (ahorro nulo)
        long MIN_SIZE = 150 * 1024; // 150 KB
        if (file.getSize() < MIN_SIZE) {
            return false;
        }

        return true;
    }
}

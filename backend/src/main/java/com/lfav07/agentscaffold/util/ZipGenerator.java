package com.lfav07.agentscaffold.util;

import com.lfav07.agentscaffold.exception.ZipGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class ZipGenerator {


    /**
     * Creates a ZIP archive in memory from a map of file paths to their string contents.
     *
     * @param files a map of file paths (entries) to file contents.
     * @return the ZIP archive as a byte array.
     * @throws ZipGenerationException if an I/O error occurs during writing.
     */
    public byte[] generate(Map<String, String> files) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Map.Entry<String, String> entry : files.entrySet()) {
                ZipEntry zipEntry = new ZipEntry(entry.getKey());
                zos.putNextEntry(zipEntry);
                zos.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new ZipGenerationException("Failed to generate zip", e);
        }

        byte[] result = baos.toByteArray();
        log.info("ZIP generated — {} files, {} bytes", files.size(), result.length);
        return result;
    }
}


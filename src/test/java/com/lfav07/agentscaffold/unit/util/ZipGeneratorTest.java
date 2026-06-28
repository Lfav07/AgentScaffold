package com.lfav07.agentscaffold.unit.util;

import com.lfav07.agentscaffold.exception.ZipGenerationException;
import com.lfav07.agentscaffold.util.ZipGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockConstruction;

class ZipGeneratorTest {

    private final ZipGenerator generator = new ZipGenerator();

    @Test
    void generate_shouldReturnValidZip_whenGivenFileMap() throws IOException {
        Map<String, String> files = new HashMap<>();
        files.put("test.md", "content");

        byte[] result = generator.generate(files);

        assertThat(result).isNotEmpty();
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(result))) {
            ZipEntry entry = zis.getNextEntry();
            assertThat(entry).isNotNull();
            assertThat(entry.getName()).isEqualTo("test.md");
        }
    }

    @Test
    void generate_shouldReturnNonEmptyZip_whenGivenMultipleFiles() throws IOException {
        Map<String, String> files = new HashMap<>();
        files.put("file1.md", "content1");
        files.put("file2.md", "content2");
        files.put("file3.md", "content3");

        byte[] result = generator.generate(files);

        assertThat(result).isNotEmpty();
        int entryCount = 0;
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(result))) {
            while (zis.getNextEntry() != null) {
                entryCount++;
            }
        }
        assertThat(entryCount).isEqualTo(3);
    }

    @Test
    void generate_shouldThrowZipGenerationException_whenZipEntryFails() {
        Map<String, String> files = new HashMap<>();
        files.put("test.md", "content");

        try (MockedConstruction<ZipOutputStream> ignored = mockConstruction(
                ZipOutputStream.class,
                (mock, ctx) -> {
                    try {
                        doThrow(new IOException("ZIP failure")).when(mock).putNextEntry(any());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })) {
            assertThatThrownBy(() -> generator.generate(files))
                    .isInstanceOf(ZipGenerationException.class)
                    .hasMessageContaining("Failed to generate zip");
        }
    }
}

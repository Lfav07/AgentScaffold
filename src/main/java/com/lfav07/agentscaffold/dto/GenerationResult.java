package com.lfav07.agentscaffold.dto;

import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public record GenerationResult(
        byte[] zip
) {
}

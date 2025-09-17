package com.lms.document.mapper;

import com.lms.document.dto.DocumentRequestDto;
import com.lms.document.dto.DocumentResponseDto;
import com.lms.document.entity.Document;

public class DocumentMapper {

    public static Document toEntity(DocumentRequestDto dto) {
        if (dto == null) return null;

        return Document.builder()
                .caseId(dto.getCaseId())
                .uploadedBy(dto.getUploadedBy())
                .title(dto.getTitle())
                .docType(dto.getDocType())
                .remarks(dto.getRemarks())
                .fileData(dto.getFileData())
                .build();
    }

    public static DocumentResponseDto toDto(Document doc) {
        if (doc == null) return null;

        return DocumentResponseDto.builder()
                .documentId(doc.getDocumentId())
                .caseId(doc.getCaseId())
                .uploadedBy(doc.getUploadedBy())
                .title(doc.getTitle())
                .docType(doc.getDocType())
                .remarks(doc.getRemarks())
                .filePath(doc.getFilePath())
                .uploadedAt(doc.getUploadedAt())
                .build();
    }
}


package com.lms.document.service.implement;

import com.lms.document.dto.DocumentResponseDto;
import com.lms.document.entity.Document;
import com.lms.document.mapper.DocumentMapper;
import com.lms.document.repository.DocumentRepository;
import com.lms.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentResponseDto uploadDocument(Long caseId, Long uploadedBy, String title, String docType,
                                              String remarks, MultipartFile fileData) {
        Document doc = Document.builder()
                .caseId(caseId)
                .uploadedBy(uploadedBy)
                .title(title)
                .docType(docType)
                .remarks(remarks)
                .fileData(fileData != null ? toByteArray(fileData) : null)
                .build();

        return DocumentMapper.toDto(documentRepository.save(doc));
    }

    @Override
    public Optional<DocumentResponseDto> getDocumentById(Long id) {
        return documentRepository.findById(id)
                .map(DocumentMapper::toDto);
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByCaseId(Long caseId) {
        return documentRepository.findByCaseId(caseId)
                .stream()
                .map(DocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByUserId(Long userId) {
        return documentRepository.findByUploadedBy(userId)
                .stream()
                .map(DocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentResponseDto updateDocument(Long id, Long caseId, Long uploadedBy, String title,
                                              String docType, String remarks, MultipartFile fileData) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (caseId != null) doc.setCaseId(caseId);
        if (uploadedBy != null) doc.setUploadedBy(uploadedBy);
        if (title != null && !title.isBlank()) doc.setTitle(title);
        if (docType != null && !docType.isBlank()) doc.setDocType(docType);
        if (remarks != null && !remarks.isBlank()) doc.setRemarks(remarks);
        if (fileData != null && !fileData.isEmpty()) {
            doc.setFileData(toByteArray(fileData));
        }

        return DocumentMapper.toDto(documentRepository.save(doc));
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    private byte[] toByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }
    }
}

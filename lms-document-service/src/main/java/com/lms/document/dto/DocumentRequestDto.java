package com.lms.document.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequestDto {
    private Long caseId;
    private Long uploadedBy;
    private String title;
    private String docType;
    private String remarks;
    private byte[] fileData; // or MultipartFile in controller layer
}


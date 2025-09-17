package com.lms.cases.mapper;



import com.lms.cases.dto.CaseRequestDto;
import com.lms.cases.dto.CaseResponseDto;
import com.lms.cases.entity.Case;
import com.lms.cases.enums.Status;

public class CaseMapper {

    // Convert Request DTO → Entity
    public static Case toEntity(CaseRequestDto dto) {
        return Case.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .advocateId(dto.getAdvocateId())
                .status(Status.OPEN) // default status when creating
                .build();
    }

    // Convert Entity → Response DTO
    public static CaseResponseDto toDto(Case entity) {
        return CaseResponseDto.builder()
                .caseId(entity.getCaseId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .advocateId(entity.getAdvocateId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}


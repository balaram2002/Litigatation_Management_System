package com.lms.cases.mapper;

import com.lms.cases.dto.CaseAdvocateRequestDto;
import com.lms.cases.dto.CaseAdvocateResponseDto;
import com.lms.cases.entity.CaseAdvocate;

public class CaseAdvocateMapper {

    public static CaseAdvocate toEntity(CaseAdvocateRequestDto dto) {
        if (dto == null) return null;
        return CaseAdvocate.builder()
                .caseId(dto.getCaseId())
                .advocateId(dto.getAdvocateId())
                .roleInCase(dto.getRoleInCase())
                .build();
    }

    public static CaseAdvocateResponseDto toDto(CaseAdvocate entity) {
        if (entity == null) return null;
        return CaseAdvocateResponseDto.builder()
                .id(entity.getId())
                .caseId(entity.getCaseId())
                .advocateId(entity.getAdvocateId())
                .roleInCase(entity.getRoleInCase())
                .assignedAt(entity.getAssignedAt())
                .unassignedAt(entity.getUnassignedAt())
                .build();
    }
}


package com.lms.cases.mapper;


import com.lms.cases.dto.HearingRequestDto;
import com.lms.cases.dto.HearingResponseDto;
import com.lms.cases.entity.Hearing;

public class HearingMapper {

    // Convert Request DTO to Entity
    public static Hearing toEntity(HearingRequestDto dto) {
        if (dto == null) return null;

        return Hearing.builder()
                .caseId(dto.getCaseId())
                .advocateId(dto.getAdvocateId())
                .date(dto.getDate())
                .location(dto.getLocation())
                .notes(dto.getNotes())
                .build();
    }

    // Convert Entity to Response DTO
    public static HearingResponseDto toResponseDto(Hearing hearing) {
        if (hearing == null) return null;

        return HearingResponseDto.builder()
                .hearingId(hearing.getHearingId())
                .caseId(hearing.getCaseId())
                .advocateId(hearing.getAdvocateId())
                .date(hearing.getDate())
                .location(hearing.getLocation())
                .notes(hearing.getNotes())
                .createdAt(hearing.getCreatedAt())
                .updatedAt(hearing.getUpdatedAt())
                .build();
    }

}


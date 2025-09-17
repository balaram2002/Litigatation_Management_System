package com.lms.cases.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.lms.cases.feign.dto.DocumentResponseDto;

@FeignClient(
 name = "document-service",
 configuration = com.lms.cases.feignconfig.FeignConfig.class
)
public interface DocumentClient {

	@GetMapping("/case/{caseId}")
	List<DocumentResponseDto> getDocumentsByCaseId(Long caseId);
}

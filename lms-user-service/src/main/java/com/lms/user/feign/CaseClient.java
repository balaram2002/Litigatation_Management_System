package com.lms.user.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms.user.feign.dto.CaseResponseDto;
import com.lms.user.feignconfig.FeignConfig;


@FeignClient(
 name = "case-service",
 configuration = FeignConfig.class
)
public interface CaseClient {

 @GetMapping("cases/user/{userId}")
 List<CaseResponseDto> getCasesByUserId(@PathVariable("userId") Long userId);
}


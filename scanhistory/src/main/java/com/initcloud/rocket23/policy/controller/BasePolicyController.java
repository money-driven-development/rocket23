package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.BasePolicyDto;
import com.initcloud.rocket23.policy.service.BasePolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Base Policy API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/basePolicys")
public class BasePolicyController {

    private final BasePolicyService basePolicyService;

    /**
     * TODO: BackOfiice API  - Base Policy 등록
     * */

    /**
     * TODO: BackOfiice API  - Base Policy 수정
     * */

    /**
     * Base Policy 요약 전체 조회
     * */
    @Operation(
            summary = "Get Base policy-set",
            description = "Show Base policy-set.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @GetMapping
    public ResponseDto<Page<BasePolicyDto.Summary>> teamPagedPolicies(
                                                         final Pageable pageable
    ) {

        Page<BasePolicyDto.Summary> response = basePolicyService.getPagedBasePolicy(pageable);

        return new ResponseDto<>(response);
    }

    /**
     * Base Policy 상세 전체 조회
     * */
    @Operation(
            summary = "Get Base policy-set",
            description = "Show Base policy-set.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @GetMapping("/details")
    public ResponseDto<Page<BasePolicyDto.Details>> teamPagedPoliciesDetails(
            final Pageable pageable
    ) {

        Page<BasePolicyDto.Details> response = basePolicyService.getPagedBasePolicyDetails(pageable);

        return new ResponseDto<>(response);
    }



    /**
     * TODO: BackOfiice API  - Base Policy 삭제
     * */

}

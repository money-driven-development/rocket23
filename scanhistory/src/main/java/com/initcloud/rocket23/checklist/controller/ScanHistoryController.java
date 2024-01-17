package com.initcloud.rocket23.checklist.controller;

import com.initcloud.rocket23.checklist.dto.ScanResultDto;
import com.initcloud.rocket23.checklist.dto.ScanResultDto.Summary;
import com.initcloud.rocket23.checklist.service.ScanHistoryService;
import com.initcloud.rocket23.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Scan History API")
@RestController
@RequestMapping("/rocket/team/")
@RequiredArgsConstructor
public class ScanHistoryController {

    private final ScanHistoryService scanHistoryService;

    /*
        단일 스캔 이력 조회(검출통계[성공, 실패, 스킵, 전체스캔])
     */
    @Operation(summary = "Get a scan-history", description = "Retrieve a scan-history.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "hashCode", in = ParameterIn.PATH, description = "History unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/projects/{projectCode}/history/{hashCode}")
    public ResponseDto<ScanResultDto> getScanHistory(
            @PathVariable("teamCode") String teamCode,
            @PathVariable("projectCode") String projectCode,
            @PathVariable("hashCode") String hashCode
    ) {
        ScanResultDto dto = scanHistoryService.getScanHistory(teamCode, projectCode, hashCode);
        return new ResponseDto<>(dto);
    }

    /*
        단일 스캔 전체 내역 조회
     */
    @GetMapping("/{teamCode}/projects/{projectCode}/history/{hashCode}/total")
    public ResponseDto<ScanResultDto> getScanHistoryTotal(
            @PathVariable("teamCode") String teamCode,
            @PathVariable("projectCode") String projectCode,
            @PathVariable("hashCode") String hashCode
    ) {
        ScanResultDto dto = scanHistoryService.getScanHistoryTotal(teamCode, projectCode, hashCode);
        return new ResponseDto<>(dto);
    }

    /*
        프로젝트 내 스캔 목록 조회 Pagination
     */
    @Operation(summary = "Get paged scan-history list",
            description = "Retrieve paged scan-history list.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "pageable", in = ParameterIn.QUERY, description = "paging value", required = true, content = @Content(schema = @Schema(implementation = PageableAsQueryParam.class)))
    @GetMapping("/{teamCode}/projects/{projectCode}/history")
    public ResponseDto<Page<ScanResultDto.Summary>> getScanHistoryPaging(
            @PathVariable("teamCode") String teamCode,
            @PathVariable("projectCode") String projectCode,
            final Pageable pageable
    ) {
        Page<ScanResultDto.Summary> dtos = scanHistoryService.getScanHistoryPaging(teamCode, projectCode, pageable);
        return new ResponseDto<>(dtos);
    }

    /*
        프로젝트 내 스캔 목록 조회 Pagination X
     */
    @Operation(summary = "Get scan-history list",
            description = "Retrieve scan-history list.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/projects/{projectCode}/history/all")
    public ResponseDto<List<ScanResultDto.Summary>> getScanHistoryAll(
            @PathVariable("teamCode") String teamCode,
            @PathVariable("projectCode") String projectCode
    ) {
        List<Summary> dtos = scanHistoryService.getScanHistoryAll(teamCode, projectCode);
        return new ResponseDto<>(dtos);
    }

    /*
        프로젝트 내 파일 해시를 통한 스캔 목록 조회
     */
    @Operation(summary = "Get scan-history list By FileHash",
            description = "Retrieve scan-history list.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/projects/{projectCode}/file/{fileHash}")
    public ResponseDto<String> getScanHistoryFile(
            @PathVariable("fileHash") String fileHash
    ) {
        String dto = scanHistoryService.getScanSuccess(fileHash);
        return new ResponseDto<>(dto);
    }

    /*
        단일 스캔 실패 내역에 대한 조회
     */
//    @Operation(
//            summary = "Get a failed-scan-history",
//            description = "Retrieve a failed-scan-history.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
//    )
//    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
//    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
//    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
//    @Parameter(name = "hashCode", in = ParameterIn.PATH, description = "History unique code", required = true, schema = @Schema(type = "string"))
//    @GetMapping("/{teamCode}/projects/{projectCode}/history/{hashCode}/fail")
//    public ResponseDto<ScanFailDetailDto> getScanFailDetail(
//            @PathVariable("teamCode") String teamCode,
//            @PathVariable("projectCode") String projectCode,
//            @PathVariable("hashCode") String hashCode
//    ) {
//        ScanFailDetailDto dtos = scanHistoryService.getScanFailDetail(teamCode, projectCode, hashCode);
//        return new ResponseDto<>(dtos);
//    }

    /**
     * get 방식을 통해 file scan history 내역을 최근 10개를 출력하도록함.
     *
     */
    //	@GetMapping("/{team}/projects/{project}/scans/recent")
    //	public ResponseDto<List<HistoryDto>> getHistoryList(@PathVariable("team") Long teamId,
    //		@PathVariable("project") Long projectId) {
    //		List<HistoryDto> dtos = scanHistoryService.getHistoryList(teamId, projectId);
    //		return new ResponseDto<>(dtos);
    //	}
    //
    //	@GetMapping("/{team}/projects/{project}/scans")
    //	public ResponseDto<Page<HistoryDto>> getOffsetPageHistoryList(@PathVariable("team") Long teamId,
    //		@PathVariable("project") Long projectId,
    //		@RequestParam(required = true) Integer page) {
    //		PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.DESC, "historyId");
    //		Page<HistoryDto> dtos = scanHistoryService.getOffsetPageHistoryList(teamId, projectId, pageRequest);
    //		return new ResponseDto<>(dtos);
    //	}
    //
    //    /**
    //     * get 방식을 통해 url로 file의 uuid값을 전달받음
    //     *
    //     * @param fileHash path를 통해 전달받은 file의 uuid값
    //     * @return file에 해당하는 스캔내역
    //	 * get 방식을 통한 file pagination 기능 구현 teamId 와 historyseq를 통한 cursor 지정.
    //	*/
    //     @GetMapping("/{team}/projects/{project}/scans/cursor")
    //	public ResponseDto<CursorResultDto> getCursorPageHistoryList(@PathVariable("team") Long teamId,
    //		@RequestParam(required = false) Long cursorId) {
    //		CursorResultDto dtos;
    //		dtos = scanHistoryService.getCursorPageHistoryList(teamId, cursorId, PageRequest.of(0, 10));
    //        return new ResponseDto<>(dtos);
    //    }
}

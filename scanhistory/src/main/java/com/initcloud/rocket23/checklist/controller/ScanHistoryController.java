package com.initcloud.rocket23.checklist.controller;

import com.initcloud.rocket23.checklist.dto.ScanFailDetailDto;
import com.initcloud.rocket23.checklist.dto.ScanResultDto;
import com.initcloud.rocket23.checklist.service.ScanHistoryService;
import com.initcloud.rocket23.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class ScanHistoryController {

	private final ScanHistoryService scanHistoryService;

	//private final int DEFAULT_SIZE = 10;

	/*
		단일 스캔 이력 조회(검출통계[성공, 실패, 스킵, 전체스캔])
	 */
	@GetMapping("/{teamCode}/projects/{projectCode}/scans/history/{hashCode}")
	public ResponseDto<ScanResultDto> getScanHistory(@PathVariable("teamCode") String teamCode,
		@PathVariable("projectCode") String projectCode, @PathVariable("hashCode") String hashCode) {
		ScanResultDto dto = scanHistoryService.getScanHistory(teamCode, projectCode, hashCode);
		return new ResponseDto<>(dto);
	}

	/*
		단일 스캔 이력 Pagination
	 */
	@GetMapping("/{teamCode}/projects/{projectCode}/scans/history")
	public ResponseDto<Page<ScanResultDto.Summary>> getScanHistoryPaging(@PathVariable("teamCode") String teamCode,
		@PathVariable("projectCode")
		String projectCode, final Pageable pageable) {
		Page<ScanResultDto.Summary> dtos = scanHistoryService.getScanHistoryPaging(teamCode, projectCode, pageable);
		return new ResponseDto<>(dtos);
	}

	/*
		단일 스캔 실패 내역에 대한 조회
	 */
	@GetMapping("/{teamCode}/projects/{projectCode}/scans/history/{hashCode}/fail")
	public ResponseDto<ScanFailDetailDto> getScanFailDeatil(@PathVariable("teamCode") String teamCode,
		@PathVariable("projectCode") String projectCode, @PathVariable("hashCode") String hashCode) {
		ScanFailDetailDto dtos = scanHistoryService.getScanFailDetail(teamCode, projectCode, hashCode);
		return new ResponseDto<>(dtos);
	}

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

package com.initcloud.scanhistory.checklist.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.HistoryDto;
import com.initcloud.scanhistory.checklist.entity.ScanHistory;
import com.initcloud.scanhistory.checklist.service.ScanHistoryService;
import com.initcloud.scanhistory.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class ScanHistoryController {

	private final ScanHistoryService scanHistoryService;

	private final int DEFAULT_SIZE = 10;

	/**
	 * get 방식을 통해 file scan history 내역을 최근 10개를 출력하도록함.
	 *
	 */
	@GetMapping("/{team}/projects/{project}/scans/recent")
	public ResponseDto<List<HistoryDto>> getHistoryList(@PathVariable("team") Long teamId,
		@PathVariable("project") Long projectId) {
		List<HistoryDto> dtos = scanHistoryService.getHistoryList(teamId, projectId);
		return new ResponseDto<>(dtos);
	}

	@GetMapping("/{team}/projects/{project}/scans")
	public ResponseDto<Page<HistoryDto>> getOffsetPageHistoryList(@PathVariable("team") Long teamId,
		@PathVariable("project") Long projectId,
		@RequestParam(required = true) Integer page) {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.DESC, "historyId");
		Page<HistoryDto> dtos = scanHistoryService.getOffsetPageHistoryList(teamId, projectId, pageRequest);
		return new ResponseDto<>(dtos);
	}

	/**
	 * get 방식을 통한 file pagination 기능 구현 teamId 와 historyseq를 통한 cursor 지정.
	 */
	@GetMapping("/{team}/projects/{project}/scans/cursor")
	public ResponseDto<CursorResultDto> getCursorPageHistoryList(@PathVariable("team") Long teamId,
		@RequestParam(required = false) Long cursorId) {
		CursorResultDto dtos;
		dtos = scanHistoryService.getCursorPageHistoryList(teamId, cursorId, PageRequest.of(0, 10));
		return new ResponseDto<>(dtos);
	}
}

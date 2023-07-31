package com.initcloud.scanhistory.checklist.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.HistoryDto;
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
	@GetMapping("/{team}/projects/scans/recent")
	public ResponseDto<List<HistoryDto>> getHistoryList(@PathVariable("team") Long teamId) {
		List<HistoryDto> dtos = scanHistoryService.getHistoryList(teamId);
		return new ResponseDto<>(dtos);
	}
	/**
	 * get 방식을 통한 file pagination 기능 구현 teamId 와 historyseq를 통한 cursor 지정.
	 */
	@GetMapping("/{team}/projects/scans")
	public ResponseDto<CursorResultDto> getPageHistoryList(@PathVariable("team") Long teamId,
		@RequestParam(required = false) Long cursorId,
		@RequestParam(required = false) Integer size) {
		CursorResultDto dtos;
		// if (size == null)
		// 	size = DEFAULT_SIZE;
		dtos = scanHistoryService.getPageHistoryList(teamId, cursorId, PageRequest.of(0, size));
		return new ResponseDto<>(dtos);
	}
}

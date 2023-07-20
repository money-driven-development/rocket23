package com.initcloud.scanhistory.checklist.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;
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
	@GetMapping("/history/recent")
	public ResponseDto<List<ScanHistoryDto>> getHistoryList() {
		List<ScanHistoryDto> dtos = scanHistoryService.getHistoryList();
		return new ResponseDto<>(dtos);
	}

	/**
	 * get 방식을 통해 url로 file의 uuid값을 전달받음
	 * @param UUID path를 통해 전달받은 file의 uuid값
	 * @return file에 해당하는 스캔내역
	 */
	@GetMapping("/history/{uuid}")
	public ResponseDto<List<ScanHistoryDto>> getFileHistoryList(@PathVariable String UUID) {
		List<ScanHistoryDto> dtos = scanHistoryService.getFileHistoryList(UUID);
		return new ResponseDto<>(dtos);
	}

	@GetMapping("/history")
	public ResponseDto<CursorResultDto> getPageHistoryList(@RequestParam Long cursorId,
		@RequestParam(required = false) Integer size, @RequestParam String... fileType) {
		CursorResultDto dtos;
		if (size == null)
			size = DEFAULT_SIZE;
		if (fileType == null) {
			dtos = scanHistoryService.getPageHistoryList(cursorId, PageRequest.of(0, size));
		} else {
			dtos = scanHistoryService.getPageHistoryList(cursorId, PageRequest.of(0, size), fileType);
		}
		return new ResponseDto<>(dtos);
	}
}

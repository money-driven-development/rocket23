package com.initcloud.scanhistory.checklist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;
import com.initcloud.scanhistory.checklist.service.ScanHistoryService;
import com.initcloud.scanhistory.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class ScanHistoryController {

	private final ScanHistoryService scanHistoryService;

	/**
	 * get 방식을 통해 file scan history 내역을 최근 10개를 출력하도록함.
	 *
	 */
	@GetMapping("/scan/history")
	public ResponseDto<List<ScanHistoryDto>> getHistoryList() {
		List<ScanHistoryDto> dtos = scanHistoryService.getHistoryList();
		return new ResponseDto<>(dtos);
	}

	/**
	 * get 방식을 통해 url로 file의 uuid값을 전달받음
	 * @param fileHash path를 통해 전달받은 file의 uuid값
	 * @return file에 해당하는 스캔내역
	 */
	@GetMapping("/scan/history/{uuid}")
	public ResponseDto<List<ScanHistoryDto>> getFileHistoryList(@PathVariable String fileHash) {
		List<ScanHistoryDto> dtos = scanHistoryService.getFileHistoryList(fileHash);
		return new ResponseDto<>(dtos);
	}
}

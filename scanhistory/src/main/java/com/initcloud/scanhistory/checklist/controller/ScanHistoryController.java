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

	@GetMapping("/scan/{uuid}")
	public ResponseDto<List<ScanHistoryDto>> getHistoryList(@PathVariable String fileHash) {
		List<ScanHistoryDto> dtos = scanHistoryService.getHistoryList(fileHash);
		return new ResponseDto<>(dtos);
	}
}

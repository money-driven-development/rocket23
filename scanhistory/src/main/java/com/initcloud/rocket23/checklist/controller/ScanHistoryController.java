package com.initcloud.rocket23.checklist.controller;

import java.util.List;

import com.initcloud.rocket23.checklist.service.ScanHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.checklist.dto.ScanHistoryDto;
import com.initcloud.rocket23.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class ScanHistoryController {

	private final ScanHistoryService scanHistoryService;

	/**
	 * get 방식을 통해 url로 file의 uuid값을 전달받음
	 * @param fileHash path를 통해 전달받은 file의 uuid값
	 * @return file에 해당하는 스캔내역
	 */
	@GetMapping("/scan/{uuid}")
	public ResponseDto<List<ScanHistoryDto>> getHistoryList(@PathVariable String fileHash) {
		List<ScanHistoryDto> dtos = scanHistoryService.getHistoryList(fileHash);
		return new ResponseDto<>(dtos);
	}
}

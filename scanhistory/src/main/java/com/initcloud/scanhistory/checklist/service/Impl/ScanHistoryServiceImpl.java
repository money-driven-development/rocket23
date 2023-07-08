package com.initcloud.scanhistory.checklist.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;
import com.initcloud.scanhistory.checklist.service.ScanHistoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;

	/**
	 * 파일의 uuid를 입력받고 스캔 내역을 조회하는 기능
	 * @param fileHash file의 uuid 정보를 입력받음
	 * @return ScanHistory에서 조회한 내역을 dto로 반환함
	 */
	public List<ScanHistoryDto> getHistoryList(String fileHash) {
		List<ScanHistoryDto> dtos = new ArrayList<>();
		List<ScanHistoryEntity> scanHistoryEntities = scanHistoryRepository.findByFileHash(fileHash);
		for (ScanHistoryEntity e : scanHistoryEntities) {
			dtos.add(new ScanHistoryDto(e));
		}
		return dtos;
	}
}

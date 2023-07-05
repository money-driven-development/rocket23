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

	public List<ScanHistoryDto> getHistoryList(String fileHash) {
		List<ScanHistoryDto> dtos = new ArrayList<ScanHistoryDto>();
		List<ScanHistoryEntity> scanHistoryEntities = scanHistoryRepository.findByFileHash(fileHash);
		for (ScanHistoryEntity e : scanHistoryEntities) {
			dtos.add(new ScanHistoryDto(e));
		}
		return dtos;
	}
}

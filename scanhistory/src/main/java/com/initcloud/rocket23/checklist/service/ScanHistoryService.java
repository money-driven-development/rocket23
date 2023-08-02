package com.initcloud.rocket23.checklist.service;

import java.util.List;

import com.initcloud.rocket23.checklist.dto.ScanHistoryDto;

public interface ScanHistoryService {
	List<ScanHistoryDto> getHistoryList(String fileHash);
}

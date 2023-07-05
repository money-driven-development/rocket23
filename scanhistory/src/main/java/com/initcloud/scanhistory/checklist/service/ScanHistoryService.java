package com.initcloud.scanhistory.checklist.service;

import java.util.List;

import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;

public interface ScanHistoryService {
	List<ScanHistoryDto> getHistoryList();
}

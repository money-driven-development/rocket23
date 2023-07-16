package com.initcloud.scanhistory.checklist.service.Impl;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
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
	 * 파일의 uuid를 입력받고 상위 10개의 스캔 내역을 조회하는 기능
	 * @param UUID file의 uuid 정보를 입력받음
	 * @return ScanHistory에서 조회한 내역을 dto로 반환함
	 */
	public List<ScanHistoryDto> getFileHistoryList(String UUID) {
		return toConvert(scanHistoryRepository.findTop10ByFileHash(UUID));
	}

	/**
	 * 상위 10개의 파일 스캔 결과만 반영해줍니다.
	 * @return ScanHistory에서 조회한 내여을 dto로 반환함.
	 */
	public List<ScanHistoryDto> getHistoryList() {
		return toConvert(scanHistoryRepository.findTop10ByOrderByIdDesc());
	}

	public CursorResultDto getPageHistoryList(Long cursorId, Pageable page) {
		List<ScanHistoryDto> dtos = getPage(cursorId, page);
		Long lastId = -1L;
		if (!dtos.isEmpty()) {
			dtos.get(dtos.size() - 1).getId();
		}
		return new CursorResultDto(dtos, hasNext(lastId));
	}

	private List<ScanHistoryDto> getPage(Long id, Pageable page) {
		if (id == null) {
			return toConvert(scanHistoryRepository.findAllByOrderByIdDesc(page));
		}
		return toConvert(scanHistoryRepository.findByIdLessThanOrderByIdDesc(id, page));
	}

	private List<ScanHistoryDto> toConvert(List<ScanHistoryEntity> entities) {
		List<ScanHistoryDto> dtos = new ArrayList<>();
		for (ScanHistoryEntity e : entities) {
			dtos.add(new ScanHistoryDto(e));
		}
		return dtos;
	}

	private Boolean hasNext(Long id) {
		if (id == null)
			return false;
		return scanHistoryRepository.existsByIdLessThan(id);
	}
}
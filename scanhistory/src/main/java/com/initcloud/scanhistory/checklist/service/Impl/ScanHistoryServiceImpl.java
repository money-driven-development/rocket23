package com.initcloud.scanhistory.checklist.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;
import com.initcloud.scanhistory.checklist.service.ScanHistoryService;

import lombok.Getter;
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

	/**
	 * ID 기반의 Cursor-based pagination 구현, 최근 목록을 기준으로 pagination이 동작함.
	 * @param cursorId
	 * @param page
	 * @return page된 부분과 마지막의 id를 통해서 page 지점을 전달
	 * TODO: 2023-07-17 일단 id 기반으로 구현했지만, 결국 user에 대한 정보를 통해서 pagination을 구현해야합니다. 추후 user 기능관 결합되면 이를 수정할 예정입니다.
	 */
	public CursorResultDto getPageHistoryList(Long cursorId, Pageable page) {
		List<ScanHistoryDto> dtos = getPage(cursorId, page);
		Long lastId = null;
		if (!dtos.isEmpty()) {
			lastId = dtos.get(dtos.size() - 1).getId();
		}
		return new CursorResultDto(dtos, hasNext(lastId));
	}

	/*
	todo filetype 배열을 통해서 선택적 출력이 가능한 JPA로직 구현 및 기능 구현.
	 */
	public CursorResultDto getPageHistoryList(Long cursorId, Pageable Page, String[] fileType) {
		return new CursorResultDto();
	}

	/**
	 * id 값에 따른  Page 처리된
	 * @param id
	 * @param page
	 * @return
	 */
	private List<ScanHistoryDto> getPage(Long id, Pageable page) {
		if (id == null) {
			return toConvert(scanHistoryRepository.findAllByOrderByIdDesc(page));
		}
		return toConvert(scanHistoryRepository.findByIdLessThanOrderByIdDesc(id, page));
	}

	/**
	 * ScanHistoryEntity -> ScanHistoryDto 로 변환
	 * @param entities
	 * @return
	 */
	private List<ScanHistoryDto> toConvert(List<ScanHistoryEntity> entities) {
		List<ScanHistoryDto> dtos = new ArrayList<>();
		for (ScanHistoryEntity e : entities) {
			dtos.add(new ScanHistoryDto(e));
		}
		return dtos;
	}

	/**
	 * 다음 page가 있는 지를 확인하는 함수
	 * @param id
	 * @return
	 */
	private Boolean hasNext(Long id) {
		if (id == null)
			return false;
		return scanHistoryRepository.existsByIdLessThan(id);
	}

	private enum fileType {

		TF(".tf"),
		YAML(".yaml"),
		DOCKERFILE("Dockerfile"),
		JSON(".json"),
		YML(".yml");
		@Getter
		private String type;

		private fileType(String type) {
			this.type = type;
		}
	}
}
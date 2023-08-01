package com.initcloud.scanhistory.checklist.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.HistoryDto;
import com.initcloud.scanhistory.checklist.entity.ScanHistory;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;
import com.initcloud.scanhistory.checklist.service.ScanHistoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;

	/**
	 * 상위 10개의 파일 스캔 결과만 반영해줍니다.
	 * @return ScanHistory에서 조회한 내여을 dto로 반환함.
	 */
	public List<HistoryDto> getHistoryList(Long teamId, Long projectId) {
		return toConvert(scanHistoryRepository.findTop10ByTeamIdAndProjectIdOrderByHistoryIdDesc(teamId, projectId));
	}

	public Page<HistoryDto> getOffsetPageHistoryList(Long teamId, Long projectId, Pageable page) {
		Page<ScanHistory> pagies = scanHistoryRepository.findAllByTeamIdAndProjectId(teamId, projectId, page);
		Page<HistoryDto> dtos = toDtoPage(pagies);
		return dtos;
	}

	private Page<HistoryDto> toDtoPage(Page<ScanHistory> page) {
		return page.map(m -> HistoryDto.builder()
			.id(m.getHistoryId())
			.fileName(m.getFileName())
			.fileHash(m.getFileHash())
			.scanDateTime(m.getCreatedAt())
			.provider(m.getCsp())
			.score(m.getScore())
			.failed(m.getFailed())
			.passed(m.getPassed())
			.skipped(m.getSkipped()).build());
	}

	/**
	 * ID 기반의 Cursor-based pagination 구현, 최근 목록을 기준으로 pagination이 동작함.
	 * @param cursorId
	 * @param page
	 * @return page된 부분과 마지막의 id를 통해서 page 지점을 전달
	 * TODO: 2023-07-17 일단 id 기반으로 구현했지만, 결국 user에 대한 정보를 통해서 pagination을 구현해야합니다. 추후 user 기능관 결합되면 이를 수정할 예정입니다.
	 */
	public CursorResultDto getCursorPageHistoryList(Long teamId, Long cursorId, Pageable page) {
		List<HistoryDto> dtos = getPage(teamId, cursorId, page);
		Long lastId = null;
		if (!dtos.isEmpty()) {
			lastId = dtos.get(dtos.size() - 1).getId();
		}
		return new CursorResultDto(dtos, hasNext(teamId, lastId));
	}

	private List<HistoryDto> getPage(Long teamId, Long cursorId, Pageable page) {
		if (cursorId == null) {
			return toConvert(scanHistoryRepository.findAllByTeamIdOrderByHistoryIdDesc(teamId, page));
		}
		return toConvert(
			scanHistoryRepository.findByTeamIdAndHistoryIdLessThanOrderByHistoryIdDesc(teamId, cursorId, page));
	}

	private List<HistoryDto> toConvert(List<ScanHistory> entities) {
		List<HistoryDto> dtos = new ArrayList<>();
		for (ScanHistory e : entities) {
			dtos.add(new HistoryDto(e));
		}
		return dtos;
	}

	private Boolean hasNext(Long teamId, Long cursorId) {
		if (cursorId == null)
			return false;
		return scanHistoryRepository.existsByTeamIdAndHistoryIdLessThan(teamId, cursorId);
	}
}
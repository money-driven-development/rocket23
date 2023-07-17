package com.initcloud.scanhistory.scanHistory;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;
import com.initcloud.scanhistory.checklist.service.Impl.ScanHistoryServiceImpl;

@SpringBootTest
public class CursorBasedPaginationTest {

	@Autowired
	ScanHistoryRepository scanHistoryRepository;

	@Autowired
	ScanHistoryServiceImpl scanHistoryService;

	private final Pageable page = PageRequest.of(0, 10);

	@AfterEach
	public void cleanup() {
		scanHistoryRepository.deleteAll();
	}

	@Test
	public void CursorServiceTest() {
		//given
		String fileHash = "a73609ba-c96e-40ec-9d8d-cdfc0a4f5c68";
		Integer passed = 3;
		Integer skipped = 3;
		Integer failed = 2;
		Integer high = 30;
		Integer medium = 20;
		Integer low = 50;
		Integer unknown = 0;
		Integer cveCount = 30;
		Double score = 40.0;
		for (int i = 0; i < 17; i++) {
			String fileName = i + ".yaml";
			ScanHistoryEntity scanHistoryEntity = ScanHistoryEntity.builder()
				.fileName(fileName)
				.fileHash(fileHash)
				.passed(passed)
				.skipped(skipped)
				.failed(failed)
				.high(high)
				.medium(medium)
				.low(low)
				.unknown(unknown)
				.cveCount(cveCount)
				.score(score)
				.build();
			scanHistoryRepository.save(scanHistoryEntity);
		}
		//when
		CursorResultDto firstselection = scanHistoryService.getPageHistoryList(null, page);
		Long Index = firstselection.getValues().get(9).getId();
		assertThat(firstselection.getValues().size()).isEqualTo(10);
		assertTrue(firstselection.getHasNext());

		CursorResultDto secondselection = scanHistoryService.getPageHistoryList(Index, page);
		Index = secondselection.getValues().get(secondselection.getValues().size() - 1).getId();
		assertThat(secondselection.getValues().size()).isEqualTo(7);
		assertFalse(secondselection.getHasNext());

		CursorResultDto thirdselection = scanHistoryService.getPageHistoryList(Index, page);
		assertTrue(thirdselection.getValues().isEmpty());
		assertFalse(thirdselection.getHasNext());
	}
}

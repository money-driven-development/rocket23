package com.initcloud.scanhistory.scanHistory;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;
import com.initcloud.scanhistory.checklist.service.Impl.ScanHistoryServiceImpl;

@SpringBootTest
@DisplayName("ScanHistory Service 및 Entity 테스트")
public class ScanHistoryTest {
	@Autowired
	ScanHistoryRepository scanHistoryRepository;

	@Autowired
	ScanHistoryServiceImpl scanHistoryService;

	@AfterEach
	public void cleanup() {
		scanHistoryRepository.deleteAll();
	}

	@Test
	public void ScanHistoryEntityTest() {
		//given
		String fileName = "abc.yaml";
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
		scanHistoryRepository.save(ScanHistoryEntity.builder()
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
			.build());

		//when
		List<ScanHistoryDto> dtos = scanHistoryService.getFileHistoryList(fileHash);

		//then
		ScanHistoryDto dto = dtos.get(0);
		assertThat(dto.getFileName()).isEqualTo(fileName);
		assertThat(dto.getFileHash()).isEqualTo(fileHash);
		assertThat(dto.getPassed()).isEqualTo(passed);
		assertThat(dto.getSkipped()).isEqualTo(skipped);
		assertThat(dto.getFailed()).isEqualTo(failed);
		assertThat(dto.getHigh()).isEqualTo(high);
		assertThat(dto.getMedium()).isEqualTo(medium);
		assertThat(dto.getLow()).isEqualTo(low);
		assertThat(dto.getUnknown()).isEqualTo(unknown);
		assertThat(dto.getCveCount()).isEqualTo(cveCount);
		assertThat(dto.getScore()).isEqualTo(score);
	}
}

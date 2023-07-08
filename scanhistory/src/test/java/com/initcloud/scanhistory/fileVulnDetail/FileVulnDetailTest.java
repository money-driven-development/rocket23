package com.initcloud.scanhistory.fileVulnDetail;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.initcloud.scanhistory.checklist.entity.FileVulnDetailEntity;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryDetailEntity;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.FileVulnDetailRepository;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;

@SpringBootTest
@DisplayName("FileVulnDetail 외래키 연결 테스트")
public class FileVulnDetailTest {
	@Autowired
	ScanHistoryRepository scanHistoryRepository;

	@Autowired
	FileVulnDetailRepository fileVulnDetailRepository;

	@AfterEach
	public void cleanup() {
		fileVulnDetailRepository.deleteAll();
		scanHistoryRepository.deleteAll();
	}

	@Test
	public void ScanHistoryDetailEntityTesT() {

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

		String line = "30";
		String isValid = "T";
		String validContent = "adddddd";
		String vulnTitle = "CVE_123123";
		String hasVuln = "T";
		String vulnContent = "CVE_asdjkDDD";
		Integer vulnScore = 70;

		FileVulnDetailEntity fileVulnDetailEntity = FileVulnDetailEntity.builder()
			.scanHistoryEntity(scanHistoryEntity)
			.line(line)
			.isValid(isValid)
			.validContent(validContent)
			.vulnTitle(vulnTitle)
			.hasVuln(hasVuln)
			.vulnContent(vulnContent)
			.vulnScore(vulnScore)
			.build();

		fileVulnDetailRepository.save(fileVulnDetailEntity);
		//when
		Optional<FileVulnDetailEntity> Result = fileVulnDetailRepository.findById(fileVulnDetailEntity.getId());

		//then
		assertThat(Result.get().getScanHistoryEntity().getFileName()).isEqualTo(fileName);
		assertThat(Result.get().getScanHistoryEntity().getFileHash()).isEqualTo(fileHash);
		assertThat(Result.get().getScanHistoryEntity().getPassed()).isEqualTo(passed);
		assertThat(Result.get().getScanHistoryEntity().getSkipped()).isEqualTo(skipped);
		assertThat(Result.get().getScanHistoryEntity().getFailed()).isEqualTo(failed);
		assertThat(Result.get().getScanHistoryEntity().getHigh()).isEqualTo(high);
		assertThat(Result.get().getScanHistoryEntity().getMedium()).isEqualTo(medium);
		assertThat(Result.get().getScanHistoryEntity().getLow()).isEqualTo(low);
		assertThat(Result.get().getScanHistoryEntity().getUnknown()).isEqualTo(unknown);
		assertThat(Result.get().getScanHistoryEntity().getCveCount()).isEqualTo(cveCount);
		assertThat(Result.get().getScanHistoryEntity().getScore()).isEqualTo(score);

		assertThat(Result.get().getLine()).isEqualTo(line);
		assertThat(Result.get().getIsValid()).isEqualTo(isValid);
		assertThat(Result.get().getValidContent()).isEqualTo(validContent);
		assertThat(Result.get().getVulnTitle()).isEqualTo(vulnTitle);
		assertThat(Result.get().getHasVuln()).isEqualTo(hasVuln);
		assertThat(Result.get().getVulnContent()).isEqualTo(vulnContent);
		assertThat(Result.get().getVulnScore()).isEqualTo(vulnScore);

	}
}

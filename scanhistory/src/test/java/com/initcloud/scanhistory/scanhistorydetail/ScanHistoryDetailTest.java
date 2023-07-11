package com.initcloud.scanhistory.scanhistorydetail;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryDetailEntity;
import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryDetailRespository;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;

@SpringBootTest
@DisplayName("ScanHistoryDetail 외래키 연결 테스트")
public class ScanHistoryDetailTest {
	@Autowired
	ScanHistoryRepository scanHistoryRepository;

	@Autowired
	ScanHistoryDetailRespository scanHistoryDetailRespository;

	@AfterEach
	public void cleanup() {
		scanHistoryDetailRespository.deleteAll();
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

		String checkType = "T";
		String targetFileName = "abc.yaml";
		String appType = "client";
		String provider = "aws";
		String scanResult = "OK";
		String line = "X";
		String code = "C";
		ScanHistoryDetailEntity scanHistoryDetailEntity = ScanHistoryDetailEntity.builder()
			.scanHistoryEntity(scanHistoryEntity)
			.checkType(checkType)
			.targetFileName(targetFileName)
			.appType(appType)
			.provider(provider)
			.scanResult(scanResult)
			.line(line)
			.code(code)
			.build();
		scanHistoryDetailRespository.save(scanHistoryDetailEntity);

		//when
		Optional<ScanHistoryDetailEntity> Result = scanHistoryDetailRespository.findById(
			scanHistoryDetailEntity.getId());

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

		assertThat(Result.get().getCheckType()).isEqualTo(checkType);
		assertThat(Result.get().getTargetFileName()).isEqualTo(targetFileName);
		assertThat(Result.get().getAppType()).isEqualTo(appType);
		assertThat(Result.get().getProvider()).isEqualTo(provider);
		assertThat(Result.get().getScanResult()).isEqualTo(scanResult);
		assertThat(Result.get().getLine()).isEqualTo(line);
		assertThat(Result.get().getCode()).isEqualTo(code);
	}
}

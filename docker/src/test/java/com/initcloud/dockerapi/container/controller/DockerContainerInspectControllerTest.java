package com.initcloud.dockerapi.container.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.initcloud.dockerapi.common.dto.ResponseDto;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.dto.ContainerInspectDto;
import com.initcloud.dockerapi.container.enums.ContainerAPIType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(DockerContainerInspectController.class)
public class DockerContainerInspectControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	DockerContainerInspectController dockerContainerInspectController;

	private List<ContainerDto> inspectList;
	private ContainerInspectDto inspectDetails;

	private static final String BASE_URI = "/rocket23/containers";

	@BeforeAll
	public void init() {
		this.inspectList = new ArrayList<>();
		this.inspectList.add(new ContainerDto("id_1", "Running"));
		this.inspectList.add(new ContainerDto("id_2", "Running"));

		this.inspectDetails = ContainerInspectDto.containerDetailsStatusBuilder()
			.pid(1000L)
			.containerId("id_1")
			.containerName("test_container")
			.args(new String[] {"arg1", "arg2"})
			.image("alpine:latest")
			.startAt("2022-01-01 00:00:00")
			.apiType(ContainerAPIType.DOCKER)
			.containerStatus("Running")
			.build();
	}

	@Test
	@DisplayName("[Controller] 컨테이너 목록 조회 테스트")
	void DockerContainerInspectControllerListTest() throws Exception {
		//given

		//when
		Mockito.when(dockerContainerInspectController.containerList()).thenReturn(new ResponseDto<>(this.inspectList));

		//then
		mvc.perform(get(BASE_URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath("success").value(true))
			.andExpect(jsonPath("data.[0].containerId").value("id_1"))
			.andExpect(jsonPath("data.[0].containerStatus").value("Running"))
			.andExpect(jsonPath("data.[1].containerId").value("id_2"))
			.andExpect(jsonPath("data.[1].containerStatus").value("Running"))
		;
	}

	@Test
	@DisplayName("[Controller] 컨테이너 상세 조회 테스트")
	void DockerContainerInspectControllerDetailsTest() throws Exception {
		//given
		String containerId = "id_1";

		//when
		Mockito.when(dockerContainerInspectController.containerDetails(containerId))
			.thenReturn(new ResponseDto<>(this.inspectDetails));

		//then
		mvc.perform(get(BASE_URI + "/{containerId}", containerId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("success").value(true))
			.andExpect(jsonPath("data.pid").value(1000L))
			.andExpect(jsonPath("data.containerId").value("id_1"))
			.andExpect(jsonPath("data.containerName").value("test_container"))
			.andExpect(jsonPath("data.containerStatus").value("Running"))
		;
	}

	@Test
	@DisplayName("[Controller] 존재하지 않는 컨테이너 상세 조회 테스트")
	void DockerContainerInspectControllerNoneDetailsTest() throws Exception {
		//given
		String containerId = "id_3";

		//when Todo - 추후 실제 반환 값을 확인해서 변경해야 함.
		Mockito.when(dockerContainerInspectController.containerDetails(containerId))
			.thenReturn(new ResponseDto<>(null));

		//then
		mvc.perform(get(BASE_URI + "/{containerId}", containerId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("success").value(true))
			.andExpect(jsonPath("data").isEmpty())
		;
	}
}

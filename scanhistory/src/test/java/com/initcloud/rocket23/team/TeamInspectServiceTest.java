package com.initcloud.rocket23.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.team.service.TeamInspectService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TeamInspectServiceTest {

    @InjectMocks
    private TeamInspectService teamInspectService;

    @Mock
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testTeamInspectServiceGetTeam() {
        // Arrange
        String invalidTeamCode = "demo_team_code";

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () ->
                teamInspectService.getTeam(invalidTeamCode));

        assertEquals(ResponseCode.INVALID_TEAM, exception.getResponseCode());
    }

}

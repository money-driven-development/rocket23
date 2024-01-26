package com.initcloud.rocket23.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.policy.service.BasePolicySetService;
import com.initcloud.rocket23.team.entity.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class BasePolicySetServiceTest {

    @InjectMocks
    private BasePolicySetService basePolicySetService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamPolicyRepository teamPolicyRepository;

    @Mock
    private BasePolicyRepository basePolicyRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBasePolicyAllToTeamPolicyWithInvalidTeamCode() {
        // Arrange
        String invalidTeamCode = "demo_team_code";

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () ->
                basePolicySetService.basePolicyAllToTeamPolicy(invalidTeamCode));

        assertEquals(ResponseCode.INVALID_TEAM, exception.getResponseCode());
    }

    @Test
    public void testBasePolicyAllToTeamPolicyWithInvalidBasePolicy() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () ->
                basePolicySetService.getBasePolicy());

        assertEquals(ResponseCode.INVALID_BASE_POLICY, exception.getResponseCode());
    }

    @Test
    public void testCreateBasePolicySetWithInvalidTeamCode() {
        // Arrange
        String invalidTeamCode = "demo_team_code";

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () ->
                basePolicySetService.createBasePolicySet(invalidTeamCode));

        assertEquals(ResponseCode.INVALID_TEAM, exception.getResponseCode());
    }

    @Test
    public void testBasePolicyToTeamPolicy() {
        // Given
        Team team = Mockito.mock(Team.class);
        BasePolicy basePolicy = Mockito.mock(BasePolicy.class);

        // When
        basePolicySetService.basePolicyToTeamPolicy(team, basePolicy);

        // Then
        Mockito.verify(teamPolicyRepository, Mockito.times(1)).save(Mockito.any(TeamPolicy.class));
    }

}

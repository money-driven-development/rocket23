package com.initcloud.rocket23.team.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.PolicySetPerProjectRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamProjectRepository;
import com.initcloud.rocket23.infra.repository.TeamProjectVersioningRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.PolicySetPerProject;
import com.initcloud.rocket23.team.dto.TeamProjectCreateDto;
import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamProjectService {

    private final TeamRepository teamRepository;
    private final TeamProjectRepository teamProjectRepository;
    private final TeamPolicySetRepository teamPolicySetRepository;
    private final PolicySetPerProjectRepository policySetPerProjectRepository;
    private final BasePolicyRepository basePolicyRepository;
    private final TeamProjectVersioningRepository teamProjectVersioningRepository;

    /**
     * [Page<TeamProjectDto.Summary>] 팀 프로젝트 목록 조회, 페이징 적용
     */
    @Transactional(readOnly = true)
    public Page<TeamProjectDto.Summary> getPagedTeamProjects(final Pageable pageable, String teamCode) {
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        Page<TeamProject> teamProject = teamProjectRepository.findTeamProjectsByTeamOrderByIdDesc(pageable, team);

        return TeamProject.toPageDto(teamProject);
    }

    @Transactional(readOnly = true)
    public List<TeamProjectDto.Summary> getTeamProjects(final String teamCode) {
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        List<TeamProject> teamProject = teamProjectRepository.findTeamProjectsByTeamOrderByIdDesc(team);

        return TeamProject.toDto(teamProject);
    }

    /**
     * [TeamProjectDto.Details] 팀 프로젝트 조회
     */
    @Transactional(readOnly = true)
    public TeamProjectDto.Details getTeamProjectDetails(final String teamCode, final String projectCode) {

        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        TeamProject teamProject = teamProjectRepository.findTeamProjectByTeamAndProjectCode(team, projectCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        return teamProject.toDetailsDto();
    }

    /**
     * [String] 팀 프로젝트 추가
     */
    @Transactional
    public String createTeamProject(final String teamCode, final TeamProjectCreateDto request) {
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        TeamProject teamProject = request.toCreateEntity(team);
        teamProjectRepository.save(teamProject);

        List<PolicySet> policySets = teamPolicySetRepository.findPolicySetByTeam_TeamCodeAndNameIn(teamCode,
                request.getPolicySetNames());
        List<PolicySetPerProject> policySetPerProjects = policySets.stream()
                .map(
                        policySet -> new PolicySetPerProject(policySet, teamProject)
                )
                .collect(Collectors.toList());

        policySetPerProjectRepository.saveAll(policySetPerProjects);

        return teamProject.getProjectCode();
    }

    /**
     * [void] 팀 프로젝트 등록 해제
     */
    @Transactional
    public void removeTeamProject(final String teamCode, final String projectCode) {

        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        teamProjectRepository.deleteTeamProjectByTeamAndProjectCode(team, projectCode);
    }

    /**
     * 팀 프로젝트 엔티티 가져오기
     */
    public TeamProject getTeamProject(Team team, String projectCode) throws Exception {
        return teamProjectRepository.findTeamProjectByTeamAndProjectCode(team, projectCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));
    }
}

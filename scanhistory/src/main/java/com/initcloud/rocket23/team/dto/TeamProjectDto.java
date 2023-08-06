package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamProjectDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Summary {

    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Details {

    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Create {


        public TeamProject toCreateEntity(Team team) {
            return null;
        }
    }
}

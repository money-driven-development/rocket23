package com.initcloud.rocket23.team.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamInviteDto {

	@Getter
	public static class Request {
		private String email;
		private String teamCode;

		public Request(String email, String teamCode) {
			this.email = email;
			this.teamCode = teamCode;
		}
	}

	@Getter
	public static class Response {
		private String email;
		private String teamCode;
		private Boolean join;

		public Response(String email, String teamCode, Boolean join) {
			this.email = email;
			this.teamCode = teamCode;
			this.join = join;
		}
	}

}

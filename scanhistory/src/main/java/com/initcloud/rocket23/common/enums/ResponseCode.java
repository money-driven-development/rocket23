package com.initcloud.rocket23.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    /* Invalid Request */
    INVALID_REQUEST(4001, HttpStatus.BAD_REQUEST, "Invalid Request."),
    DATA_MISSING(4002, HttpStatus.BAD_REQUEST, "BAD_REQUEST"),
    INVALID_TOKEN(4015, HttpStatus.UNAUTHORIZED, "Invalid Token signature."),
    INVALID_TOKEN_FORMAT(4016, HttpStatus.UNAUTHORIZED, "Invalid Token format."),
    UNSUPPORTED_TOKEN(4017, HttpStatus.UNAUTHORIZED, "Unsupported Token."),
    INVALID_TOKEN_SIGNATURE(4018, HttpStatus.UNAUTHORIZED, "Invalid Token signature."),
    EMPTY_TOKEN_CLAIMS(4019, HttpStatus.UNAUTHORIZED, "Empty Token Claims."),
    NULL_TOKEN(4020, HttpStatus.UNAUTHORIZED, "Token is null"),
    INVALID_CREDENTIALS(4021, HttpStatus.UNAUTHORIZED, "Can not access OAuth"),
    TOKEN_EXPIRED(4022, HttpStatus.UNAUTHORIZED, "Token Expired."),

    /*Scan history Error. 5400*/
    NO_SCAN_RESULT(5401, HttpStatus.INTERNAL_SERVER_ERROR, "조회할 수 있는 스캔 결과가 없습니다."),
    SCAN_SAVE_ERROR(5402, HttpStatus.INTERNAL_SERVER_ERROR, "스캔 저장중 오류입니다."),
    SCAN_PARSING_ERROR(5403, HttpStatus.INTERNAL_SERVER_ERROR, "스캔 데이터 파싱중 오류입니다."),
    SCAN_DESCRIOTION_ERROR(5404, HttpStatus.INTERNAL_SERVER_ERROR, "스캔 세부조회 저장중 오류입니다."),
    SCAN_CODE_BLOCK_ERROR(5405, HttpStatus.INTERNAL_SERVER_ERROR, "스캔 코드블록 저장중 오류입니다."),
    /*Redis Error. 5500*/
    JSON_PROCESSING_ERROR(5501, HttpStatus.INTERNAL_SERVER_ERROR, "Redis Publish JSON 직렬화 오류입니다."),

    /* 6: User */
    DUPLICATE_USER(4601, HttpStatus.BAD_REQUEST, "Duplicate user"),
    INVALID_PASSWORD(4602, HttpStatus.BAD_REQUEST, "Invalid password"),

    /* 7: Team */
    INVALID_TEAM(4701, HttpStatus.BAD_REQUEST, "Invalid Team."),
    INVALID_USER_IN_TEAM(4702, HttpStatus.BAD_REQUEST, "Invalid user in this team."),
    INVALID_PROJECT_IN_TEAM(4703, HttpStatus.BAD_REQUEST, "Invalid project in this team."),
    INVALID_POLICY_IN_TEAM(4704, HttpStatus.BAD_REQUEST, "Invalid policy in this team."),
    INVALID_POLICY_SET_IN_TEAM(4705, HttpStatus.BAD_REQUEST, "Invalid policy set in this team."),
    INVALID_OWNER(4706, HttpStatus.BAD_REQUEST, "Invalid owner for github organizations or users."),

    /* Server Error. */
    SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Server busy."),
    SCAN_ERROR(5002, HttpStatus.INTERNAL_SERVER_ERROR, "Scan Error."),
    SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error."),
    /*File Uplaod Error. */
    SERVER_CREATED_DIR_ERROR(5201, HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리를 생성할 수 없습니다."),
    SERVER_STORE_ERROR(5202, HttpStatus.INTERNAL_SERVER_ERROR, "서버 저장 오류입니다."),
    FILE_EMPTY(5203, HttpStatus.INTERNAL_SERVER_ERROR, "빈 파일입니다."),
    AWS_FILE_UPLOAD_ERROR(5204, HttpStatus.INTERNAL_SERVER_ERROR, "AWS 파일 업로드 오류입니다."),
    FILE_WRONG_ERROR(5205, HttpStatus.INTERNAL_SERVER_ERROR, "파일이 올바르지 않습니다."),
    ZIP_PATH_ERROR(5206, HttpStatus.INTERNAL_SERVER_ERROR, "zip 파일 경로가 올바르지 않습니다."),
    ZIP_ENCODING_ERROR(5207, HttpStatus.INTERNAL_SERVER_ERROR, "압축파일 인코딩 오류입니다."),
    INVALID_BASE_POLICY(5208, HttpStatus.INTERNAL_SERVER_ERROR, "부적절한 Base Policy 입니다."),
    NO_TEAM_POLICIES_FOUND(5209,HttpStatus.INTERNAL_SERVER_ERROR, "Team Policy를 찾을 수 없습니다.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
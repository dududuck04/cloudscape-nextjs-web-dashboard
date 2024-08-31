package com.stc.exporter.app.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 유저 회원가입 정보
 *
 * @author 김경민
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {

	@Schema(description = "사용자 이메일", example = "user1@example.com", required = true)
	private String email;

	@Schema(description = "사용자 비밀번호", example = "", required = true)
	private String password;

	@Schema(description = "사용자 닉네임", example = "user1", required = true)
	private String nickname;

}

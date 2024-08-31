package com.stc.exporter.app.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenDto {

    @Schema(description = "인증 방식", example = "Bearer", required = true)
    private String grantType;

    @Schema(description = "접근 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", required = true)
    private String accessToken;

    @Schema(description = "갱신 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5d", required = true)
    private String refreshToken;

    @Schema(description = "사용자 고유 ID", example = "123", required = true)
    private int userIdx;
}

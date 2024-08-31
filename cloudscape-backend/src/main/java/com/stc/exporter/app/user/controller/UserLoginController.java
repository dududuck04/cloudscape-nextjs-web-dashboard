package com.stc.exporter.app.user.controller;

import com.stc.exporter.app.user.model.UserRegistrationDto;
import com.stc.exporter.app.user.service.UserLoginService;
import com.stc.exporter.config.security.JwtService;
import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.config.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.stc.exporter.config.utils.BaseResponseStatus.DATABASE_ERROR_CREATE_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
public class UserLoginController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserLoginService userLoginService;

    private final JwtService jwtService;

    /**
     *
     * @param userRegistrationDto 클라이언트로부터 받은 유저 등록 정보를 담은 DTO 객체.
     * @return 회원가입 성공 시 "로그인에 성공하였습니다."라는 메시지를 담은 BaseResponse 객체.
     * @throws BaseException 회원가입 과정에서 발생한 예외.
     */

    @PostMapping("/login")
    @Operation(
            summary = "사용자 로그인",
            description = "인증된 사용자가 로그인 한 경우 jwt token을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "2028", description = "패스워드를 입력해 주세요"),
            @ApiResponse(responseCode = "4030", description = "올바른 비밀번호가 아닙니다."),
            @ApiResponse(responseCode = "2008", description = "email을 입력해주세요."),
            @ApiResponse(responseCode = "3019", description = "존재하지 않는 email 입니다."),

    })
    public BaseResponse<HashMap<String, Object>> login(@RequestBody UserRegistrationDto userRegistrationDto) {

        try {

            HashMap<String, Object> loginInfo = new HashMap<>();

            loginInfo = userLoginService.logIn(userRegistrationDto);

            return new BaseResponse<>(loginInfo);

        }

        catch (BaseException exception) {

            logger.error("BaseException", exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @Operation(
            summary = "사용자 카카오 소셜 로그인",
            description = "인증된 사용자가 로그인 한 경우 Client Secret을 반환합니다.",
            parameters = {
                    @Parameter(name = "Authorization-refresh", description = "access token 발급하기 위한 refresh token", example = "test@test.com"),
            }
    )
    @PostMapping("/login/kakao")
    public BaseResponse<HashMap<String, Object>> loginWithKakao(@RequestBody UserRegistrationDto userRegistrationDto) {

        try {

            HashMap<String, Object> loginInfo = new HashMap<>();

            loginInfo = userLoginService.logIn(userRegistrationDto);

            return new BaseResponse<>(loginInfo);

        }

        catch (BaseException exception) {

            logger.error("BaseException", exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @Operation(
            summary = "만료된 access token 재 발급",
            description = "인증된 사용자 access token이 만료된 경우 refresh token을 이용해 재 발급합니다.",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "Authorization-refresh",
                            description = "access token 발급하기 위한 refresh token",
                            schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDk3ODg5NjcsImV4cCI6MTcwOTg3NTM2N30.36i_z2na0Q2GDSjfMEuLuwSJPYzeQx6AGWXo6OyY_7E"))
            }
    )
    @PostMapping("/login/refresh")
    public BaseResponse<HashMap<String, Object>> refreshToken(@RequestHeader(name = "Authorization-refresh") String refreshToken, HttpServletResponse response) throws BaseException {
        try {

            HashMap<String, Object> tokenInfo = new HashMap<>();

            if (refreshToken != null && !jwtService.isTokenExpired(refreshToken)) {
                String accessToken = jwtService.checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
                if (accessToken != null) {

                    tokenInfo.put("token", accessToken);

                    return new BaseResponse<>(tokenInfo);
                } else {
                    // 로그 추가 및 응답 메시지 개선
                    logger.info("Access token re-issue failed for refreshToken: {}", refreshToken);

                }
            } else {
                if (refreshToken == null) {
                    logger.info("Refresh token is null.");

                } else {
                    logger.info("Refresh token is not expired yet.");

                }
            }
            tokenInfo.put("Refresh token is not valid.", refreshToken);
            return new BaseResponse<>(tokenInfo); // "Refresh token is not valid."

        } catch (Exception exception) {
            logger.error("DB 처리 오류", exception);
            throw new BaseException(DATABASE_ERROR_CREATE_USER); // "신규 유저 정보를 DB에 등록하지 못하였습니다."
        }
    }

}
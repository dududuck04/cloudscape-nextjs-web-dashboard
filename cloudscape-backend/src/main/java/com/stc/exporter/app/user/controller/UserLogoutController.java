package com.stc.exporter.app.user.controller;

import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.config.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
public class UserLogoutController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 사용자 로그아웃을 처리하는 컨트롤러 메소드.
     *
     * @param userRegistrationDto 클라이언트로부터 받은 유저 등록 정보를 담은 DTO 객체.
     * @return 회원가입 성공 시 "로그인에 성공하였습니다."라는 메시지를 담은 BaseResponse 객체.
     * @throws BaseException 회원가입 과정에서 발생한 예외.
     */
    @PostMapping("/logout")
    @Operation(
            summary = "사용자 로그아웃",
            description = "인증된 사용자를 로그아웃 처리합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
    })
    public BaseResponse<String> logout() {

        SecurityContextHolder.clearContext();
        return new BaseResponse<>("로그아웃에 성공하였습니다.");

    }

}

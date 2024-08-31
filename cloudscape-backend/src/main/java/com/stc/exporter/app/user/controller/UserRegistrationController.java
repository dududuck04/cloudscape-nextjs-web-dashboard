package com.stc.exporter.app.user.controller;

import com.stc.exporter.app.user.model.UserRegistrationDto;
import com.stc.exporter.app.user.service.UserRegistrationService;
import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.config.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap; 

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
public class UserRegistrationController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRegistrationService userRegistrationService;

    /**
     * @param userRegistrationDto
     * @return 회원가입 성공 시 "회원 가입에 성공하였습니다."라는 메시지를 담은 BaseResponse 객체.
     * @throws BaseException 회원가입 과정에서 발생한 예외.
     */
    @PostMapping("/add")
    @Operation(
            summary = "회원가입",
            description = "사용자의 email, password, nickname을 받아 새로운 유저를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found"),
            @ApiResponse(responseCode = "3018", description = "이미 가입된 email 입니다.")

    })
    public BaseResponse<HashMap<String, Object>> postUser(UserRegistrationDto userRegistrationDto) {
        try {
            HashMap<String, Object> userInfo;
            userInfo = this.userRegistrationService.postUser(userRegistrationDto);

            return new BaseResponse<>(userInfo);

        } catch (BaseException exception) {
            logger.error("BaseException", exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

}

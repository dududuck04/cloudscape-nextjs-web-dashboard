package com.stc.exporter.app.user.controller;

import com.stc.exporter.app.user.model.UserRegistrationDto;
import com.stc.exporter.app.user.service.UserPasswordService;
import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.config.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
public class UserPasswordController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserPasswordService userPasswordService;

    /**
     * 사용자 로그인을 처리하는 컨트롤러 메소드.
     * 클라이언트로부터 받은 UserRegistrationDto 객체를 사용하여 유저를 로그인합니다.
     *
     * @param userRegistrationDto 클라이언트로부터 받은 유저 등록 정보를 담은 DTO 객체.
     * @return 회원가입 성공 시 "로그인에 성공하였습니다."라는 메시지를 담은 BaseResponse 객체.
     * @throws BaseException 회원가입 과정에서 발생한 예외.
     */

    @PostMapping("/password/find")
    public BaseResponse<String> loginEmailStage(@RequestBody UserRegistrationDto userRegistrationDto) {

        try {

            userPasswordService.findPassword(userRegistrationDto);

            return new BaseResponse<>("로그인 이메일 단계 성공");

        }

        catch (BaseException exception) {

            logger.error("BaseException", exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

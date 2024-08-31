package com.stc.exporter.app.user.controller;

import com.stc.exporter.app.user.service.UserDeleteService;
import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.config.utils.BaseResponse;
import com.stc.exporter.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.stc.exporter.config.utils.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
public class UserDeleteController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDeleteService userDeleteService;

    private final JwtService jwtService;


    /**
     * 회원 탈퇴
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteUser(@RequestParam(required = false) Integer deleteType) {

        try {

            /**
             * 타입 입력 검사
             */
            if (deleteType == null) {
                return new BaseResponse<>(POST_USERS_DELETE_TYPE_EMPTY); // "탈퇴 이유를 알려주세요."
            }

            /**
             * 타입 형식 검사
             */

            if (deleteType < 1 || deleteType > 6) {
                throw new BaseException(WRONG_USERS_REPORT_TYPE); // "올바르지 않은 타입입니다."
            }

            int userIdx = jwtService.getUserIdx();

            /**
             * 유저 상태 비활성화
             */
            userDeleteService.deleteUser(userIdx);

            String typeMessage;

            switch (deleteType) {
                case 1:
                    typeMessage = "너무 많이 이용해요";
                    break;
                case 2:
                    typeMessage = "사고 싶은 물품이 없어요";
                    break;
                case 3:
                    typeMessage = "물품이 안팔려요";
                    break;
                case 4:
                    typeMessage = "비매너 사용자를 만났어요";
                    break;
                case 5:
                    typeMessage = "새 계정을 만들고 싶어요";
                    break;
                case 6:
                    typeMessage = "기타";
                    break;
                default:
                    typeMessage = null;
            }

            return new BaseResponse<>("계정 탈퇴 이유 : " + typeMessage);

        } catch (BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));
        }
    }

}

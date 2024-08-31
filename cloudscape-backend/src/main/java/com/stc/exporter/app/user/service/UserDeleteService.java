package com.stc.exporter.app.user.service;

import com.stc.exporter.config.utils.BaseException;

import com.stc.exporter.app.user.dao.UserLoginDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stc.exporter.config.utils.BaseResponseStatus.*;

/**
 * 유저 기본 인증 정보 CUD 를 위한 Service
 *
 * @author 김경민
 * @version 1.0, 유저 회원가입, 인증, 로그인, 로그아웃, 탈퇴
 */
@Service
@RequiredArgsConstructor

public class UserDeleteService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    final UserLoginDao userLoginDao;

    /**
     * 로그인 상태 확인
     */
    /* 회원탈퇴 (유저 비활성화) - deleteUser() */
    @Transactional
    public void deleteUser(int userIdx) throws BaseException {

        try {
            /**
             * 로그인 상태 확인
             */
            // 로그인 상태 확인
            if (this.userLoginDao.checkLoginStatus(userIdx)) {
                throw new BaseException(DATABASE_ERROR_USER_NOT_LOGIN);
            }

        } catch (Exception exception) {

            logger.error("DB 처리 오류", exception);
            throw new BaseException(DATABASE_ERROR_DELETE_USER);
        }

    }

}

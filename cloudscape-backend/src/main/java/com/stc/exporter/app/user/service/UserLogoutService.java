package com.stc.exporter.app.user.service;

import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.app.user.dao.UserLoginDao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stc.exporter.config.utils.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserLogoutService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserLoginDao userloginDao;

    /**
     * 로그인 상태 확인
     */
    /* 유저 로그아웃 - logout() */
    @Transactional
    public void logout(int userIdx) throws BaseException {
        
        /**
         * 로그인 상태 확인
         */
        // 이미 로그아웃된 유저인지 확인
        if (this.userloginDao.checkLoginStatus(userIdx)) {
            throw new BaseException(DATABASE_ERROR_ALREADY_LOGOUT);
        }

        try {

            // 로그아웃 상태 변경
            if (!this.userloginDao.updateLoginStatus(userIdx)) {
                throw new BaseException(DATABASE_ERROR_FAIL_LOGOUT);
            }

        } catch (Exception exception) {

            logger.error("DB 처리 오류", exception);
            throw new BaseException(DATABASE_ERROR_FAIL_LOGOUT); // "로그 아웃에 실패했습니다."
        }

    }


}

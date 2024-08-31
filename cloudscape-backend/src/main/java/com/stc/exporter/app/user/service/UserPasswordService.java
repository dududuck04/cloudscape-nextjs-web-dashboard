package com.stc.exporter.app.user.service;

import com.stc.exporter.app.user.dao.UserPasswordDao;
import com.stc.exporter.app.user.dao.UserRegistrationDao;
import com.stc.exporter.app.user.model.UserRegistrationDto;
import com.stc.exporter.config.utils.BaseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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
public class UserPasswordService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRegistrationDao userRegistrationDao;
    private UserPasswordDao userPasswordDao;

    /**
     * 로그인 상태 설정 클래스
     *
     * @param
     * @return
     * @exception
     */
    /* 회원가입 인증 - userJoinCheck() */
    @Transactional
    public void findPassword(UserRegistrationDto userRegistrationDto) throws BaseException {
        try {
            String email = userRegistrationDto.getEmail();

            if (email == null || email.isEmpty()) {
                throw new BaseException(POST_USERS_EMPTY_EMAIL); // "email을 입력해주세요."
            }

            if (this.userRegistrationDao.checkVerificationEmail(email)) {
                throw new BaseException(POST_USERS_UNVALID_EMAIL); // "존재하지 않는 email 입니다."
            }

            // 무작위 비밀번호 생성
            String randomPassword = RandomStringUtils.randomAlphanumeric(10);

            // 이메일 제목과 내용
            String subject = "색갈피 임시비밀번호 안내 이메일 입니다.";
            String text = "안녕하세요. 색갈피 임시비밀번호 안내 관련 이메일입니다. 회원님의 임시 비밀번호는 " + randomPassword + "입니다. 로그인 후에 비밀번호를 변경해주세요.";

            // 이메일 전송
//            sendPasswordByEmail(email, subject, text, randomPassword);

            logger.info("New Password : " + randomPassword);

        }catch (Exception exception) {

            logger.error("DB 처리 오류", exception);
            throw new BaseException(DATABASE_ERROR_FAIL_LOGIN); // "로그인에 실패했습니다."
        }

    }

//    @Transactional
//    public void sendPasswordByEmail(String email, String subject, String text, String randomPassword) throws BaseException {
//        try {
//
//            SimpleMailMessage emailForm = new SimpleMailMessage();
//            emailForm.setFrom("kimkm95@mz.co.kr");
//            emailForm.setTo(email);
//            emailForm.setSubject(subject);
//            emailForm.setText(text);
//            javaMailSender.send(emailForm);
//
//            // db password 변경 절차
//            this.userPasswordDao.postTempUserPassword(randomPassword);
//
//        }catch (Exception exception) {
//
//            logger.error("DB 처리 오류", exception);
//            throw new BaseException(DATABASE_ERROR_FAIL_LOGIN); // "로그인에 실패했습니다."
//        }
//    }
}

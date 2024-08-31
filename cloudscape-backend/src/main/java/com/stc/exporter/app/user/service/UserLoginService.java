package com.stc.exporter.app.user.service;

import com.stc.exporter.app.user.dao.UserLoginDao;
import com.stc.exporter.app.user.model.UserRegistrationDto;
import com.stc.exporter.config.security.CustomUserDetails;
import com.stc.exporter.config.security.CustomUserDetailsService;
import com.stc.exporter.config.security.JwtService;
import com.stc.exporter.config.utils.BaseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import static com.stc.exporter.config.utils.BaseResponseStatus.*;

/**
 * 유저 기본 인증 정보 CUD 를 위한 Service
 *
 * @author 김경민
 * @version 1.0, 유저 회원가입, 인증, 로그인, 로그아웃, 탈퇴
 */
@Service
@RequiredArgsConstructor
public class UserLoginService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserLoginDao userLoginDao;

    private final JwtService jwtService;

    private final CustomUserDetailsService customUserDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public HashMap<String, Object> logIn(UserRegistrationDto userRegistrationDto) throws BaseException {
        try {
            String email = userRegistrationDto.getEmail(); // 이메일 사용
            String rawPassword = userRegistrationDto.getPassword(); // 사용자가 입력한 비밀번호

            if (email == null || email.isEmpty() || rawPassword == null || rawPassword.isEmpty()) {
                throw new BaseException(POST_USER_EMPTY_REQUIREMENT); // 비밀번호나 이메일이 비어 있는 경우
            }

            // 사용자 정보 조회
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);

            if (!passwordEncoder.matches(rawPassword, customUserDetails.getPassword())) {
                throw new BaseException(PASSWORD_VERIFICATION_ERROR); // 비밀번호 검증 실패
            }

            // 비밀번호 검증 성공 후, 마지막 로그인 시간 업데이트
            int userIdx = customUserDetails.getUserIdx();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            userLoginDao.updateLastLoginTime(userIdx, now); // 마지막 로그인 시간을 현재 시간으로 업데이트

            String token = jwtService.generateToken(userIdx);
            String refreshToken = jwtService.generateRefreshToken();
            Date expiryDate = jwtService.extractExpiration(token);


            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", email);
            userInfo.put("userIdx", userIdx);
            userInfo.put("token", token);
            userInfo.put("refreshToken", refreshToken);
            userInfo.put("expiryDate", expiryDate);

            return userInfo;

        } catch (Exception exception) {
            logger.error("DB 처리 오류", exception);
            throw new BaseException(DATABASE_ERROR_FAIL_LOGIN); // 로그인 실패
        }
    }
}

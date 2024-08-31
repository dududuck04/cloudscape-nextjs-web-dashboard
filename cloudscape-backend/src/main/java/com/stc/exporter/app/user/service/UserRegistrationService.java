package com.stc.exporter.app.user.service;

import com.stc.exporter.config.utils.BaseException;
import com.stc.exporter.app.user.dao.TokenDao;
import com.stc.exporter.app.user.dao.UserRegistrationDao;
import com.stc.exporter.app.user.model.UserRegistrationDto;
import com.stc.exporter.config.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.stc.exporter.config.utils.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRegistrationDao userRegistrationDao;

    private final JwtService jwtService;

    private final TokenDao tokenDao;

    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * 사용자 회원가입. 이메일 중복 확인 후 계정 생성 및 JWT 발급.
     *
     * @return HashMap 생성된 사용자 ID 및 JWT 토큰 포함
     * @throws BaseException 이메일 중복 또는 DB 오류 발생 시 예외 처리1
     */
    @Transactional
    public HashMap<String, Object> postUser(UserRegistrationDto userRegistrationDto) throws BaseException {

        // 임시 저장된 이메일 정보 가져오기
        String email = userRegistrationDto.getEmail();
        String rawPassword = userRegistrationDto.getPassword();
        String nickname = userRegistrationDto.getNickname();

        if (this.userRegistrationDao.checkEmail(email)) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL); // "이미 가입된 이메일입니다."
        }

        // 비밀번호를 BCryptPasswordEncoder를 사용하여 해시
        String password = passwordEncoder.encode(rawPassword);


        // 유저 등록 및 JWT 처리
        try {
            HashMap<String, Object> userInfo = new HashMap<>();
            int userIdx = this.userRegistrationDao.postUser(email, password, nickname);

            userInfo.put("userIdx", userIdx);
            userInfo.put("email", email);
            userInfo.put("nickname", nickname);

//            colorPostService.postDefaultColorList((Integer) userInfo.get("userIdx"));
//            emotionPostService.postDefaulEmotionList((Integer) userInfo.get("userIdx"));
//            keywordPostService.postDefaulKeywordList((Integer) userInfo.get("userIdx"));

            return userInfo;

        } catch (Exception exception) {
            logger.error("DB 처리 오류", exception);
            throw new BaseException(DATABASE_ERROR_CREATE_USER); // "신규 유저 정보를 DB에 등록하지 못하였습니다."
        }
    }

    @Transactional
    public void postNickName(UserRegistrationDto userRegistrationDto) throws BaseException {

        String nickname = userRegistrationDto.getNickname();

        if (nickname.length() > 10) { // MAX_KEYWORD_LENGTH는 상수로 정의
            throw new BaseException(POST_USER_NICKNAME_INVALID_LENGTH); // "닉네임 길이는 10자를 초과할 수 없습니다."
        }

        // 이메일 임시 저장 로직 (임시 사용자 생성 등)
        this.userRegistrationDao.postNickname(nickname);

    }

}
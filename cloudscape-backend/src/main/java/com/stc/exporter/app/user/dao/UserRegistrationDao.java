package com.stc.exporter.app.user.dao;

import com.stc.exporter.config.security.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * 유저 기본 데이터 처리 DAO
 *
 * @return 사원 목록
 */
@Mapper
public interface UserRegistrationDao {

    /**
     * email 중복여부 확인 메소드
     *
     * @return 중복된 닉네임이 있다면 True
     */
    boolean checkEmail(String email);

    boolean checkVerificationEmail(String email);

    boolean checkVerificationPassword(String password);

    /**
     * user 생성 메소드
     *
     * @return int type userIdx 반환
     */
    int postUser(String email, String password, String nickname);

    boolean postNickname(String nickname);

    /**
     * useridx 조회 메소드
     *
     * @return 조회된 useridx 반환
     */


    int getUserIdxByEmail(String email);

    String getEmailByUserIdx(int userIdx);


    int postTempUser(String email);

    CustomUserDetails findUserByUserIdx(int userIdx);

    CustomUserDetails findUserByUserEmail(String email);
}

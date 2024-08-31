package com.stc.exporter.app.user.dao;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;


/**
 * 모든 사원 목록을 조회한다.
 *
 * @param authCode
 * @param userReq
 *
 * @return 사원 목록
 */
@Mapper
public interface UserLoginDao {

    boolean checkLoginStatus(int userIdx);

    /**
     * 사용자의 마지막 로그인 시간을 업데이트합니다.
     *
     * @param userIdx 사용자의 고유 ID.
     * @return 업데이트 성공 여부를 나타내는 boolean 값.
     */
    boolean updateLastLoginTime(int userIdx , Timestamp now);

    boolean updateLoginStatus(int userIdx);

    Timestamp getLastLoginTime(int userIdx);

}

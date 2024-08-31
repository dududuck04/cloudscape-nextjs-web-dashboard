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
public interface UserPasswordDao {

    boolean postTempUserPassword(String randomPassword);

}

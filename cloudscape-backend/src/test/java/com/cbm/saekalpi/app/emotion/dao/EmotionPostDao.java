package com.cbm.saekalpi.app.emotion.dao;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.HashMap;


/**
 * 유저 기본 데이터 처리 DAO
 *
 * @return 사원 목록
 */
@Mapper
public interface EmotionPostDao {
    HashMap<String, Object> getMyColorSetting(int userIdx);

    boolean postDefaultColorSetting(int userIdx);

    boolean patchMyColorSetting(int userIdx, String color, String colorName);

    boolean patchMyColorByDate(int userIdx, LocalDate date);



}

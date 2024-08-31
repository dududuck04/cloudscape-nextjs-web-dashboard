package com.cbm.saekalpi.app.diary.dao;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


/**
 * 유저 기본 데이터 처리 DAO
 *
 * @return 사원 목록
 */
@Mapper
public interface DiaryPostDao {

    boolean postDiary(HashMap<String, Object> diaryPostInfo);

    boolean checkStoryLength(String story);

    boolean checkDiaryEmpty(LocalDate date);

    List<String> getAllStoryByDate(LocalDate date);

    List<String> postDiaryStory(List<String> story);

    HashMap<String, Object> getDiaryByDate(LocalDate date, int userIdx);

    HashMap<String, Object> getDiaryColorByMonth(LocalDate date, int userIdx);


}

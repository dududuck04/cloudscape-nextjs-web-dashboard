package com.cbm.saekalpi.app.keyword.dao;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;


/**
 * 유저 기본 데이터 처리 DAO
 *
 * @return 사원 목록
 */
@Mapper
public interface KeywordPostDao {

    List<String> getKeywordListByDate(int userIdx, LocalDate date);

    boolean postKeywordListByDate(int userIdx, LocalDate date, List keywordList);

}

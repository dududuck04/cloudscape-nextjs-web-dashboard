package com.cbm.saekalpi.app.user.dao;

import com.stc.exporter.app.user.dao.UserRegistrationDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationDaoTest {

    @Mock
    private UserRegistrationDao userRegistrationDao;

    @DisplayName("이메일 중복 테스트")
    @Test
    void checkEmailTest() {
        // Mock 객체의 동작 정의

        // "test@example.com" 주소에 대해서만 true를 반환
        doReturn(true).when(userRegistrationDao).checkEmail("test@example.com");

        // 테스트 수행
        assertTrue(userRegistrationDao.checkEmail("test@example.com"));
    }

    @DisplayName("실제 이메일이 존재하는지 테스트")
    @Test
    void checkVerificationEmailTest() {
        // Mock 객체의 동작 정의

        // "test@example.com" 주소에 대해서만 true를 반환
        doReturn(true).when(userRegistrationDao).checkVerificationEmail("test@example.com");

        // 테스트 수행
        assertTrue(userRegistrationDao.checkVerificationEmail("test@example.com"));
    }

    @DisplayName("비밀번호 검증 테스트")
    @Test
    void checkVerificationPasswordTest() {
        // Mock 객체의 동작 정의
        // "123"에 대해서는 true(유효)를 반환
        doReturn(true).when(userRegistrationDao).checkVerificationPassword("P@ssw0rd");

        // 테스트 수행
        assertTrue(userRegistrationDao.checkVerificationPassword("P@ssw0rd"));
    }

    @DisplayName("사용자 등록 테스트")
    @Test
    void postUserTest() {
        // Mock 객체의 동작 정의

        // 주어진 이메일과 비밀번호에 대해 특정 ID(예: 1)를 반환하도록 설정
        doReturn(1).when(userRegistrationDao).postUser("test@example.com", "P@ssw0rd","testUser");

        // 테스트 수행
        int userIdx = userRegistrationDao.postUser("test@example.com", "P@ssw0rd","testUser");

        // 반환된 ID가 1인지 확인
        assertEquals(1, userIdx);
    }


    @DisplayName("별명 등록 테스트")
    @Test
    void postNicknameTest() {

        // Mock 객체의 동작 정의
        // 주어진 별명에 대해 true를 반환하도록 설정
        doReturn(true).when(userRegistrationDao).postNickname("testUser");

        // 테스트 수행
        boolean result = userRegistrationDao.postNickname("testUser");

        // 반환된 결과가 true인지 확인
        assertTrue(result);
    }

    @DisplayName("이메일에 의한 사용자 인덱스 얻기 테스트")
    @Test
    void getUserIdxByEmailTest() {
        // Mock 객체의 동작 정의
        // 주어진 이메일에 대해 특정 사용자 인덱스(예: 1) 반환하도록 설정
        doReturn(1).when(userRegistrationDao).getUserIdxByEmail("test@example.com");

        // 테스트 수행
        int userIdx = userRegistrationDao.getUserIdxByEmail("test@example.com");

        // 반환된 인덱스가 1인지 확인
        assertEquals(1, userIdx);
    }


    @DisplayName("사용자 인덱스에 의한 이메일 얻기 테스트")
    @Test
    void getEmailByUserIdxTest() {
        // Mock 객체의 동작 정의
        // 주어진 사용자 인덱스에 대해 특정 이메일 반환하도록 설정
        doReturn("test@example.com").when(userRegistrationDao).getEmailByUserIdx(1);

        // 테스트 수행
        String email = userRegistrationDao.getEmailByUserIdx(1);

        // 반환된 이메일이 올바른지 확인
        assertEquals("test@example.com", email);
    }

}

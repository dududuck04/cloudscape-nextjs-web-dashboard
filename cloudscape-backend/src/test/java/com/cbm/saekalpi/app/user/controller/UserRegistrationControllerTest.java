//package com.cbm.saekalpi.app.user.controller;
//
//import com.cbm.saekalpi.app.user.dao.UserRegistrationDao;
//import com.cbm.saekalpi.app.user.model.UserRegistrationDto;
//import com.cbm.saekalpi.app.user.service.UserRegistrationService;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(MockitoExtension.class)
//public class UserRegistrationControllerTest {
//
//    @Mock
//    UserRegistrationService userRegistrationService;
//
//    @Mock
//    UserRegistrationDao  userRegistrationDao;
//
//    @InjectMocks
//    private UserRegistrationController userRegistrationController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).build();
//    }
//
//    @DisplayName("회원 가입 성공")
//    @Test
//    void postUser() throws Exception {
//        //given
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
//
//        userRegistrationDto.setEmail("test@example.com");
//        userRegistrationDto.setPassword("P@ssw0rd");
//        userRegistrationDto.setNickname("testUser");
//
//        Mockito.doReturn(new HashMap<String, String>()).when(userRegistrationService).postUser(ArgumentMatchers.any(UserRegistrationDto.class));
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(userRegistrationDto))
//        );
//
//        // Then
//        MvcResult mvcResult = actions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        String responseString = mvcResult.getResponse().getContentAsString();
//
//    }
//}

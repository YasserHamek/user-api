package com.test.userapi.integrationtest;

import com.test.userapi.UserApiApplication;
import com.test.userapi.controller.UserController;
import com.test.userapi.dto.UserDto;
import com.test.userapi.model.User;
import com.test.userapi.utils.Helper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.test.userapi.utils.StaticMessages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = UserApiApplication.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
class CreateUserAccountIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    User user;
    UserDto userDto;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        user = new User();
        user.setName("user");
        user.setEmail("user@gmail.com");
        user.setCountry("France");
        user.setBirthday(LocalDate.of(1970, 01, 01));

        userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCountry(user.getCountry());
        userDto.setBirthday(user.getBirthday());
    }

    @Test
    void servletContextTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBeansOfType(UserController.class));
    }

    @Test
    @DisplayName("Testing creating user account")
    @Transactional
    void createAccountIntegrationTest() throws Exception {
        String userDtoJson = Helper.userDtoAsJsonString(userDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
        ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Testing creating user account when user already exist with same email")
    @Transactional
    void createAccountIntegrationTest_WhenUserAlreadyExists() throws Exception {
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        //adding user
        mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                );

        //adding the same user
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andReturn();

        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(ONLY_ONE_ACCOUNT_PER_EMAIL_IS_ALLOWED);
    }

    @Test
    @DisplayName("Testing creating user account when user name is null")
    void createAccountIntegrationTest_WhenUserNameIsNull() throws Exception {
        userDto.setName(null);
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo("Name"+ MAY_NOT_BE_NULL_OR_EMPTY);
    }

    @Test
    @DisplayName("Testing creating user account when user birthday is null")
    void createAccountIntegrationTest_WhenUserBirthdayIsNull() throws Exception {
        userDto.setBirthday(null);
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(BIRTHDAY_MAY_NOT_BE_NULL);
    }

    @Test
    @DisplayName("Testing creating user account when user is underage")
    void createAccountIntegrationTest_WhenUserIsUnderAge() throws Exception {
        userDto.setBirthday(LocalDate.of(2006, 01, 01));
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(ONLY_ADULT_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT);
    }

    @Test
    @DisplayName("Testing creating user account when user country is null")
    void createAccountIntegrationTest_WhenUserCountryIsNull() throws Exception {
        userDto.setCountry(null);
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(COUNTRY_MAY_NOT_BE_NULL_OR_EMPTY);
    }

    @Test
    @DisplayName("Testing creating user account when user is not french resident")
    void createAccountIntegrationTest_WhenUserCountryIsNotFrench() throws Exception {
        userDto.setCountry("Country");
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(ONLY_FRENCH_RESIDENTS_ARE_ALLOWED_TO_CREATE_AN_ACCOUNT);
    }

    @Test
    @DisplayName("Testing creating user account when user email is null")
    void createAccountIntegrationTest_WhenUserEmailIsNull() throws Exception {
        userDto.setEmail(null);
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(EMAIL_MAY_NOT_BE_NULL_OR_EMPTY);
    }

    @Test
    @DisplayName("Testing creating user account when user email is not valid")
    void createAccountIntegrationTest_WhenUserEmailIsNotValid() throws Exception {
        userDto.setEmail("notValideEmail.com");
        String userDtoJson = Helper.userDtoAsJsonString(userDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(INVALID_EMAIL);
    }
}
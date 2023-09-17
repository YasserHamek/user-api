package com.test.userapi.integrationtest;

import com.test.userapi.UserApiApplication;
import com.test.userapi.controller.UserController;
import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import com.test.userapi.utils.Helper;
import jakarta.servlet.ServletContext;
import org.json.JSONArray;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = UserApiApplication.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
class ResearchUsersIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    UserDto userDto;
    UserDto userDto2;
    UserDto userDto3;

    UserFilterDto userFilterDto;
    UserFilterDto invalidUserFilterDto;
    UserFilterDto userFilterDto2;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        userDto = new UserDto();
        userDto.setName("user");
        userDto.setEmail("user@gmail.com");
        userDto.setCountry("France");
        userDto.setBirthday(LocalDate.of(1970, 01, 01));
        userDto.setGender("null");
        userDto.setPhoneNumber("null");

        userDto2 = new UserDto();
        userDto2.setName("user");
        userDto2.setEmail("user2@gmail.com");
        userDto2.setCountry("France");
        userDto2.setBirthday(LocalDate.of(1970, 01, 01));
        userDto2.setGender("null");
        userDto2.setPhoneNumber("null");

        userDto3 = new UserDto();
        userDto3.setName("user3");
        userDto3.setEmail("user3@gmail.com");
        userDto3.setCountry("France");
        userDto3.setBirthday(LocalDate.of(1970, 01, 01));
        userDto3.setGender("null");
        userDto3.setPhoneNumber("null");

        userFilterDto = new UserFilterDto(userDto.getName(), userDto.getEmail());
        invalidUserFilterDto = new UserFilterDto(null, null);
        userFilterDto2 = new UserFilterDto("userNotExist", "userNotExist@gmail.com");

        //adding users
        String userDtoJson = Helper.userDtoAsJsonString(userDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print());

        String userDtoJson2 = Helper.userDtoAsJsonString(userDto2);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print());

        String userDtoJson3 = Helper.userDtoAsJsonString(userDto3);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/singup").content(userDtoJson3)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print());
    }

    @Test
    void servletContextTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBeansOfType(UserController.class));
    }

    @Test
    @DisplayName("Testing search users")
    @Transactional
    void searchUsersIntegrationTest_WhenUsersFound() throws Exception {
        String userFilterDtoJson = Helper.userFilterJsonString(userFilterDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/search-by-filter").content(userFilterDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
        ).andDo(print())
                .andExpect(status().isFound()).andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertThat(jsonArray.get(0).toString()).hasToString(Helper.userDtoAsJsonString(userDto));
    }

    @Test
    @DisplayName("Testing search users when both email and name are null")
    @Transactional
    void searchUsersIntegrationTest_WhenFilterNotValid() throws Exception {
        String invalidUserFilterDtoJson = Helper.userFilterJsonString(invalidUserFilterDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/search-by-filter").content(invalidUserFilterDtoJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn();

        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo(INVALID_FILTER);
    }

    @Test
    @DisplayName("Testing search users when users are not found")
    @Transactional
    void searchUsersIntegrationTest_WhenUsersNotFound() throws Exception {
        String userFilterDto2Json = Helper.userFilterJsonString(userFilterDto2);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/search-by-filter").content(userFilterDto2Json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
                ).andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();

        assertThat(mvcResult.getResponse().getErrorMessage()).isEqualTo("Users matching : " + userFilterDto2.getName() + " "
                + userFilterDto2.getEmail() + ", was not found in DataBase");
    }


}
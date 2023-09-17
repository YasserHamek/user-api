package com.test.userapi.utils;

import com.test.userapi.dto.UserDto;
import com.test.userapi.dto.UserFilterDto;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;

public class Helper {

    private Helper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converting UserDto to Json String
     * @param userDto to be converted
     * @return String representing UserDto as Json String
     * @throws JSONException
     */
    public static String userDtoAsJsonString(UserDto userDto) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", userDto.getName());
        jsonObject.put("birthday",
                userDto.getBirthday() != null ?
                        userDto.getBirthday().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null
        );
        jsonObject.put("country", userDto.getCountry());
        jsonObject.put("email", userDto.getEmail());
        jsonObject.put("phoneNumber", userDto.getPhoneNumber());
        jsonObject.put("gender", userDto.getGender());
        return jsonObject.toString();
    }

    /**
     * Converting UserFilterDto to Json String
     * @param userFilterDto to be converted
     * @return String representing UserDto as Json String
     * @throws JSONException
     */
    public static String userFilterJsonString(UserFilterDto userFilterDto) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", userFilterDto.getName());
        jsonObject.put("email", userFilterDto.getEmail());
        return jsonObject.toString();
    }
}

package com.atc.basictest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.atc.basictest.entity.User;
import com.atc.basictest.entity.UserSetting;
import com.atc.basictest.entity.dto.ResponseDto;
import com.atc.basictest.entity.dto.UserDto;
import com.atc.basictest.entity.dto.UserSettingDto;
import com.atc.basictest.entity.enumeration.UserSettingsKey;
import com.atc.basictest.repository.UserRepository;
import com.atc.basictest.repository.UserSettingRepository;

@Service
public class UserSettingService {
    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;

    public UserSettingService(UserRepository userRepository, UserSettingRepository userSettingRepository) {
        this.userRepository = userRepository;
        this.userSettingRepository = userSettingRepository;
    }

    public List<Map<String, String>> findByUserId(Long userId) {
        List<UserSetting> userSettings = userSettingRepository.findByUserId(userId);

        List<Map<String, String>> settingKeys = mapSettingKeys(userSettings);

        return settingKeys;
    }

    public ResponseDto updateUserSetting(Long userId, UserSettingDto req) {
        List<UserSetting> userSettings = userSettingRepository.findByUserId(userId);

        validateUserSetting(req);
        for (Map<String, String> mapReq : req.getSettingKeys()) {
            for (UserSetting userSetting : userSettings) {
                if (mapReq.containsKey(userSetting.getKey())) {
                    userSetting.setValue(mapReq.get(userSetting.getKey()));
                }
            }
        }

        Optional<User> opUser = userRepository.findById(userId);
        if (!opUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find resource with id " + userId);
        }
        User user = opUser.get();
        UserDto userDto = setUserDto(user);
        List<Map<String, String>> settingKeys = mapSettingKeys(userSettings);

        return new ResponseDto(userDto, settingKeys);
    }

    private UserDto setUserDto(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getSsn(), user.getFirstName(), user.getMiddleName(),
                user.getFamilyName(), user.getBirthDate(), user.getCreatedTime(), user.getUpdatedTime(),
                user.getCreatedBy(), user.getUpdatedBy(), user.isActive(), user.getDeletedTime());
        return userDto;
    }

    private List<Map<String, String>> mapSettingKeys(List<UserSetting> userSettings) {
        List<Map<String, String>> settingKeys = new ArrayList<Map<String, String>>();
        for (UserSetting userSetting : userSettings) {
            Map<String, String> settingKey = new HashMap<String, String>();
            settingKey.put(userSetting.getKey(), userSetting.getValue());
            settingKeys.add(settingKey);
        }
        return settingKeys;
    }

    private void validateUserSetting(UserSettingDto userSetting) {
        String desc = UserSettingsKey.getDescription("widget_order");

        //I push this code change after 12 PM 14/4/2023 because the bug is bugging my mind.
        for (Map<String, String> settingKey : userSetting.getSettingKeys()) {
            String widgetOrder = settingKey.get(desc);
            if (widgetOrder != null) {
                String[] arrOfStr = widgetOrder.split(",");

                int sum = 0;
                for (String a : arrOfStr) {
                    int intA = Integer.parseInt(a);
                    sum += intA;
                    if (intA > 5 && intA < 1) {
                        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                                "Invalid value for field Widget Order, rejected value: " + settingKey.values());
                    }
                }
                if (sum != 15) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "Invalid value for field Widget Order, rejected value: " + settingKey.values());
                }
            }
        }
    }
}

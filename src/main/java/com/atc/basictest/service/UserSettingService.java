package com.atc.basictest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.atc.basictest.entity.User;
import com.atc.basictest.entity.UserSetting;
import com.atc.basictest.entity.dto.ResponseDto;
import com.atc.basictest.entity.dto.UserDto;
import com.atc.basictest.entity.dto.UserSettingDto;
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

        for (Map<String, String> mapReq : req.getSettingKeys()) {
            for (UserSetting userSetting : userSettings) {
                if (mapReq.containsKey(userSetting.getKey())) {
                    userSetting.setValue(mapReq.get(userSetting.getKey()));
                }
            }
        }

        Optional<User> opUser = userRepository.findById(userId);
        if (!opUser.isPresent()) {
            throw new RuntimeException("401");
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
}

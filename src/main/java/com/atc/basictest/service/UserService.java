package com.atc.basictest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atc.basictest.entity.User;
import com.atc.basictest.entity.UserSetting;
import com.atc.basictest.entity.dto.ResponseDto;
import com.atc.basictest.entity.dto.UserDto;
import com.atc.basictest.entity.enumeration.UserSettingsKey;
import com.atc.basictest.repository.UserRepository;
import com.atc.basictest.repository.UserSettingRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;

    public UserService(UserRepository userRepository, UserSettingRepository userSettingRepository) {
        this.userRepository = userRepository;
        this.userSettingRepository = userSettingRepository;
    }

    public Page<User> findAll(Pageable pgb) {
        return userRepository.findAll(pgb);
    }

    public UserDto findUserById(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        if (!opUser.isPresent()) {
            throw new RuntimeException("401");
        }
        User user = opUser.get();
        UserDto userDto = setUserDto(user);
        return userDto;
    }

    private void ValidateUser(User user) {
        String ssn = user.getSsn();
        if (ssn.length() < 16) {
            user.setSsn(("0000000000000000" + ssn).substring(ssn.length()));
        } else if (ssn.length() > 16) {
            throw new RuntimeException("422");
        }
    }

    public ResponseDto createUser(User user) {
        ValidateUser(user);
        User result = userRepository.save(user);
        UserDto userDto = setUserDto(result);
        List<Map<String, String>> settingKeys = new ArrayList<Map<String, String>>();

        settingKeys = createUserSettingDto(result);
        return new ResponseDto(userDto, settingKeys);
    }

    public ResponseDto updateUser(Long id, UserDto req) {
        Optional<User> opUser = userRepository.findById(id);
        if (!opUser.isPresent()) {
            throw new RuntimeException("401");
        }

        User newUser = setNewUser(opUser.get(), req);
        User result = userRepository.save(newUser);
        UserDto userDto = setUserDto(result);
        List<Map<String, String>> settingKeys = new ArrayList<Map<String, String>>();

        settingKeys = getUserSettingDto(result);
        return new ResponseDto(userDto, settingKeys);
    }

    private User setNewUser(User oldUser, UserDto user) {
        User newUser = new User();
        newUser.setId(oldUser.getId());
        newUser.setSsn(oldUser.getSsn());
        newUser.setFirstName(user.getFirstName());
        newUser.setMiddleName(user.getMiddleName());
        newUser.setFamilyName(user.getFamilyName());
        newUser.setBirthDate(user.getBirthDate());
        newUser.setCreatedTime(oldUser.getCreatedTime());
        newUser.setUpdatedTime(LocalDateTime.now());
        newUser.setCreatedBy(oldUser.getCreatedBy());
        newUser.setUpdatedBy(oldUser.getUpdatedBy());
        newUser.setActive(oldUser.isActive());
        newUser.setDeletedTime(oldUser.getDeletedTime());
        return newUser;
    }

    private List<Map<String, String>> createUserSettingDto(User result) {
        List<Map<String, String>> settingKeys = new ArrayList<Map<String, String>>();

        for (UserSettingsKey key : UserSettingsKey.values()) {
            Map<String, String> settingKey = new HashMap<String, String>();
            UserSetting userSetting = new UserSetting();
            userSetting.setKey(key.desc);
            userSetting.setValue(key.constraint);
            userSetting.setUser(result);
            UserSetting resultSetting = userSettingRepository.save(userSetting);

            settingKey.put(resultSetting.getKey(), resultSetting.getValue());
            settingKeys.add(settingKey);
        }

        return settingKeys;
    }

    private List<Map<String, String>> getUserSettingDto(User result) {
        List<Map<String, String>> settingKeys = new ArrayList<Map<String, String>>();
        List<UserSetting> userSettings = userSettingRepository.findByUserId(result.getId());

        for (UserSetting userSetting : userSettings) {
            Map<String, String> settingKey = new HashMap<String, String>();

            settingKey.put(userSetting.getKey(), userSetting.getValue());
            settingKeys.add(settingKey);
        }

        return settingKeys;
    }

    private UserDto setUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setSsn(user.getSsn());
        userDto.setFirstName(user.getFirstName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setFamilyName(user.getFamilyName());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setCreatedTime(user.getCreatedTime());
        userDto.setUpdatedTime(LocalDateTime.now());
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setUpdatedBy(user.getUpdatedBy());
        userDto.setActive(user.isActive());
        userDto.setDeletedTime(user.getDeletedTime());
        return userDto;
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        if (!opUser.isPresent()) {
            throw new RuntimeException("401");
        }

        userRepository.setActiveAndDeletedTime(id, LocalDateTime.now());
    }

    @Transactional(readOnly = false)
    public ResponseDto refreshUser(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        if (!opUser.isPresent()) {
            throw new RuntimeException("401");
        }
        userRepository.setActiveAndDeletedTime(id, true);
        Optional<User> newOpUser = userRepository.findById(id);

        UserDto userDto = setUserDto(newOpUser.get());
        List<Map<String, String>> settingKeys = new ArrayList<Map<String, String>>();

        settingKeys = getUserSettingDto(newOpUser.get());
        return new ResponseDto(userDto, settingKeys);
    }
}

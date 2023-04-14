package com.atc.basictest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atc.basictest.entity.User;
import com.atc.basictest.entity.dto.HttpPageResponse;
import com.atc.basictest.entity.dto.HttpResponse;
import com.atc.basictest.entity.dto.ResponseDto;
import com.atc.basictest.entity.dto.UserDto;
import com.atc.basictest.entity.dto.UserSettingDto;
import com.atc.basictest.service.UserService;
import com.atc.basictest.service.UserSettingService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserSettingService userSettingService;

    public UserController(UserService userService, UserSettingService userSettingService) {
        this.userService = userService;
        this.userSettingService = userSettingService;
    }

    @GetMapping
    public ResponseEntity getUsers(
            @RequestParam(value = "max_records", required = false, defaultValue = "5") Integer max_records,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        Pageable pgb = PageRequest.of(offset, max_records);
        Page<User> pages = userService.findAll(pgb);
        return ResponseEntity.ok(HttpPageResponse.ok(pages.getContent(), pgb.getPageSize(), pgb.getPageNumber()));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        ResponseDto dto = userService.createUser(user);
        return ResponseEntity.ok(HttpResponse.ok(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.findUserById(id);
        List<Map<String, String>> settingKeys = userSettingService.findByUserId(id);
        ResponseDto responseDto = new ResponseDto(userDto, settingKeys);
        return ResponseEntity.ok(HttpResponse.ok(responseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserDto req) {
        ResponseDto dto = userService.updateUser(id, req);
        return ResponseEntity.ok(HttpResponse.ok(dto));
    }

    @PutMapping("/{id}/settings")
    public ResponseEntity updateUserSettings(@PathVariable("id") Long id, @RequestBody UserSettingDto req) {
        ResponseDto dto = userSettingService.updateUserSetting(id, req);
        return ResponseEntity.ok(HttpResponse.ok(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/refresh")
    public ResponseEntity refreshUser(@PathVariable("id") Long id) {
        ResponseDto dto = userService.refreshUser(id);
        return ResponseEntity.ok(HttpResponse.ok(dto));
    }
}

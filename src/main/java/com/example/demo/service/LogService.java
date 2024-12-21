package com.example.demo.service;

import com.example.demo.entity.Login;

import java.util.List;

public interface LogService {

    List<Login> getAllLogin();

    Login getLoginByUserId(String userId);
}
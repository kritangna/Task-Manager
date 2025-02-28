package com.todomanagement.todo_managemnet.service;

import com.todomanagement.todo_managemnet.dto.LoginDto;
import com.todomanagement.todo_managemnet.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);

}

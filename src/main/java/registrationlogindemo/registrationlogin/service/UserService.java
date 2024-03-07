package registrationlogindemo.registrationlogin.service;

import registrationlogindemo.registrationlogin.dto.UserDto;
import registrationlogindemo.registrationlogin.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}

package registrationlogindemo.registrationlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import registrationlogindemo.registrationlogin.dto.UserDto;
import registrationlogindemo.registrationlogin.entity.Role;
import registrationlogindemo.registrationlogin.entity.User;
import registrationlogindemo.registrationlogin.repository.RoleRepository;
import registrationlogindemo.registrationlogin.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role userRole = roleRepository.findByRoleName("ADMIN");
        if (userRole == null) {
            userRole = createAdminRole();
        }
        newUser.setRoles(Arrays.asList(userRole));
        userRepository.save(newUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
         return userRepository.findAll().stream()
                 .map(user -> mapToUserDto(user))
                 .filter(Objects::nonNull)
                 .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role createAdminRole() {
        Role role = new Role();
        role.setRoleName("ADMIN");
        return roleRepository.save(role);
    }
}

package registrationlogindemo.registrationlogin.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;
import registrationlogindemo.registrationlogin.dto.UserDto;
import registrationlogindemo.registrationlogin.entity.User;
import registrationlogindemo.registrationlogin.service.UserService;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public ResponseEntity<UserDto> registration(@Valid @ModelAttribute("user") UserDto userDto,
                                                BindingResult result,
                                                Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && !StringUtils.isEmptyOrWhitespace(existingUser.getEmail())){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }

        userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}

package com.example.org.validators;

import com.example.org.models.User;
import com.example.org.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private final UserService userService;
    private Pattern pattern;
    private Matcher matcher;
    private static final String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        matcher = pattern.matcher(user.getEmail());

        if(userService.findByUsername(user.getUsername()).isPresent())
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует!");

        if(!matcher.matches()) {
            if (user.getEmail().isEmpty()) {
                errors.rejectValue("email", "", "Почта не должна быть пустой!");
            } else {
                errors.rejectValue("email", "", "Неправильный email!");
            }
        }

        if(userService.findByEmail(user.getEmail()).isPresent())
            errors.rejectValue("email", "", "Пользователь с такой почтой уже существует!");

        if(user.getPassword().isEmpty())
            errors.rejectValue("password", "", "Пароль не должен быть пустым");

        if(user.getUsername().isEmpty())
            errors.rejectValue("username", "", "Имя не должно быть пустым!");
    }
}

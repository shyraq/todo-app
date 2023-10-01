package com.example.org.validators;

import com.example.org.models.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class TaskValidator implements Validator {

    private final DateValidator validator;

    @Autowired
    public TaskValidator(DateValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tasks.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Tasks task = (Tasks)target;
        int monthId = task.getMonth().getId();
        StringBuilder monthIdStr = new StringBuilder();


        if(monthId < 10){
            monthIdStr.append("0").append(monthId);
        }else {
            monthIdStr.append(monthId);
        }

        if(!validator.validate(task.getDay() + "/" + monthIdStr + "/" + LocalDateTime.now().getYear())) {
            if (task.getDay().isEmpty()) {
                errors.rejectValue("day", "", "День не должен быть пустой!");
            }
            else {
                errors.rejectValue("day", "", "Дата должна соответствовать месяцу!");
            }
        }

        if(task.getDescription().isEmpty()){
            errors.rejectValue("description", "", "Цель не должна быть пустой!");
        }
    }
}

package com.example.org.validators;

import com.example.org.models.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if(!validator.validate(task.getDay())) {
            if (task.getDay().isEmpty()) {
                errors.rejectValue("day", "", "Дата не должна быть пустой!");
            }else {
                errors.rejectValue("day", "", "Дата должна соблюдать вид(ДД/ММ/ГГГГ)!");
            }
        }

        if(task.getDescription().isEmpty()){
            errors.rejectValue("description", "", "Цель не должна быть пустой!");
        }
    }
}

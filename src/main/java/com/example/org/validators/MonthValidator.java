package com.example.org.validators;

import com.example.org.models.Month;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MonthValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Month.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Month month = (Month) target;

        if(month.getMonth() == null){
            errors.rejectValue("month", "", "Месяц не должен быть пустым");
        }
    }
}

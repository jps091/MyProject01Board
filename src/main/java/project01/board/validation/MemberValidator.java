package project01.board.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project01.board.member.Member;

@Component
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member form = (Member)target;

        if(!StringUtils.hasText(form.getName())){
            errors.rejectValue("name", "required");
        }
        if(form.getAge() == null || form.getAge() < 5 || form.getAge() > 20){
            errors.rejectValue("age", "range", new Object[]{5, 20}, null);
        }
    }
}

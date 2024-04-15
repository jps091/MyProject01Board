package project01.board.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project01.board.controller.BoardForm;

@Component
public class BoardFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoardForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BoardForm boardForm = (BoardForm)target;

        if(!StringUtils.hasText(boardForm.getMemberName())){
            errors.rejectValue("memberName", "required");
        }
        if(boardForm.getTitle() == null || boardForm.getTitle().length() < 2) {
            errors.rejectValue("title", "min", new Object[]{2}, null);
        }
        if(boardForm.getContent() == null || boardForm.getContent().length() > 6){
            errors.rejectValue("content", "max", new Object[]{5}, null);
        }

        if(boardForm.getTitle() != null && boardForm.getContent() != null){
            int totalLen = boardForm.getTitle().length() + boardForm.getContent().length();
            if(totalLen > 10){
                errors.reject("totalInputMax", new Object[]{10, totalLen}, null);
            }
        }
    }
}

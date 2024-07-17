package project01.board.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter @Setter
public class BoardForm {
    @NotBlank
    private String memberName;

    @NotBlank
    @Length(min = 2, max = 4)
    private String title;

    @NotBlank
    @Length(min = 2, max = 5)
    private String content;

    private List<String> responseMethods; // 다중선택
    private String boardTypeCode; // 스크롤바
}

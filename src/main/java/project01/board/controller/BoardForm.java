package project01.board.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BoardForm {
    private String memberName;
    private String title;
    private String content;

    private List<String> responseMethods; // 다중선택
    private String boardTypeCode; // 스크롤바
}

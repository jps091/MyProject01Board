package project01.board.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Board {
    private Long boardId;
    private String title;
    private String content;
    private Long memberId;

    private List<String> responseMethods; // 다중선택
    private String boardTypeCode; // 스크롤바
}

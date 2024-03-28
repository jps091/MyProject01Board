package project01.board.board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Board {
    private Long boardId;
    private String title;
    private String content;
    private Long memberId;
}

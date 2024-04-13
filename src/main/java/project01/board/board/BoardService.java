package project01.board.board;

import project01.board.controller.BoardForm;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    //Board CreateBoard(Long memberId, String title, String contents);
    Board CreateBoard(Long memberId, BoardForm form);
    List<Board> findAll();
    Board findById(Long boardId);
    void Update(Long boardId, Board updateBoard);
    boolean Delete(Long boardId);
}

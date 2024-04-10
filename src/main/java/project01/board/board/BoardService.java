package project01.board.board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    Board CreateBoard(Long memberId, String title, String contents);
    List<Board> findAll();
    Board findById(Long boardId);
    void Update(Long boardId, Board updateBoard);
    boolean Delete(Long boardId);
}

package project01.board.board;

import project01.board.member.Member;
import project01.board.repository.BoardRepository;
import project01.board.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BoardServiceImpl implements  BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public BoardServiceImpl(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Override

    public Board CreateBoard(Long memberId, String title, String contents) {
        if(memberRepository.findById(memberId).isEmpty()){
            throw new BoardNotFoundException("멤버가 존재 하지 않습니다");
        }

        Board board = new Board();
        board.setMemberId(memberId);
        board.setTitle(title);
        board.setContent(contents);

        boardRepository.save(board);
        return  board;
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long boardId) {
/*        if(boardRepository.findById(boardId).isPresent()){
            return boardRepository.findById(boardId).get();
        }else{
            throw new NoSuchElementException("게시판이 존재하지 않습니다.");
        }*/
        return boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException("게시판 ID " + boardId + "는 존재하지 않습니다."));
    }

    @Override
    public void Update(Long boardId, Board updateBoard) {
        if(boardRepository.findById(boardId).isEmpty()){
            throw new BoardNotFoundException("변경할 게시판이 존재 하지 않습니다.");
        }
        boardRepository.update(boardId, updateBoard);
    }

    @Override
    public boolean Delete(Long boardId) {
        if(boardRepository.findById(boardId).isPresent()){
            boardRepository.delete(boardId);
            return true;
        }else{
            throw new BoardNotFoundException("삭제할 게시판이 존재 하지 않습니다.");
        }
    }
}

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
    public void CreateBoard(Long memberId, String title, String contents) {
        if(memberRepository.findById(memberId).isEmpty()){
            throw new NoSuchElementException("멤버가 존재 하지 않습니다");
        }

        Board board = new Board();
        board.setMemberId(memberId);
        board.setTitle(title);
        board.setContent(contents);

        boardRepository.save(board);
    }

    @Override
    public List<Board> findAll() {
        return null;
    }

    @Override
    public Optional<Board> findById(Long boardId) {
        return Optional.empty();
    }

    @Override
    public void Update(Long boardId, Board updateBoard) {

    }

    @Override
    public boolean Delete(Long boardId) {
        return false;
    }
}

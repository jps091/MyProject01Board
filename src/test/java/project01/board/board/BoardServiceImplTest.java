package project01.board.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project01.board.member.Member;
import project01.board.repository.BoardMemoryRepository;
import project01.board.repository.BoardRepository;
import project01.board.repository.MemberMemoryRepository;
import project01.board.repository.MemberRepository;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardServiceImplTest {

    BoardService boardService;
    MemberMemoryRepository memberRepository;
    BoardMemoryRepository boardMemoryRepository;
    @BeforeEach
    public void beforeEach(){
        boardMemoryRepository = new BoardMemoryRepository();
        memberRepository = new MemberMemoryRepository();
        boardService = new BoardServiceImpl(boardMemoryRepository, memberRepository);
    }

    @Test
    void createBoard() {
        //given 초기셋팅 : 매핑할 멤버생성
        Member member = new Member();
        memberRepository.save(member);

        //when 실행
        boolean empty = memberRepository.findById(member.getMemberId()).isEmpty();

        //then 검증
        assertThat(empty).isEqualTo(false);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        //
        Board board = new Board();
        boardMemoryRepository.save(board);

        //
        Board findBoard = boardService.findById(board.getBoardId());

        //
        assertThat(findBoard).isEqualTo(board);

    }

    @Test
    void update() {
        Board existBoard = new Board();
        Board updateBoard = new Board();
       boardMemoryRepository.save(existBoard);


        boardService.Update(existBoard.getBoardId(), updateBoard);
        Board findBoard = boardService.findById(updateBoard.getBoardId());

        assertThat(findBoard).isEqualTo(updateBoard);
    }

    @Test
    void delete() {
        Board existBoard = new Board();
        boardMemoryRepository.save(existBoard);

        boolean delete = boardService.Delete(existBoard.getBoardId());

        assertThrows(NoSuchElementException.class, ()->{
            Board findBoard = boardService.findById(existBoard.getBoardId());
        });
    }
}
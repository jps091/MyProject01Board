package project01.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project01.board.board.Board;
import project01.board.board.BoardService;
import project01.board.member.Member;
import project01.board.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/boards")
@Controller
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    public BoardController(BoardService boardService, MemberRepository memberRepository) {
        this.boardService = boardService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/createForm")
    public String createBoardForm(){
        return "/boards/createBoardForm";
    }
    @PostMapping
    public String createBoard(Model model, @ModelAttribute BoardForm boardForm){
        Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
        boardService.CreateBoard(memberId, boardForm.getTitle(), boardForm.getContent());
        model.addAttribute("cratedBoard", boardForm);
        return "/boards/createdBoard";
    }
    @GetMapping
    public String findAllBoards(Model model){
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "/boards/boardList";
    }

    @GetMapping("/findForm")
    public String findBoardForm(){
        return "/boards/findBoardForm";
    }

    @GetMapping("/memberIdBoard")
    public String findBoard(Model model, @RequestParam("memberName") String memberName){
        Long memberId = memberRepository.findByNameId(memberName);
        List<Board> boards = boardService.findAll();
        List<Board> findBoards = new ArrayList<>();
        for (Board board : boards) {
            if(board.getMemberId().equals(memberId)){
                findBoards.add(board);
            }
        }
        model.addAttribute("findBoards", findBoards);
        return "/boards/finedBoard";
    }

    @GetMapping("/updateForm")
    public String updateBoardForm(){
        return "/boards/updateBoardForm";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(Model model, @ModelAttribute BoardForm boardForm){
        Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
        Board updateBoard = new Board();
        updateBoard.setMemberId(memberId);
        updateBoard.setTitle(boardForm.getTitle());
        updateBoard.setContent(boardForm.getContent());
        List<Board> boards = boardService.findAll();
        for (Board board : boards) {
            if(board.getMemberId().equals(memberId) && board.getTitle().equals(boardForm.getTitle())){
                //updateBoard = board;
                boardService.Update(board.getBoardId(), updateBoard);
                break;
            }
        }
        model.addAttribute("updateBoard", updateBoard);
        return "/boards/updatedBoard";
    }

    @GetMapping("/deleteForm")
    public String deleteBoardForm(){
        return "/boards/deleteBoardForm";
    }

    @PostMapping("/deleteBoard")
    public String deleteBoard(Model model, @RequestParam("memberName") String memberName){
        Long memberId = memberRepository.findByNameId(memberName);
        List<Board> boards = boardService.findAll();
        Board deleteBoard = new Board();
        for (Board board : boards) {
            if(board.getMemberId().equals(memberId)){
                boardService.Delete(board.getBoardId());
                deleteBoard = board;
                break;
            }
        }
        model.addAttribute("deleteBoard", deleteBoard);
        return "/boards/deletedBoard";
    }
}

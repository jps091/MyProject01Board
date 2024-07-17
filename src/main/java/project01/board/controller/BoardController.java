package project01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project01.board.Utiliry.BoardTypeCode;
import project01.board.board.Board;
import project01.board.board.BoardNotFoundException;
import project01.board.board.BoardService;
import project01.board.member.MemberNotFoundException;
import project01.board.repository.MemberRepository;
import project01.board.validation.BoardFormValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RequestMapping("/boards")
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;
    private final BoardFormValidator boardFormValidator;


/*    public BoardController(BoardService boardService, MemberRepository memberRepository) {
        this.boardService = boardService;
        this.memberRepository = memberRepository;
    }*/

    @ModelAttribute("responseMethods")
    public Map<String,String> responseMethods(){
        Map<String, String> responseMethods = new LinkedHashMap<>();
        responseMethods.put("EMAIL", "이메일");
        responseMethods.put("MESSAGE", "문자");
        responseMethods.put("KAKAO", "카톡");
        return responseMethods;
    }

    @ModelAttribute("boardTypeCodes")
    public List<BoardTypeCode> boardTypeCodes(){
        List<BoardTypeCode> boardTypeCodes = new ArrayList<>();
        boardTypeCodes.add(new BoardTypeCode("DELIVERY", "배송 문의"));
        boardTypeCodes.add(new BoardTypeCode("ITEM", "상품 문의"));
        boardTypeCodes.add(new BoardTypeCode("ETC", "기타 문의"));
        return  boardTypeCodes;
    }

    @GetMapping("/board/{boardId}")
    public String resultBoard(@PathVariable Long boardId, Model model){
        Board resultBoard = boardService.findById(boardId);
        model.addAttribute("resultBoard", resultBoard);
        return "/boards/board";
    }

    @GetMapping("/create")
    public String createBoardForm(Model model){
        model.addAttribute("boardForm", new BoardForm());
        return "/boards/createForm";
    }
    //@PostMapping("/create")
    public String createBoard(Model model, @ModelAttribute BoardForm boardForm, RedirectAttributes redirectAttributes){
        try{
            Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
            Long boardId = boardService.CreateBoard(memberId, boardForm).getBoardId();
            redirectAttributes.addAttribute("boardId", boardId);
            model.addAttribute("board", boardForm);
            return "redirect:/boards/board/{boardId}";
        }catch (MemberNotFoundException e){
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/createForm";
        }
    }

    //@PostMapping("/create")
    public String createBoardV1(@ModelAttribute BoardForm boardForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        try{
            if(!StringUtils.hasText(boardForm.getMemberName())){
                bindingResult.rejectValue("memberName", "required");
            }
            if(boardForm.getTitle() == null || boardForm.getTitle().length() < 2) {
                bindingResult.rejectValue("title", "min", new Object[]{2}, null);
                model.addAttribute("boardForm", boardForm);
            }
            if(boardForm.getContent() == null || boardForm.getContent().length() > 6){
                bindingResult.rejectValue("content", "max", new Object[]{5}, null);
            }

            if(boardForm.getTitle() != null && boardForm.getContent() != null){
                int totalLen = boardForm.getTitle().length() + boardForm.getContent().length();
                if(totalLen > 10){
                    bindingResult.reject("totalInputMax", new Object[]{10, totalLen}, null);
                }
            }
            if(bindingResult.hasErrors()) {
                log.info("errors={}", bindingResult);
                model.addAttribute("boardForm", boardForm);
                return "/boards/createForm";
            }

            // 성공 로직
            Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
            Long boardId = boardService.CreateBoard(memberId, boardForm).getBoardId();
            //model.addAttribute("boardForm", boardForm);
            redirectAttributes.addAttribute("boardId", boardId);
            return "redirect:/boards/board/{boardId}";
        }catch (MemberNotFoundException e){
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/createForm";
        }
    }

    //@PostMapping("/create")
    public String createBoardV2(@ModelAttribute BoardForm boardForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        try{
            boardFormValidator.validate(boardForm, bindingResult);

            if(bindingResult.hasErrors()) {
                log.info("errors={}", bindingResult);
                model.addAttribute("boardForm", boardForm);
                return "/boards/createForm";
            }

            // 성공 로직
            Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
            Long boardId = boardService.CreateBoard(memberId, boardForm).getBoardId();
            redirectAttributes.addAttribute("boardId", boardId);
            return "redirect:/boards/board/{boardId}";
        }catch (MemberNotFoundException e){
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/createForm";
        }
    }

    @PostMapping("/create")
    public String createBoardV3(@Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        try{

            if(bindingResult.hasErrors()) {
                log.info("errors={}", bindingResult);
                //model.addAttribute("boardForm", boardForm);
                return "/boards/createForm";
            }

            // 성공 로직
            Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
            Long boardId = boardService.CreateBoard(memberId, boardForm).getBoardId();
            redirectAttributes.addAttribute("boardId", boardId);
            return "redirect:/boards/board/{boardId}";
        }catch (MemberNotFoundException e){
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/createForm";
        }
    }

    @GetMapping
    public String findAllBoards(Model model){
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "/boards/boards";
    }

    @GetMapping("/find")
    public String findBoardForm(){
        return "/boards/findForm";
    }

    @GetMapping("/find/boards")
    public String findBoard(Model model, @RequestParam("memberName") String memberName){
        try {
            Long memberId = memberRepository.findByNameId(memberName);
            try{
                List<Board> findBoards = findBoards(memberId);
                model.addAttribute("boards", findBoards);
                return "/boards/boards";
            }catch (BoardNotFoundException e){
                model.addAttribute("message", "회원님이 작성하신 게시글이 없습니다.");
                return "/boards/findForm";
            }
        }catch (MemberNotFoundException e) {
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/findForm";
        }
    }

    private List<Board> findBoards(Long memberId) {
        List<Board> boards = boardService.findAll();
        List<Board> findBoards = new ArrayList<>();
        for (Board board : boards) {
            if(board.getMemberId().equals(memberId)){
                findBoards.add(board);
            }
        }
        if(findBoards.isEmpty()){
            throw new BoardNotFoundException(memberId + " : 작성한 게시글 없음");
        }
        return findBoards;
    }

    @GetMapping("/update")
    public String updateBoardForm(){
        return "/boards/updateForm";
    }

    @PostMapping("/update")
    public String updateBoard(Model model, @ModelAttribute BoardForm boardForm, RedirectAttributes redirectAttributes){
        try{
            Long memberId = memberRepository.findByNameId(boardForm.getMemberName());
            try{
                Board existBoard = findExistBoard(boardForm, memberId);
                Board updateBoard = createUpdateBoard(boardForm, existBoard);
                boardService.Update(existBoard.getBoardId(), updateBoard);
                model.addAttribute("updateBoard", updateBoard);
                redirectAttributes.addAttribute("boardId", existBoard.getBoardId());
                return "redirect:/boards/board/{boardId}";
            }catch (BoardNotFoundException e) {
                model.addAttribute("message", "작성자와 게시글 제목이 일치하는 게시글이 없습니다.");
                return "/boards/updateForm";
            }
        }catch (MemberNotFoundException e){
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/updateForm";
        }
    }

    private Board findExistBoard(BoardForm boardForm, Long memberId) {
        List<Board> boards = boardService.findAll();
        for (Board board : boards) {
            if(board.getMemberId().equals(memberId) && board.getTitle().equals(boardForm.getTitle())){
                //updateBoard = board;
                //boardService.Update(board.getBoardId(), updateBoard);
                return board;
            }
        }
        throw new BoardNotFoundException(boardForm.getMemberName() + " 이 작성한 " + boardForm.getTitle() + "의 글은 없습니다.");
    }

    private Board createUpdateBoard(BoardForm boardForm, Board existBoard) {
        Board createBoard = new Board();
        createBoard.setMemberId(existBoard.getMemberId());
        createBoard.setTitle(boardForm.getTitle());
        createBoard.setContent(boardForm.getContent());
        createBoard.setBoardTypeCode(existBoard.getBoardTypeCode());
        createBoard.setResponseMethods(existBoard.getResponseMethods());
        return createBoard;
    }

    @GetMapping("/deleteForm")
    public String deleteBoardForm(){
        return "/boards/deleteBoardForm";
    }

    @PostMapping("/deleteBoard")
    public String deleteBoard(Model model, @RequestParam("memberName") String memberName){
        try {
            Long memberId = memberRepository.findByNameId(memberName);
            try {
                Board deleteBoard = findBoardDelete(memberId);
                boardService.Delete(deleteBoard.getBoardId());
                model.addAttribute("deleteBoard", deleteBoard);
                return "redirect:/";
            } catch (BoardNotFoundException e) {
                model.addAttribute("message", "삭제할 게시글이 없습니다.");
                return "/boards/deleteBoardForm";
            }
        }catch (MemberNotFoundException e){
            model.addAttribute("message", "존재하지 않는 회원 입니다.");
            return "/boards/deleteBoardForm";
        }
    }

    private Board findBoardDelete(Long memberId) {
        List<Board> boards = boardService.findAll();
        for (Board board : boards) {
            if(board.getMemberId().equals(memberId)){
                return board;
            }
        }
        throw new BoardNotFoundException(memberId + " : 작성한 게시글이 없습니다.");
    }
}

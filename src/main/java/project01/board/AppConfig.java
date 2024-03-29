package project01.board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project01.board.board.BoardService;
import project01.board.board.BoardServiceImpl;
import project01.board.member.MemberService;
import project01.board.member.MemberServiceImpl;
import project01.board.repository.BoardMemoryRepository;
import project01.board.repository.BoardRepository;
import project01.board.repository.MemberMemoryRepository;
import project01.board.repository.MemberRepository;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public BoardService boardService(){
        return new BoardServiceImpl(boardRepository(), memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        return new MemberMemoryRepository();
    }
    @Bean
    public BoardRepository boardRepository(){
        return new BoardMemoryRepository();
    }
}

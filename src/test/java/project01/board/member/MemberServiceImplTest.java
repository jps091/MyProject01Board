package project01.board.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project01.board.repository.MemberMemoryRepository;
import project01.board.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {


    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemberMemoryRepository();
        memberService = new MemberServiceImpl(memberRepository);
    }
    @Test
    void findByName() {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring");
        member2.setName("find");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember = memberService.findByName("find");

        //assertThat(findMember.getName()).isEqualTo(member2.getName());
        assertThrows(MemberNotFoundException.class, () -> {
                Member find = memberService.findByName("java");
                });
    }

    @Test
    void 업데이트(){
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring");
        member2.setName("find");
        member1.setAge(20);
        member2.setAge(30);
        memberRepository.save(member1);
       //memberRepository.save(member2);
        member2.setMemberId(member1.getMemberId());

        memberService.Update(member1.getMemberId(), member2);
        List<Member> list = memberRepository.findAll();
        int len = list.size();
        Member update = memberRepository.findById(member1.getMemberId()).get();

        assertThat(update.getAge()).isSameAs(30);
    }
}
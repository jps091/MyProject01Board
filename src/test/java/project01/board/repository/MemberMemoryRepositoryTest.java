package project01.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import project01.board.member.Member;

import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberMemoryRepositoryTest {

    MemberMemoryRepository repository = new MemberMemoryRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    @Test
    void save() {
        //given 초기셋팅 : 저장할 멤버 생성
        Member member = new Member();
        member.setName("test");
        member.setAge(30);

        //when 실행
        repository.save(member);

        //then 검증
        List<Member> memberList = repository.findAll();
        int size = memberList.size();
        assertThat(size).isSameAs(1);
    }

    @Test
    void findAll() {
        //given 찾을멤버 생성
        Member member = new Member();
        member.setName("test");
        member.setAge(30);
        Member member2 = new Member();
        member2.setName("test2");
        member2.setAge(2);
        repository.save(member);
        repository.save(member2);

        //when 찾기실행
        List<Member> list = repository.findAll();

        //then 검증
        assertThat(list.size()).isSameAs(2);
    }

    @Test
    void update() {
        //given 기존멤버, 덮어쓸 멤버 생성
        Member member1 = new Member();
        member1.setName("exist");
        member1.setAge(10);
        repository.save(member1);

        //when 업데이트
        Member member2 = new Member();
        member2.setName("new");
        member2.setAge(20);
        repository.update(member1.getMemberId(), member2);

        //then 검증
        Optional<Member> id = repository.findById(member2.getMemberId());
        Member findMember = id.get();
        assertThat(findMember.getAge()).isSameAs(member2.getAge());
    }

    @Test
    void delete() {
        //given 삭제할 멤버 우선 생성
        Member member = new Member();
        member.setName("test");
        member.setAge(30);
        repository.save(member);

        //when 찾기실행
        repository.delete(member.getMemberId());

        //then 검증
        Optional<Member> findMemberOptional = repository.findById(member.getMemberId());
        assertThrows(NoSuchElementException.class, () -> {
            findMemberOptional.get();
        });
        //assertFalse(findMemberOptional.isPresent());
    }

    @Test
    void 이름으로_키_찾기(){
        Member member = new Member();
        member.setName("test");
        member.setAge(30);
        repository.save(member);

        Long findKey = repository.findByNameId("test");

        assertThat(findKey).isEqualTo(member.getMemberId());
    }
}
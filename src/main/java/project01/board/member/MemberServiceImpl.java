package project01.board.member;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import project01.board.repository.MemberMemoryRepository;
import project01.board.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("member ID " + memberId + "는 존재하지 않습니다."));
    } // orElse 사용법

    @Override
    public Member findByName(String name){
        List<Member> members = findAllMember();

        for (Member find : members) {
            if(find.getName().equals(name)) {
                return find;
            }
        }
        throw new MemberNotFoundException(name + " 멤버가 존재 하지 않습니다.");
    }

    @Override
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member Update(Long id, Member updateMember) {
        if(memberRepository.findById(id).isEmpty()){
            throw new MemberNotFoundException("수정할 멤버가 존재 하지 않습니다.");
        }
        memberRepository.update(id, updateMember);
        return memberRepository.findById(id).get();
    }

    @Override
    public void Delete(Long id) {
        if(memberRepository.findById(id).isPresent()){
            memberRepository.delete(id);
            return;
        }
        throw new MemberNotFoundException("삭제할 멤버가 존재하지 않습니다.");
    }
}

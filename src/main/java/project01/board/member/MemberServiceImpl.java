package project01.board.member;

import project01.board.repository.MemberMemoryRepository;
import project01.board.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MemberServiceImpl implements MemberService{

    MemberRepository memberRepository = new MemberMemoryRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public void Update(Long id, Member updateMember) {
        if(memberRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("수정할 멤버가 존재 하지 않습니다.");
        }
        memberRepository.update(id, updateMember);
    }

    @Override
    public boolean Delete(Long id) {
        if(memberRepository.findById(id).isPresent()){
            memberRepository.delete(id);
            return true;
        }else{
            System.out.println("삭제할 멤버가 존재 하지 않습니다.");
            return false;
        }
    }
}

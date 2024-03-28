package project01.board.member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    void join(Member member);
    Optional<Member> findMember(Long memberId);
    List<Member> findAllMember();
    void Update(Long id, Member updateMember);
    boolean Delete(Long id);
}

package project01.board.member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    void join(Member member);
    Member findMember(Long memberId);
    Member findByName(String name);
    List<Member> findAllMember();
    Member Update(Long id, Member updateMember);
    boolean Delete(Long id);
}

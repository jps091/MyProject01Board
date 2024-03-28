package project01.board.repository;

import project01.board.member.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberMemoryRepository implements MemberRepository{

    Map<Long, Member> repository = new HashMap<>();

    @Override
    public void save(Member entity) {

    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public void update(Member entity) {

    }

    @Override
    public void delete(Long id) {

    }
}

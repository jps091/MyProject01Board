package project01.board.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project01.board.Utiliry.IdConstructor;
import project01.board.board.BoardNotFoundException;
import project01.board.member.Member;
import project01.board.member.MemberNotFoundException;

import java.util.*;

@Component
@Slf4j
public class MemberMemoryRepository implements MemberRepository{

    Map<Long, Member> store = new HashMap<>();
    IdConstructor idConstructor = IdConstructor.getIdConstructor();

    @Override
    public void save(Member entity) {
         entity.setMemberId(idConstructor.getMemberId());
         store.put(entity.getMemberId(), entity);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Long findByNameId(String name){
        Set<Long> keys = store.keySet();
        for (Long key : keys) {
            if(store.get(key).getName().equals(name)){
                return key;
            }
        }
        throw new MemberNotFoundException(name + " 회원을 찾을수 없습니다.");
    }

    @Override
    public void update(Long id, Member updateEntity) {
        store.put(id, updateEntity);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }
    public void clearStore(){
        store.clear();
    }
}

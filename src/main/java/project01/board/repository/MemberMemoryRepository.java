package project01.board.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project01.board.Utiliry.IdConstructor;
import project01.board.member.Member;

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
        throw new NoSuchElementException("해당 멤버 이름이 존재 하지 않습니다.");
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

package project01.board.repository;

import org.springframework.stereotype.Component;
import project01.board.Utiliry.IdConstructor;
import project01.board.board.Board;

import java.util.*;

@Component
public class BoardMemoryRepository implements BoardRepository{

    Map<Long, Board> store = new HashMap<>();
    IdConstructor idConstructor = IdConstructor.getIdConstructor();
    @Override
    public void save(Board entity) {
        entity.setBoardId(idConstructor.getBoardId());
        store.put(entity.getBoardId(), entity);
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void update(Long id, Board updateEntity) {
        updateEntity.setBoardId(id);
        store.replace(id, updateEntity);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    public void clearStore(){
        store.clear();
    }
}

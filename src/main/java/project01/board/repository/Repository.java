package project01.board.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    void save(T entity);
    List<T> findAll();
    Optional<T> findById(Long id);
    void update(Long id, T updateEntity);
    void delete(Long id);
}

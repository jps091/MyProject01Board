package project01.board.repository;

import java.util.List;

public interface Repository<T> {

    void save(T entity);
    List<T> findAll();
    void update(T entity);
    void delete(Long id);
}

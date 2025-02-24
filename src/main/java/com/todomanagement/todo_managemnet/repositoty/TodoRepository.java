package com.todomanagement.todo_managemnet.repositoty;

import com.todomanagement.todo_managemnet.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}

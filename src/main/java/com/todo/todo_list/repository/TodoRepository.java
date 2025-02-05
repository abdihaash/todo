package com.todo.todo_list.repository;


import com.todo.todo_list.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}


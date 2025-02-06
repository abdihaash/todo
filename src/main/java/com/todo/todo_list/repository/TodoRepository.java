package com.todo.todo_list.repository;


import com.todo.todo_list.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findAll(Pageable pageable); // ✅ Enables pagination
}


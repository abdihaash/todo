package com.todo.todo_list.service;

import com.todo.todo_list.model.Todo;
import com.todo.todo_list.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // ✅ Correct import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // ✅ Ensure this method exists
    public Page<Todo> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }



    public void saveOrUpdate(Todo todo) {
        Optional<Todo> existingTodo = todoRepository.findByTitle(todo.getTitle());
        if (existingTodo.isPresent()) {
            throw new IllegalArgumentException("Task with this title already exists!");
        }
        todoRepository.save(todo);
    }

    @Transactional
    public void save(Todo todo) {
        todoRepository.save(todo);
        System.out.println("✅ Task saved: " + todo.getId() + " - Completed: " + todo.isCompleted());
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return todoRepository.existsById(id); // ✅ Corrected implementation
    }

    public boolean updateTodo(Todo todo) {
        return todoRepository.findById(todo.getId()).map(existingTodo -> {
            existingTodo.setTitle(todo.getTitle());
            todoRepository.save(existingTodo);
            return true;
        }).orElse(false); // ✅ Returns `false` instead of throwing an error
    }
}

package com.todo.todo_list.service;


import com.todo.todo_list.model.Todo;
import com.todo.todo_list.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public void saveOrUpdate(Todo todo) {
        todoRepository.save(todo);
    }

    @Transactional
    public void save(Todo todo) {
        todoRepository.save(todo);
        System.out.println("✅ Task saved: " + todo.getId() + " - Completed: " + todo.isCompleted()); // Debugging
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return false;
    }



    public void updateTodo(Todo todo) {
        Todo existingTodo = todoRepository.findById(todo.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));  // ❌ Causes 500 if ID is missing
        existingTodo.setTitle(todo.getTitle());
        todoRepository.save(existingTodo);
    }


}

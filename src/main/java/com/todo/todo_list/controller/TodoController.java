package com.todo.todo_list.controller;


import com.todo.todo_list.model.Todo;
import com.todo.todo_list.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        return "index";
    }


    @GetMapping("/add")
    public String showAddTodoForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "add_todo";
    }

    @GetMapping("/all")
    @ResponseBody // Ensures response is JSON instead of a Thymeleaf page
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }


    @PostMapping("/save")
    public String saveTodo(@ModelAttribute("todo") Todo todo) {
        todoService.saveOrUpdate(todo);
        return "redirect:/todos";
    }



    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody Map<String, Boolean> updates) {
        Todo todo = todoService.getTodoById(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        boolean completed = updates.get("completed");

        System.out.println("Updating Task ID: " + id + " - Completed: " + completed); // ✅ Debugging

        todo.setCompleted(completed);
        todoService.save(todo);

        return ResponseEntity.ok(todo);
    }






    @PostMapping("/update")
    public String updateTodo(@ModelAttribute("todo") Todo todo) {
        todoService.updateTodo(todo);
        return "redirect:/todos";  // ✅ Redirect back to list after updating
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }



}

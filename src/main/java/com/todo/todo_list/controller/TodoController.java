package com.todo.todo_list.controller;


import com.todo.todo_list.model.Todo;
import com.todo.todo_list.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;



import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;




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

    @GetMapping
    public String viewHomePage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size); // ✅ No casting needed
        Page<Todo> todoPage = todoService.getAllTodos(pageable); // ✅ Pass directly

        model.addAttribute("todos", todoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", todoPage.getTotalPages());

        return "index"; // ✅ Loads paginated data into the frontend
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

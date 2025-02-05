package com.todo.todo_list.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-arguments constructor
@AllArgsConstructor // Generates an all-arguments constructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    @Transient
    private boolean checked;





    // Getters and setters
}



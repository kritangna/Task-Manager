package com.todomanagement.todo_managemnet.controller;


import com.todomanagement.todo_managemnet.dto.TodoDto;
import com.todomanagement.todo_managemnet.entity.Todo;
import com.todomanagement.todo_managemnet.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {

    private final ModelMapper modelMapper;
    private TodoService todoService;

    // Build Add Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {

        TodoDto savedTodo = todoService.addTodo(todoDto);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Build GET Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable("id") Long id){

        TodoDto todoDto = todoService.getTodoById(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Build GET ALL Todos REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos()
    {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // Build Update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long id) {

        TodoDto savedTodo = todoService.updateTodo(todoDto, id);
        return ResponseEntity.ok(savedTodo);
    }

    // Build DELETE Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id)
    {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Deleted Todo Successfully with id: " + id);
    }

    // Build COMPLETE Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTod(@PathVariable("id") Long id)
    {
        TodoDto todoDto = todoService.completeTodo(id);
        return ResponseEntity.ok(todoDto);
    }

    // Build INCOMPLETE Todo RET API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long id)
    {
        TodoDto todoDto = todoService.inCompleteTodo(id);
        return ResponseEntity.ok(todoDto);
    }

}

package com.todomanagement.todo_managemnet.service.impl;

import com.todomanagement.todo_managemnet.dto.TodoDto;
import com.todomanagement.todo_managemnet.entity.Todo;
import com.todomanagement.todo_managemnet.exception.ResourceNotFoundException;
import com.todomanagement.todo_managemnet.repositoty.TodoRepository;
import com.todomanagement.todo_managemnet.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        // Convert Todo entity
//        Todo todo = new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());

        Todo todo = modelMapper.map(todoDto, Todo.class);


        Todo savedTodo = todoRepository.save(todo);

        // Convert Todo Entity into TodoDto
//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());
//        savedTodoDto.setDescription(savedTodo.getDescription());
//        savedTodoDto.setCompleted(savedTodo.isCompleted());
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not found with id: " + id));

        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);

        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream().map((todo) ->
                modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        todoRepository.save(todo);

        TodoDto savedTodoDto = modelMapper.map(todo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Todo Node Found with id: " + id));
        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}

package com.epam.esm.controller;

import com.epam.esm.exceptions.InvalidValueException;
import com.epam.esm.model.Task;
import com.epam.esm.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, BindingResult bindingResult) {
        checkingResponse(bindingResult);
        log.info("Received request to create task: {}", task);
        Task createdTask = taskService.createTask(task);
        log.info("Task created successfully: {}", createdTask);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@Positive @PathVariable Long taskId) {
        log.info("Received request to get task by id: {}", taskId);
        checkPositiveId(taskId);
        Task task = taskService.getTaskById(taskId);
        log.info("Task retrieved successfully: {}", task);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks( ) {
        log.info("Received request to get all tasks");
        List<Task> tasks = taskService.getAllTasks();
        log.info("Retrieved {} tasks", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @Positive @PathVariable Long taskId,
            @Valid @RequestBody Task task,
            BindingResult bindingResult) {
        checkingResponse(bindingResult);
        log.info("Received request to update task with id {}: {}", taskId, task);
        Task updatedTask = taskService.updateTask(taskId, task);
        log.info("Task updated successfully: {}", updatedTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@Positive @PathVariable Long taskId) {
        log.info("Received request to delete task with id: {}", taskId);
        checkPositiveId(taskId);
        taskService.deleteTask(taskId);
        log.info("Task deleted successfully");
        return ResponseEntity.noContent().build();
    }

    private static void checkingResponse(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Invalid request received");
            throw new InvalidValueException("Exception in request");
        }
    }

    private static void checkPositiveId(Long id) {
        if (id <= 0) {
            throw new InvalidValueException("Task ID must be positive");
        }
    }
}

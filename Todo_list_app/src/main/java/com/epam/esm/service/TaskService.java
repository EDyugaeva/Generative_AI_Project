package com.epam.esm.service;

import com.epam.esm.exceptions.TaskNotFoundException;
import com.epam.esm.model.Task;
import com.epam.esm.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        log.info("Creating a new task: {}", task);
        Task createdTask = taskRepository.save(task);
        log.info("Task created successfully: {}", createdTask);
        return createdTask;
    }

    public Task getTaskById(Long taskId) {
        log.info("Retrieving task by id: {}", taskId);
        return taskRepository.findById(taskId).orElseThrow(
                () -> {
                    log.error("Task with id {} not found", taskId);
                    return new TaskNotFoundException(String.format("Task with id %d was not found", taskId));
                });
    }

    public List<Task> getAllTasks() {
        log.info("Retrieving all tasks");
        return taskRepository.findAll();
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        log.info("Updating task with id {}: {}", taskId, updatedTask);
        Task existingTask = getTaskById(taskId);
        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        Task savedTask = taskRepository.save(existingTask);
        log.info("Task updated successfully: {}", savedTask);
        return savedTask;
    }

    public void deleteTask(Long taskId) {
        log.info("Deleting task with id: {}", taskId);
        if (!taskRepository.existsById(taskId)) {
            log.error("Task with id {} not found", taskId);
            throw new TaskNotFoundException(String.format("Task with id %d was not found", taskId));
        }
        taskRepository.deleteById(taskId);
        log.info("Task deleted successfully");
    }
}

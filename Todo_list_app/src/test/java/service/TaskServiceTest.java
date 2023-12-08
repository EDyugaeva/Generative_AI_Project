package service;

import com.epam.esm.exceptions.TaskNotFoundException;
import com.epam.esm.model.Task;
import com.epam.esm.repository.TaskRepository;
import com.epam.esm.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static constants.TaskConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_shouldReturnCreatedTask_whenRepositorySave() {

        when(taskRepository.save(any(Task.class))).thenReturn(TASK_TO_CREATE);

        Task createdTask = taskService.createTask(TASK_TO_CREATE);

        assertNotNull(createdTask);
        assertEquals(TASK_TO_CREATE.getName(), createdTask.getName());
        assertEquals(TASK_TO_CREATE.getDescription(), createdTask.getDescription());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_shouldReturnTask_whenRepositoryFindById() {
        when(taskRepository.findById(TASK_EXPECTED_ID)).thenReturn(Optional.of(TASK_EXPECTED));

        Task retrievedTask = taskService.getTaskById(TASK_EXPECTED_ID);

        assertNotNull(retrievedTask);
        assertEquals(TASK_EXPECTED, retrievedTask);

        verify(taskRepository, times(1)).findById(TASK_EXPECTED_ID);
    }

    @Test
    void getAllTasks_shouldReturnTasksList_whenRepositoryFindAll() {


        when(taskRepository.findAll()).thenReturn(EXPECTED_TASKS);

        List<Task> retrievedTasks = taskService.getAllTasks();

        assertNotNull(retrievedTasks);
        assertEquals(EXPECTED_TASKS.size(), retrievedTasks.size());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void updateTask_shouldReturnUpdatedTask_whenRepositoryFindByIdAndSave() {
        when(taskRepository.findById(TASK_EXPECTED_ID)).thenReturn(Optional.of(TASK_1));
        when(taskRepository.save(any(Task.class))).thenReturn(UPDATED_TASK);

        Task result = taskService.updateTask(TASK_EXPECTED_ID, UPDATED_TASK);

        assertNotNull(result);
        assertEquals(UPDATED_TASK.getName(), result.getName());
        assertEquals(UPDATED_TASK.getDescription(), result.getDescription());

        verify(taskRepository, times(1)).findById(TASK_EXPECTED_ID);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_shouldNotThrowException_whenRepositoryFindById() {
        when(taskRepository.existsById(TASK_EXPECTED_ID)).thenReturn(true);

        assertDoesNotThrow(() -> taskService.deleteTask(TASK_EXPECTED_ID));
        verify(taskRepository, times(1)).existsById(TASK_EXPECTED_ID);
        verify(taskRepository, times(1)).deleteById(TASK_EXPECTED_ID);
    }

    @Test
    void getTaskById_shouldThrowTaskNotFoundException_whenRepositoryFindByIdReturnsEmptyOptional() {
        when(taskRepository.findById(TASK_EXPECTED_ID)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(TASK_EXPECTED_ID));
        verify(taskRepository, times(1)).findById(TASK_EXPECTED_ID);
    }

    @Test
    void updateTask_shouldThrowTaskNotFoundException_whenRepositoryFindByIdReturnsEmptyOptional() {
        when(taskRepository.findById(TASK_EXPECTED_ID)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(TASK_EXPECTED_ID, UPDATED_TASK));
        verify(taskRepository, times(1)).findById(TASK_EXPECTED_ID);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_shouldThrowTaskNotFoundException_whenRepositoryFindByIdReturnsEmptyOptional() {
        when(taskRepository.existsById(TASK_EXPECTED_ID)).thenReturn(false);
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(TASK_EXPECTED_ID));

        verify(taskRepository, times(1)).existsById(TASK_EXPECTED_ID);
        verify(taskRepository, never()).deleteById(anyLong());
        assertEquals("Task with id 1 was not found", exception.getMessage());
    }

    @Test
    void getAllTasks_shouldReturnEmptyList_whenRepositoryFindAllReturnsEmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<Task> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }
}

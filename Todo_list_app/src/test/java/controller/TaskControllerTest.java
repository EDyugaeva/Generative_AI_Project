package controller;

import com.epam.esm.controller.TaskController;
import com.epam.esm.exceptions.InvalidValueException;
import com.epam.esm.exceptions.TaskNotFoundException;
import com.epam.esm.model.Task;
import com.epam.esm.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import static constants.TaskConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void createTask_shouldReturnCreatedTask_whenServiceCreateTask() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(taskService.createTask(any(Task.class))).thenReturn(TASK_TO_CREATE);
        ResponseEntity<Task> responseEntity = taskController.createTask(TASK_TO_CREATE, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(TASK_TO_CREATE, responseEntity.getBody());
        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void getTaskById_shouldReturnTask_whenServiceGetTaskById() {
        when(taskService.getTaskById(TASK_EXPECTED_ID)).thenReturn(TASK_1);
        ResponseEntity<Task> responseEntity = taskController.getTaskById(TASK_EXPECTED_ID);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TASK_1, responseEntity.getBody());
        verify(taskService, times(1)).getTaskById(TASK_EXPECTED_ID);
    }

    @Test
    void getAllTasks_shouldReturnTasksList_whenServiceGetAllTasks() {
        when(taskService.getAllTasks()).thenReturn(EXPECTED_TASKS);
        ResponseEntity<List<Task>> responseEntity = taskController.getAllTasks();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXPECTED_TASKS, responseEntity.getBody());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void updateTask_shouldReturnUpdatedTask_whenServiceUpdateTask() {
        BindingResult bindingResult = mock(BindingResult.class);

        when(taskService.updateTask(eq(TASK_EXPECTED_ID), any(Task.class))).thenReturn(UPDATED_TASK);

        ResponseEntity<Task> responseEntity = taskController.updateTask(TASK_EXPECTED_ID, UPDATED_TASK, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(UPDATED_TASK, responseEntity.getBody());

        verify(taskService, times(1)).updateTask(eq(TASK_EXPECTED_ID), any(Task.class));
    }

    @Test
    void deleteTask_shouldReturnNoContent_whenServiceDeleteTask() {
        assertDoesNotThrow(() -> taskController.deleteTask(TASK_EXPECTED_ID));

        verify(taskService, times(1)).deleteTask(TASK_EXPECTED_ID);
    }

    @Test
    void createTask_shouldThrowInvalidValueException_whenBindingResultHasErrors() {
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(InvalidValueException.class, () -> taskController.createTask(TASK_TO_CREATE, bindingResult));

        verify(taskService, never()).createTask(any(Task.class));
    }

    @Test
    void getTaskById_shouldThrowTaskNotFoundException_whenServiceGetTaskByIdThrowsException() {
        when(taskService.getTaskById(TASK_EXPECTED_ID)).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskController.getTaskById(TASK_EXPECTED_ID));

        verify(taskService, times(1)).getTaskById(TASK_EXPECTED_ID);
    }

    @Test
    void getTaskById_shouldThrowInvalidValueException_whenRequestNegativeId() {
        assertThrows(InvalidValueException.class, () -> taskController.getTaskById(TASK_NEGATIVE_ID));

        verify(taskService, times(0)).getTaskById(TASK_EXPECTED_ID);
    }
}

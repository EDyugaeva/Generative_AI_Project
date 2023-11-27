package constants;

import com.epam.esm.model.Task;

import java.util.Arrays;
import java.util.List;

public class TaskConstants {

    public final static Task TASK_TO_CREATE = new Task("Task 1", "Description 1");
    public final static Task TASK_1 = new Task("Task 1", "Description 1");
    public final static Task TASK_EXPECTED = new Task(1L, "Task 1", "Description 1");
    public final static Long TASK_EXPECTED_ID = 1L;
    public final static Long TASK_NEGATIVE_ID = -1L;
    public final static Task TASK_2 = new Task("Task 2", "Description 2");
    public final static List<Task> EXPECTED_TASKS = Arrays.asList(TASK_1, TASK_2);
    public final static Task UPDATED_TASK = new Task("Updated Task", "Updated Description");
}

package egg.task;

import egg.Storage;

import egg.command.AddCommand;
import egg.command.ByeCommand;
import egg.command.Command;
import egg.command.DeleteCommand;
import egg.command.FindCommand;
import egg.command.ListCommand;
import egg.command.MarkCommand;
import egg.command.UnknownCommand;
import egg.command.UnmarkCommand;

import egg.task.DeadlineTask;
import egg.task.EventTask;
import egg.task.Task;
import egg.task.TaskList;
import egg.task.TodoTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class StorageTest {
    private TaskList tasks;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage("./data/test_tasks.txt");
        tasks = new TaskList();
        
        storage.store(tasks);
    }

    @Test
    public void loadTask_todoTask_success() {
        String taskString = "[T][ ] my todo task ()";
        storage.loadTask(tasks, taskString);

        TodoTask task = (TodoTask) tasks.getTaskAtIndex(1);

        assertEquals("my todo task", task.description);
    }

    @Test
    public void loadTask_deadlineTask_success() {
        String taskString = "[D][ ] my deadline task (by: Fri, 02 Jan 2026) ()";
        storage.loadTask(tasks, taskString);

        DeadlineTask task = (DeadlineTask) tasks.getTaskAtIndex(1);
        assertEquals("my deadline task", task.description);
        assertEquals(LocalDate.parse("2026-01-02"), task.by);
    }

    @Test
    public void loadTask_eventTask_success() {
        String taskString = "[E][ ] my event task (from: Fri, 02 Jan 2026 to: Sun, 07 Jun 2026) ()";
        storage.loadTask(tasks, taskString);

        EventTask task = (EventTask) tasks.getTaskAtIndex(1);

        assertEquals("my event task", task.description);
        assertEquals(LocalDate.parse("2026-01-02"), task.from);
        assertEquals(LocalDate.parse("2026-06-07"), task.to);
    }

    @Test
    public void loadTask_blank_exceptionThrown() {
        String taskString = "";

        try {
            storage.loadTask(tasks, taskString);
        } catch (Exception e) {
            assertEquals("Could not parse as task: " + taskString, e.getMessage());
        }
    }

    @Test
    public void loadTask_invalidTodo_exceptionThrown() {
        String taskString = "[T] anomaly";

        try {
            storage.loadTask(tasks, taskString);
        } catch (Exception e) {
            assertEquals("Could not parse as todo task: " + taskString, e.getMessage());
        }
    }

    @Test
    public void loadTask_invalidDeadline_exceptionThrown() {
        String taskString = "[D][ ] anomaly (by: Fri, 02 Jan 2026) (";

        try {
            storage.loadTask(tasks, taskString);
        } catch (Exception e) {
            assertEquals("Could not parse as deadline task: " + taskString, e.getMessage());
        }
    }
}

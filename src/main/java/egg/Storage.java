package egg;

import egg.task.DeadlineTask;
import egg.task.EventTask;
import egg.task.Task;
import egg.task.TaskList;
import egg.task.TodoTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the loading and storing of tasks to a local file.
 * This class manages file creation, directory verification, and the
 * conversion between Task objects and their string representations for persistence.
 */
public class Storage {
    /** The path to the file where tasks are stored. */
    private Path path;

    private static void createFile(Path path) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            Files.createFile(path);
        } catch (IOException e) {
            System.err.println("Could not create file " + path + "\n" + e.getMessage());
        }
    }

    /**
     * Initializes a Storage object with a specified filename.
     * If the file or its parent directories do not exist, they will be created.
     *
     * @param filename The relative or absolute path to the storage file.
     */
    public Storage(String filename) {
        path = Paths.get(filename);

        if (!Files.exists(path)) {
            createFile(path);
        }
    }

    /**
     * Parses a single line from the storage file and adds the corresponding
     * Task to the TaskList.
     *
     * @param tasks      The TaskList to populate.
     * @param taskString The raw string data representing a task.
     * @throws RuntimeException If the task type prefix is unrecognized.
     */
    public void loadTask(TaskList tasks, String taskString) {
        if (taskString.startsWith("[T]")) {
            tasks.addTask(TodoTask.fromString(taskString));
        } else if (taskString.startsWith("[D]")) {
            tasks.addTask(DeadlineTask.fromString(taskString));
        } else if (taskString.startsWith("[E]")) {
            tasks.addTask(EventTask.fromString(taskString));
        } else {
            throw new RuntimeException("Could not parse as task: " + taskString);
        }
    }

    /**
     * Reads all tasks from the storage file.
     *
     * @return A {@link TaskList} containing all tasks loaded from the file.
     */
    public TaskList load() {
        TaskList tasks = new TaskList();

        try {
            Stream<String> lines = Files.lines(path);

            lines.forEach(line -> loadTask(tasks, line));
        } catch (IOException e) {
            System.err.println("Could not open file: " + path);
        }

        return tasks;
    }

    /**
     * Writes the current list of tasks to the storage file.
     * Each task is converted to its string representation before saving.
     *
     * @param taskList The list of tasks to be saved.
     */
    public void store(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();

        try {
            List<String> taskStrings = tasks
                .stream()
                .map(Task::toString)
                .collect(Collectors.toList());

            Files.write(path, taskStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

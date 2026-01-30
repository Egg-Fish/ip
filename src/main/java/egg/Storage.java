package egg;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

import java.io.IOException;

public class Storage {
    private Path path;

    public Storage(String filename) {
        path = Paths.get(filename);

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public void loadTask(TaskList tasks, String taskString) {
        if (taskString.startsWith("[T]")) {
            tasks.addTask(TodoTask.fromString(taskString));
        }
        else if (taskString.startsWith("[D]")) {
            tasks.addTask(DeadlineTask.fromString(taskString));
        }
        else if (taskString.startsWith("[E]")) {
            tasks.addTask(EventTask.fromString(taskString));
        }
        else {
            throw new RuntimeException("Could not parse as task: " + taskString);
        }
    }

    public TaskList load() {
        TaskList tasks = new TaskList();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                    loadTask(tasks, line);
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

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

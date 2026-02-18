package egg.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages an in-memory list of tasks.
 * Provides methods to add, delete, and retrieve tasks, serving as a
 * wrapper around an {@link ArrayList} of {@link Task} objects.
 */
public class TaskList {
    /** The internal list used to store tasks. */
    private ArrayList<Task> tasks;

    /**
     * Initializes an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves the underlying list of tasks.
     *
     * @return A {@link List} containing all tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Retrieves a task based on its 1-based index.
     * @param index The 1-based index of the task (e.g., 1 for the first task).
     * @return The {@link Task} at the specified position.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task getTaskAtIndex(int index) {
        return tasks.get(index - 1);
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The {@link Task} to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a specific task from the list.
     *
     * @param task The {@link Task} object to be removed.
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public TaskList filterByKeyword(String keyword) {
        TaskList filteredTasks = new TaskList();

        for (Task t : this.getTasks()) {
            if (t.getDescription().contains(keyword)) {
                filteredTasks.addTask(t);
            }
        }

        return filteredTasks;
    }
}

package egg;

import egg.task.Task;
import egg.task.TaskList;

/**
 * Represents the user interface of the Egg application.
 * This abstract class defines the contract for displaying messages,
 * task updates, and lists to the user.
 */
public abstract class Ui {

    /**
     * Displays a generic message to the user.
     * @param message The string message to be printed.
     */
    public abstract void printMessage(String message);

    /**
     * Displays a confirmation message when a task is successfully added.
     * @param tasks The current list of tasks to show updated count or context.
     * @param addedTask The specific task that was added to the list.
     */
    public abstract void printTaskAddedMessage(TaskList tasks, Task addedTask);

    /**
     * Displays a confirmation message when a task is removed.
     * @param tasks The current list of tasks to show updated count or context.
     * @param deletedTask The specific task that was removed from the list.
     */
    public abstract void printTaskDeletedMessage(TaskList tasks, Task deletedTask);

    /**
     * Displays the current list of tasks to the user, preceded by a header message.
     * @param tasks The TaskList containing the tasks to be displayed.
     * @param message A header or descriptive message to print before the list.
     */
    public abstract void printTaskList(TaskList tasks, String message);
}

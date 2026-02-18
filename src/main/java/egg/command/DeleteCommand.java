package egg.command;

import egg.Storage;
import egg.task.Task;
import egg.task.TaskList;
import egg.Ui;

/**
 * Represents a command to remove a task from the task list.
 */
public class DeleteCommand extends Command {
    /** The 1-based index of the task to be deleted. */
    private int index;

    /**
     * Initializes a DeleteCommand with the specified task index.
     *
     * @param index The 1-based index provided by the user.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This command attempts to retrieve and remove the task at the given index.
     * If the index is invalid, an error message is displayed to the user.
     * Changes are persisted to storage upon successful deletion.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.getTaskAtIndex(index);
            tasks.deleteTask(task);
            ui.printTaskDeletedMessage(tasks, task);
            storage.store(tasks);
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Index " + index + " out of bounds");
        }
    }
}

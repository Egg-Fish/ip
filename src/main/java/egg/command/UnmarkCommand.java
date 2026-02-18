package egg.command;

import egg.Storage;
import egg.task.Task;
import egg.task.TaskList;
import egg.Ui;

/**
 * Represents a command to mark a specific task as not completed.
 */
public class UnmarkCommand extends Command {
    /** The 1-based index of the task to be unmarked. */
    private int index;

    /**
     * Initializes an UnmarkCommand with the specified task index.
     *
     * @param index The 1-based index provided by the user.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This command retrieves the task at the specified index,
     * calls its unmark method, notifies the user, and persists
     * the change to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.getTaskAtIndex(index);
            task.unmark();
            ui.printMessage("OK, I've marked this task as not done yet:\n  " + task);
            storage.store(tasks);
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Index " + index + " out of bounds");
        }
    }
}

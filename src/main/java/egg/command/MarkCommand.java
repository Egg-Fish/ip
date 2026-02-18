package egg.command;

import egg.Storage;
import egg.Task;
import egg.TaskList;
import egg.Ui;

/**
 * Represents a command to mark a specific task as completed.
 */
public class MarkCommand extends Command {
    /** The 1-based index of the task to be marked as done. */
    private int index;

    /**
     * Initializes a MarkCommand with the specified task index.
     *
     * @param index The 1-based index provided by the user.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This command retrieves the task at the specified index,
     * calls its mark method, notifies the user of the success,
     * and persists the change to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.getTaskAtIndex(index);
            task.mark();
            ui.printMessage("Nice! I've marked this task as done:\n  " + task);
            storage.store(tasks);
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Index " + index + " out of bounds");
        }
    }
}

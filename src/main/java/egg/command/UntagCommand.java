package egg.command;

import egg.Storage;
import egg.task.Task;
import egg.task.TaskList;
import egg.Ui;

/**
 * Represents a command to remove a tag from a specific task.
 */
public class UntagCommand extends Command {
    /** The 1-based index of the task to be untagged. */
    private int index;

    /** The tag to remove from the task. */
    private String tag;

    /**
     * Initializes an UntagCommand with the specified task index
     * and tag.
     *
     * @param index The 1-based index provided by the user.
     * @param tag The tag to be removed.
     */
    public UntagCommand(int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This command retrieves the task at the specified index,
     * removes the tag, notifies the user of the success, and
     * persists the change to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.getTaskAtIndex(index);
            task.untag(tag);
            ui.printMessage("Noted! I've removed the tag (#" + tag + ") from this task:\n  " + task);
            storage.store(tasks);
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Index " + index + " out of bounds");
        }
    }
}

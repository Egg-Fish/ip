package egg.command;

import egg.Storage;
import egg.task.Task;
import egg.task.TaskList;
import egg.Ui;

/**
 * Represents a command to add a tag to a specific task.
 */
public class TagCommand extends Command {
    /** The 1-based index of the task to be tagged. */
    private int index;

    /** The tag to add to the task. */
    private String tag;

    /**
     * Initializes a TagCommand with the specified task index
     * and tag.
     *
     * @param index The 1-based index provided by the user.
     * @param tag The tag to be added.
     */
    public TagCommand(int index, String tag) {
        this.index = index;
        this.tag = tag;
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
            task.tag(tag);
            ui.printMessage("Nice! I've tagged this task with (#" + tag + "):\n  " + task);
            storage.store(tasks);
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Index " + index + " out of bounds");
        }
    }
}

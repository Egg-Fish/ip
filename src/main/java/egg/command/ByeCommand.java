package egg.command;

import egg.Storage;
import egg.task.TaskList;
import egg.Ui;

/**
 * Represents a command to terminate the application.
 */
public class ByeCommand extends Command {

    /**
     * {@inheritDoc}
     * <p>
     * This command displays a goodbye message to the user.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printMessage("Bye. Hope to see you again soon!");
    }
}

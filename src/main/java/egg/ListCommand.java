package egg;

/**
 * Represents a command to display all tasks currently in the task list.
 */
public class ListCommand extends Command {

    /**
     * {@inheritDoc}
     * <p>
     * This command triggers displays the full list of tasks.
     * It does not modify any data or interact with storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printTaskList(tasks, "Here are the tasks in your list:");
    }
}

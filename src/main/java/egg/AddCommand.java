package egg;

/**
 * Represents a command to add a new task to the task list.
 */
public class AddCommand extends Command {
    /** The task to be added to the list. */
    private Task task;
    
    /**
     * Initializes an AddCommand with the specified task.
     *
     * @param task The {@link Task} object to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This command adds the task to the list, displays a
     * confirmation message, and stores the updated list to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.addTask(task);
        ui.printTaskAddedMessage(tasks, task);
        storage.store(tasks);
    }
}

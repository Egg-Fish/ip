package egg;

/**
 * Represents an executable command in the application.
 * This is an abstract base class for all specific command implementations,
 * such as adding, deleting, or marking tasks.
 */
public abstract class Command {

    /**
     * Executes the specific command.
     * @param tasks   The {@link TaskList} instance.
     * @param ui      The {@link Ui} instance.
     * @param storage The {@link Storage} instance.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
}

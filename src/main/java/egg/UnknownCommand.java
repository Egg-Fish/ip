package egg;

/**
 * Represents an invalid or unrecognized command.
 * This class is used as a fallback when the parser cannot match
 * user input to a known command type.
 */
public class UnknownCommand extends Command {
    /** The raw input string that could not be recognized. */
    private String commandString;

    /**
     * Initializes an UnknownCommand with the offending input string.
     *
     * @param commandString The unrecognized command string provided by the user.
     */
    public UnknownCommand(String commandString) {
        this.commandString = commandString;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This command displays an error message to the user indicating
     * that the specific command string was not recognized.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printMessage("Unknown command: " + commandString);
    }
}

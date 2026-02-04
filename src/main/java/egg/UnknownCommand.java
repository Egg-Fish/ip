package egg;


public class UnknownCommand extends Command {
    private String commandString;

    public UnknownCommand(String commandString) {
        this.commandString = commandString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printMessage("Unknown command: " + commandString);
    }
}

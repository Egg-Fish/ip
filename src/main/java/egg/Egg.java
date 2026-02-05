package egg;

import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Egg extends Application {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    public Egg() {
        ui = new TextUi();
        storage = new Storage("./data/tasks.txt");
        taskList = storage.load();
    }

    public Egg(Stage stage) {
        ui = new GraphicUi(stage, this);
        storage = new Storage("./data/tasks.txt");
        taskList = storage.load();
    }

    @Override
    public void start(Stage stage) {
        new Egg(stage).run();
    }

    public void runCommand(String commandString) {
        try {
            Command command = Parser.parseCommand(commandString);

            command.execute(taskList, ui, storage);
        } catch (RuntimeException e) {
            ui.printMessage(e.getMessage());
        }
    }

    public void run() {
        if (true) {
            return;
        }
        
        ui.printMessage("Hello! I'm Egg \nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String commandString = scanner.nextLine().trim();

            runCommand(commandString);

            if (commandString.equals("bye")) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        new Egg().run();
    }
}

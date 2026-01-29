package egg;

import java.util.Scanner;

public class Egg {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    
    public Egg() {
        ui = new Ui();
        storage = new Storage("./data/tasks.txt");
        taskList = storage.load();
    }

    public void run() {
        ui.printMessage("Hello! I'm Egg \nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String commandString = scanner.nextLine().trim();

            try {
                Command command = Parser.parseCommand(commandString);

                command.execute(taskList, ui, storage);
            } catch (RuntimeException e){
                ui.printMessage(e.getMessage());
            }
            
            if (commandString.equals("bye")) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        new Egg().run();
    }
}

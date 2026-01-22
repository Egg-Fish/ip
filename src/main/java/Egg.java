import java.util.Scanner;
import java.util.ArrayList;

public class Egg {
    private static void print(String s, int indent){
        System.out.println(" ".repeat(indent) + s);
    }

     private static void print(String s){
         print(s, 4);
    }

    private static void printMessage(String message) {
        String hl = "------------------------------------------";
        
        print(hl);
        
        for (String m : message.split("\n")) {
            print(m);
        }
        
        print(hl);
    }
    
    public static void main(String[] args) {
        printMessage("Hello! I'm Egg \nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        
        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                break;
            }

            else if (command.equals("list")) {
                String s = "";
                
                for (int i = 0; i < tasks.size(); i++) {
                    s += (i + 1) + "." + tasks.get(i) + "\n";
                }

                printMessage(s);
            }

            else if (command.startsWith("mark ")) {
                int index = Integer.parseInt(command.split(" ")[1]);

                if (index < 1 || index > tasks.size()) {
                    printMessage("invalid task number");
                    continue;
                }

                Task task = tasks.get(index - 1);

                task.mark();

                printMessage("Nice! I've marked this task as done:\n  " + task);
            }

            else if (command.startsWith("unmark ")) {
                int index = Integer.parseInt(command.split(" ")[1]);

                if (index < 1 || index > tasks.size()) {
                    printMessage("invalid task number");
                    continue;
                }

                Task task = tasks.get(index - 1);

                task.unmark();

                printMessage("OK, I've marked this task as not done yet:\n  " + task);
            }
            
            else {
                String description = command;
                
                printMessage("added task: " + description);

                tasks.add(new Task(description));
            }
        }

        printMessage("Bye. Hope to see you again soon!");
    }
}

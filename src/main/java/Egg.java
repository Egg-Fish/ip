import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Egg {
    private static void print(String s, int indent){
        System.out.println(" ".repeat(indent) + s);
    }

     private static void print(String s){
         print(s, 4);
    }

    private static void printMessage(String message) {
        String hl = "---------------------------------------------";
        
        print(hl);
        
        for (String m : message.split("\n")) {
            print(m);
        }
        
        print(hl);
    }

    public static ArrayList<Task> tasks = new ArrayList<>();

    public static void addTask(Task task) {
        tasks.add(task);

        String l1 = "Got it. I've added this task:\n";
        String l2 = "  " + task + "\n";
        String l3 = "Now you have " + tasks.size() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    public static void deleteTask(Task task) {
        tasks.remove(task);

        String l1 = "Noted. I've removed this task:\n";
        String l2 = "  " + task + "\n";
        String l3 = "Now you have " + tasks.size() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    public static void listTasks() {
        String s = "Here are the tasks in your list:\n";
                
        for (int i = 0; i < tasks.size(); i++) {
            s += (i + 1) + "." + tasks.get(i) + "\n";
        }

        printMessage(s);
    }

    static Pattern markPattern = Pattern.compile("^mark\\s+(\\d+)$");
    static Pattern unmarkPattern = Pattern.compile("^unmark\\s+(\\d+)$");
    static Pattern todoPattern = Pattern.compile("^todo\\s+([^\\n]+?)\\s*$");
    static Pattern deadlinePattern = Pattern.compile("^deadline\\s+([^\\n]+?)\\s+/by\\s+([^\\n]+?)\\s*$");
    static Pattern eventPattern = Pattern.compile("^event\\s+([^\\n]+?)\\s+/from\\s+([^\\n]+?)\\s+/to\\s+([^\\n]+?)\\s*$");
    static Pattern deletePattern = Pattern.compile("^delete\\s+(\\d+)$");
    
    public static void main(String[] args) {
        printMessage("Hello! I'm Egg \nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);

        Matcher matcher;
        
        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                break;
            }

            if (command.equals("list")) {
                listTasks();

                continue;
            }

            matcher = deletePattern.matcher(command);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));

                if (index < 1 || index > tasks.size()) {
                    printMessage("invalid task number " + index);
                    continue;
                }

                Task task = tasks.get(index - 1);

                deleteTask(task);
                
                continue;
            }

            matcher = markPattern.matcher(command);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));

                if (index < 1 || index > tasks.size()) {
                    printMessage("invalid task number " + index);
                    continue;
                }

                Task task = tasks.get(index - 1);

                task.mark();

                printMessage("Nice! I've marked this task as done:\n  " + task);
                
                continue;
            }

            matcher = unmarkPattern.matcher(command);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));

                if (index < 1 || index > tasks.size()) {
                    printMessage("invalid task number " + index);
                    continue;
                }

                Task task = tasks.get(index - 1);

                task.unmark();

                printMessage("OK, I've marked this task as not done yet:\n  " + task);
                continue;
            }
            
            matcher = todoPattern.matcher(command);
            if (matcher.matches()) {
                String description = matcher.group(1);
                
                addTask(new TodoTask(description));

                continue;
            } else if (command.startsWith("todo")) {
                printMessage("please write a description of your todo task!");

                continue;
            }

            matcher = deadlinePattern.matcher(command);
            if (matcher.matches()) {
                String description = matcher.group(1);
                String by = matcher.group(2);
                
                addTask(new DeadlineTask(description, by));

                continue;
            } else if (command.startsWith("deadline") && !command.contains("/by")) {
                printMessage("please add a deadline using /by");

                continue;
            }

            matcher = eventPattern.matcher(command);
            if (matcher.matches()) {
                String description = matcher.group(1);
                String from = matcher.group(2);
                String to = matcher.group(3);
                
                addTask(new EventTask(description, from, to));

                continue;
            } else if (command.startsWith("event") && (!command.contains("/from") || !command.contains("/to"))) {
                printMessage("please include both /from and /to");

                continue;
            }

            printMessage("I don't understand, could you say that again?");
        }

        printMessage("Bye. Hope to see you again soon!");
    }
}

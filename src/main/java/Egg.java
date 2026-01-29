import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;


public class Egg {
    private Ui ui;
    private TaskList taskList;

    public Egg() {
        ui = new Ui();
        taskList = new TaskList();
    }

    public void addTask(Task task) {
        taskList.addTask(task);

        ui.printTaskAddedMessage(taskList, task);
    }

    public void deleteTask(Task task) {
        taskList.deleteTask(task);

        ui.printTaskDeletedMessage(taskList, task);

    }

    public void listTasks() {
        String s = "Here are the tasks in your list:\n";

        List<Task> tasks = taskList.getTasks();
                
        for (int i = 0; i < tasks.size(); i++) {
            s += (i + 1) + "." + tasks.get(i) + "\n";
        }

        ui.printMessage(s);
    }

    public static String tasksFilename = "./data/tasks.txt";

    public void loadTask(String taskString) {
        if (taskString.startsWith("[T]")) {
            taskList.addTask(TodoTask.fromString(taskString));

            return;
        }

        if (taskString.startsWith("[D]")) {
            taskList.addTask(DeadlineTask.fromString(taskString));

            return;
        }

        if (taskString.startsWith("[E]")) {
            taskList.addTask(EventTask.fromString(taskString));

            return;
        }

        else {
            throw new RuntimeException("Could not parse as task: " + taskString);
        }
    }

    public void loadTasks() {
        new File("./data").mkdirs();

        try (BufferedReader reader = new BufferedReader(new FileReader(tasksFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadTask(line);
            }
        } catch (IOException e) {
            return;
        }
    }

    public void storeTasks() {
        new File("./data").mkdirs();

        List<Task> tasks = taskList.getTasks();

        try {
            FileWriter writer = new FileWriter(tasksFilename);
            
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Could not open file ./data/tasks.txt");
            return;
        }
    }

    
    
    public void run() {
        Pattern markPattern = Pattern.compile("^mark\\s+(\\d+)$");
        Pattern unmarkPattern = Pattern.compile("^unmark\\s+(\\d+)$");
        Pattern todoPattern = Pattern.compile("^todo\\s+([^\\n]+?)\\s*$");
        Pattern deadlinePattern = Pattern.compile("^deadline\\s+([^\\n]+?)\\s+/by\\s+([^\\n]+?)\\s*$");
        Pattern eventPattern = Pattern.compile("^event\\s+([^\\n]+?)\\s+/from\\s+([^\\n]+?)\\s+/to\\s+([^\\n]+?)\\s*$");
        Pattern deletePattern = Pattern.compile("^delete\\s+(\\d+)$");
 
        try {
            loadTasks();
        } catch (Exception e) {
            ui.printMessage("Error: Could not load tasks.");
            return;
        }

        ui.printMessage("Hello! I'm Egg \nWhat can I do for you?");
   
        Scanner scanner = new Scanner(System.in);
        Matcher matcher;
        
        while (true) {
            storeTasks();
        
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

                if (index < 1 || index > taskList.getSize()) {
                    ui.printMessage("invalid task number " + index);
                    continue;
                }

                Task task = taskList.getTaskAtIndex(index);

                deleteTask(task);
                
                continue;
            }

            matcher = markPattern.matcher(command);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));

                if (index < 1 || index > taskList.getSize()) {
                    ui.printMessage("invalid task number " + index);
                    continue;
                }

                Task task = taskList.getTaskAtIndex(index);

                task.mark();

                ui.printMessage("Nice! I've marked this task as done:\n  " + task);
                
                continue;
            }

            matcher = unmarkPattern.matcher(command);
            if (matcher.matches()) {
                int index = Integer.parseInt(matcher.group(1));

                if (index < 1 || index > taskList.getSize()) {
                    ui.printMessage("invalid task number " + index);
                    continue;
                }

                Task task = taskList.getTaskAtIndex(index);

                task.unmark();

                ui.printMessage("OK, I've marked this task as not done yet:\n  " + task);
                continue;
            }
            
            matcher = todoPattern.matcher(command);
            if (matcher.matches()) {
                String description = matcher.group(1);
                
                addTask(new TodoTask(description));

                continue;
            } else if (command.startsWith("todo")) {
                ui.printMessage("please write a description of your todo task!");

                continue;
            }

            matcher = deadlinePattern.matcher(command);
            if (matcher.matches()) {
                String description = matcher.group(1);
                String byString = matcher.group(2);

                try {
                    LocalDate by = LocalDate.parse(byString);
                    addTask(new DeadlineTask(description, by));
                } catch (Exception e) {
                    ui.printMessage("could not parse as date: " + byString);
                }
                
                continue;
            } else if (command.startsWith("deadline") && !command.contains("/by")) {
                ui.printMessage("please add a deadline using /by");

                continue;
            }

            matcher = eventPattern.matcher(command);
            if (matcher.matches()) {
                String description = matcher.group(1);
                String fromString = matcher.group(2);
                String toString = matcher.group(3);
                
                LocalDate from;
                LocalDate to;

                try {
                    from = LocalDate.parse(fromString);
                } catch (Exception e) {
                    ui.printMessage("could not parse as date: " + fromString);
                    continue;
                }

                try {
                    to = LocalDate.parse(toString);
                } catch (Exception e) {
                    ui.printMessage("could not parse as date: " + toString);
                    continue;
                }

                if (from.isAfter(to)) {
                    ui.printMessage("start date cannot be after end date");
                    continue;
                }

                addTask(new EventTask(description, from, to));

                continue;
            } else if (command.startsWith("event") && (!command.contains("/from") || !command.contains("/to"))) {
                ui.printMessage("please include both /from and /to");

                continue;
            }

            ui.printMessage("I don't understand, could you say that again?");
        }

        ui.printMessage("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        new Egg().run();
    }
}

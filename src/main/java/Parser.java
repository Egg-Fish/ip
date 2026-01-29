import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.LocalDate;

public class Parser {
    public static Command parseTodoCommand(String commandString) {
        Pattern pattern = Pattern.compile("^todo\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);
        
        if (matcher.matches()) {
            String description = matcher.group(1);
                
            Task newTask = new TodoTask(description);
            Command command = new AddCommand(newTask);
            
            return command;
        } else {
            throw new RuntimeException("Could not parse as todo command: " + commandString);
        }
    }

    public static Command parseDeadlineCommand(String commandString) {
        Pattern pattern = Pattern.compile("^deadline\\s+([^\\n]+?)\\s+/by\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);
        
        if (matcher.matches()) {
            String description = matcher.group(1);
            String byString = matcher.group(2);

            try {
                LocalDate by = LocalDate.parse(byString);
                    
                Task newTask = new DeadlineTask(description, by);
                Command command = new AddCommand(newTask);
            
                return command;
            } catch (Exception e) {
                throw new RuntimeException("Could not parse as date: " + byString);
            }
        } else {
            throw new RuntimeException("Could not parse as deadline command: " + commandString);
        }
    }
    
    public static Command parseCommand(String commandString) {
        String first = commandString.trim().split(" ", 2)[0];

        switch (first) {
        case "todo":
            return parseTodoCommand(commandString);
        case "deadline":
            return parseDeadlineCommand(commandString);
        case "bye":
            return new ByeCommand();
        default:
            return new UnknownCommand(commandString);
        }
    }
} 

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

    public static Command parseEventCommand(String commandString) {
        Pattern pattern = Pattern.compile("^event\\s+([^\\n]+?)\\s+/from\\s+([^\\n]+?)\\s+/to\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);
        
        if (matcher.matches()) {
            String description = matcher.group(1);
            String fromString = matcher.group(2);
            String toString = matcher.group(3);
                
            LocalDate from;
            LocalDate to;

            try {
                from = LocalDate.parse(fromString);
            } catch (Exception e) {
                throw new RuntimeException("Could not parse as date: " + fromString);
            }

            try {
                to = LocalDate.parse(toString);
            } catch (Exception e) {
                throw new RuntimeException("Could not parse as date: " + toString);
            }

            if (from.isAfter(to)) {
                throw new RuntimeException("Start date cannot be after end date");
            }

            Task newTask = new EventTask(description, from, to);
            Command command = new AddCommand(newTask);

            return command;
        } else {
            throw new RuntimeException("Could not parse as event command: " + commandString);
        }
    }

    public static Command parseMarkCommand(String commandString) {
        Pattern pattern = Pattern.compile("^mark\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(commandString);
        
        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
                
            Command command = new MarkCommand(index);
            
            return command;
        } else {
            throw new RuntimeException("Could not parse as mark command: " + commandString);
        }
    }

    public static Command parseUnmarkCommand(String commandString) {
        Pattern pattern = Pattern.compile("^unmark\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(commandString);
        
        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
                
            Command command = new UnmarkCommand(index);
            
            return command;
        } else {
            throw new RuntimeException("Could not parse as unmark command: " + commandString);
        }
    }

    public static Command parseDeleteCommand(String commandString) {
        Pattern pattern = Pattern.compile("^delete\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(commandString);
        
        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
                
            Command command = new DeleteCommand(index);
            
            return command;
        } else {
            throw new RuntimeException("Could not parse as delete command: " + commandString);
        }
    }
    
    public static Command parseCommand(String commandString) {
        String first = commandString.trim().split(" ", 2)[0];

        switch (first) {
        case "todo":
            return parseTodoCommand(commandString);
        case "deadline":
            return parseDeadlineCommand(commandString);
        case "event":
            return parseEventCommand(commandString);
        case "mark":
            return parseMarkCommand(commandString);
        case "unmark":
            return parseUnmarkCommand(commandString);
        case "delete":
            return parseDeleteCommand(commandString);
        case "list":
            return new ListCommand();
        case "bye":
            return new ByeCommand();
        default:
            return new UnknownCommand(commandString);
        }
    }
} 

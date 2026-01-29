import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
    
    public static Command parseCommand(String commandString) {
        String first = commandString.trim().split(" ", 2)[0];

        switch (first) {
        case "todo":
            return parseTodoCommand(commandString);
        case "bye":
            return new ByeCommand();
        default:
            return new UnknownCommand(commandString);
        }
    }
} 

package egg;

import egg.command.AddCommand;
import egg.command.ByeCommand;
import egg.command.Command;
import egg.command.DeleteCommand;
import egg.command.FindCommand;
import egg.command.ListCommand;
import egg.command.MarkCommand;
import egg.command.TagCommand;
import egg.command.UnknownCommand;
import egg.command.UnmarkCommand;

import egg.task.DeadlineTask;
import egg.task.EventTask;
import egg.task.Task;
import egg.task.TaskList;
import egg.task.TodoTask;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static Command parseTodoCommand(String commandString) {
        assert commandString != null;

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

    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            String message = "Could not parse as date: " + dateString + "\n";
            message += "Please enter date in the format yyyy-MM-dd. (e.g., 2026-12-31)";

            throw new RuntimeException(message);
        }
    }

    public static Command parseDeadlineCommand(String commandString) {
        assert commandString != null;

        Pattern pattern = Pattern.compile("^deadline\\s+([^\\n]+?)\\s+/by\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            String description = matcher.group(1);
            String byString = matcher.group(2);

            LocalDate by = parseDate(byString);

            Task newTask = new DeadlineTask(description, by);
            Command command = new AddCommand(newTask);

            return command;
        } else {
            throw new RuntimeException("Could not parse as deadline command: " + commandString);
        }
    }

    public static Command parseEventCommand(String commandString) {
        assert commandString != null;

        Pattern pattern = Pattern.compile("^event\\s+([^\\n]+?)\\s+/from\\s+([^\\n]+?)\\s+/to\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            String description = matcher.group(1);
            String fromString = matcher.group(2);
            String toString = matcher.group(3);

            LocalDate from = parseDate(fromString);
            LocalDate to = parseDate(toString);

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
        assert commandString != null;

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
        assert commandString != null;

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
        assert commandString != null;

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

    public static Command parseFindCommand(String commandString) {
        assert commandString != null;

        Pattern pattern = Pattern.compile("^find\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            String keyword = matcher.group(1);

            Command command = new FindCommand(keyword);

            return command;
        } else {
            throw new RuntimeException("Could not parse as find command: " + commandString);
        }
    }

    public static Command parseTagCommand(String commandString) {
        assert commandString != null;

        Pattern pattern = Pattern.compile("^tag\\s+(\\d+)\\s+(.+)$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
            String tag = matcher.group(2);

            if (tag.contains(" ")) {
                throw new RuntimeException("Tag cannot contain spaces: " + tag);
            }

            Command command = new TagCommand(index, tag);

            return command;
        } else {
            throw new RuntimeException("Could not parse as tag command: " + commandString);
        }
    }

    public static Command parseCommand(String commandString) {
        assert commandString != null;

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
        case "find":
            return parseFindCommand(commandString);
        case "tag":
            return parseTagCommand(commandString);
        case "list":
            return new ListCommand();
        case "bye":
            return new ByeCommand();
        default:
            return new UnknownCommand(commandString);
        }
    }
}

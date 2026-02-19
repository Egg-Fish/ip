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
import egg.command.UntagCommand;

import egg.task.DeadlineTask;
import egg.task.EventTask;
import egg.task.Task;
import egg.task.TaskList;
import egg.task.TodoTask;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the interpretation of user input strings.
 * The Parser class extracts commands, task details, and indices from raw text
 * and converts them into executable {@code Command} objects.
 */
public class Parser {

    /**
     * Parses a "todo" command string.
     * * @param commandString The raw input from the user.
     * @return An {@link AddCommand} containing the new {@link TodoTask}.
     * @throws RuntimeException If the input does not match the required format.
     */
    public static Command parseTodoCommand(String commandString) {
        assert commandString != null;

        Pattern pattern = Pattern.compile("^todo\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            String description = matcher.group(1);
            Task newTask = new TodoTask(description);
            return new AddCommand(newTask);
        } else {
            throw new RuntimeException("Could not parse as todo command: " + commandString);
        }
    }

    /**
     * Converts a string representation of a date into a {@link LocalDate} object.
     * * @param dateString The date string in yyyy-MM-dd format.
     * @return The parsed {@link LocalDate}.
     * @throws RuntimeException If the date format is invalid.
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            String message = "Could not parse as date: " + dateString + "\n"
                    + "Please enter date in the format yyyy-MM-dd. (e.g., 2026-12-31)";
            throw new RuntimeException(message);
        }
    }

    /**
     * Parses a "deadline" command string including a description and a due date.
     * * @param commandString Raw input expected in format: deadline [desc] /by [date].
     * @return An {@link AddCommand} containing the new {@link DeadlineTask}.
     * @throws RuntimeException If parsing fails or the date format is incorrect.
     */
    public static Command parseDeadlineCommand(String commandString) {
        assert commandString != null;

        Pattern pattern = Pattern.compile("^deadline\\s+([^\\n]+?)\\s+/by\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            String description = matcher.group(1);
            String byString = matcher.group(2);
            LocalDate by = parseDate(byString);

            Task newTask = new DeadlineTask(description, by);
            return new AddCommand(newTask);
        } else {
            throw new RuntimeException("Could not parse as deadline command: " + commandString);
        }
    }

    /**
     * Parses an "event" command string including start and end dates.
     * * @param commandString Raw input expected in format: event [desc] /from [date] /to [date].
     * @return An {@link AddCommand} containing the new {@link EventTask}.
     * @throws RuntimeException If parsing fails or the start date is after the end date.
     */
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
            return new AddCommand(newTask);
        } else {
            throw new RuntimeException("Could not parse as event command: " + commandString);
        }
    }

    /**
     * Parses a "mark" command to identify the task index to be completed.
     * * @param commandString Raw input (e.g., "mark 1").
     * @return A {@link MarkCommand} for the specified index.
     */
    public static Command parseMarkCommand(String commandString) {
        assert commandString != null;
        Pattern pattern = Pattern.compile("^mark\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
            return new MarkCommand(index);
        } else {
            throw new RuntimeException("Could not parse as mark command: " + commandString);
        }
    }

    /**
     * Parses an "unmark" command to revert a task's completed status.
     * * @param commandString Raw input (e.g., "unmark 1").
     * @return An {@link UnmarkCommand} for the specified index.
     */
    public static Command parseUnmarkCommand(String commandString) {
        assert commandString != null;
        Pattern pattern = Pattern.compile("^unmark\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
            return new UnmarkCommand(index);
        } else {
            throw new RuntimeException("Could not parse as unmark command: " + commandString);
        }
    }

    /**
     * Parses a "delete" command to remove a task from the list.
     * * @param commandString Raw input (e.g., "delete 1").
     * @return A {@link DeleteCommand} for the specified index.
     */
    public static Command parseDeleteCommand(String commandString) {
        assert commandString != null;
        Pattern pattern = Pattern.compile("^delete\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
            return new DeleteCommand(index);
        } else {
            throw new RuntimeException("Could not parse as delete command: " + commandString);
        }
    }

    /**
     * Parses a "find" command to search for tasks by keyword.
     * * @param commandString Raw input (e.g., "find book").
     * @return A {@link FindCommand} with the target keyword.
     */
    public static Command parseFindCommand(String commandString) {
        assert commandString != null;
        Pattern pattern = Pattern.compile("^find\\s+([^\\n]+?)\\s*$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            String keyword = matcher.group(1);
            return new FindCommand(keyword);
        } else {
            throw new RuntimeException("Could not parse as find command: " + commandString);
        }
    }

    /**
     * Parses a "tag" command to add a label to a task.
     * * @param commandString Raw input (e.g., "tag 1 urgent").
     * @return A {@link TagCommand} for the specified task and tag.
     * @throws RuntimeException If the tag contains spaces.
     */
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
            return new TagCommand(index, tag);
        } else {
            throw new RuntimeException("Could not parse as tag command: " + commandString);
        }
    }

    /**
     * Parses an "untag" command to remove a label from a task.
     * * @param commandString Raw input (e.g., "untag 1 urgent").
     * @return An {@link UntagCommand} for the specified task and tag.
     */
    public static Command parseUntagCommand(String commandString) {
        assert commandString != null;
        Pattern pattern = Pattern.compile("^untag\\s+(\\d+)\\s+(.+)$");
        Matcher matcher = pattern.matcher(commandString);

        if (matcher.matches()) {
            int index = Integer.parseInt(matcher.group(1));
            String tag = matcher.group(2);

            if (tag.contains(" ")) {
                throw new RuntimeException("Tag cannot contain spaces: " + tag);
            }
            return new UntagCommand(index, tag);
        } else {
            throw new RuntimeException("Could not parse as untag command: " + commandString);
        }
    }

    /**
     * The main entry point for parsing. Determines the command type and
     * routes the input to the appropriate sub-parser.
     * * @param commandString The full raw input from the user.
     * @return The specific {@link Command} to be executed.
     */
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
        case "untag":
            return parseUntagCommand(commandString);
        case "list":
            return new ListCommand();
        case "bye":
            return new ByeCommand();
        default:
            return new UnknownCommand(commandString);
        }
    }
}

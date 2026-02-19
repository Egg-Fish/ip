package egg;

import egg.task.Task;
import egg.task.TaskList;

/**
 * Implements a text-based user interface for the Egg application.
 * This class handles formatting messages with horizontal separators and
 * consistent indentation for terminal output.
 */
public class TextUi extends Ui {
    /** The default number of spaces used for indenting output. */
    private static final int DEFAULT_INDENT_LEVEL = 4;

    /**
     * Prints a string to the console with a specified level of indentation.
     * @param s The string to print.
     * @param indentLevel The number of leading spaces to apply.
     */
    private void print(String s, int indentLevel) {
        System.out.println(" ".repeat(indentLevel) + s);
    }

    /**
     * Prints a string to the console using the {@code DEFAULT_INDENT_LEVEL}.
     * @param s The string to print.
     */
    private void print(String s) {
        print(s, DEFAULT_INDENT_LEVEL);
    }

    /**
     * Prints a message wrapped between horizontal line separators.
     * Multiline messages are split and indented individually.
     * @param message The message to be displayed.
     */
    @Override
    public void printMessage(String message) {
        String hl = "---------------------------------------------";

        print(hl);

        for (String m : message.split("\n")) {
            print(m);
        }

        print(hl);
    }

    /**
     * Formats and prints a confirmation message when a task is added.
     * Includes the task details and the updated total task count.
     * @param tasks The TaskList containing the new task.
     * @param addedTask The task that was recently added.
     */
    @Override
    public void printTaskAddedMessage(TaskList tasks, Task addedTask) {
        assert tasks != null;
        assert addedTask != null;

        String l1 = "Got it. I've added this task:\n";
        String l2 = "  " + addedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    /**
     * Formats and prints a confirmation message when a task is deleted.
     * @param tasks The TaskList after the task has been removed.
     * @param deletedTask The task that was removed.
     */
    @Override
    public void printTaskDeletedMessage(TaskList tasks, Task deletedTask) {
        assert tasks != null;
        assert deletedTask != null;

        String l1 = "Noted. I've removed this task:\n";
        String l2 = "  " + deletedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    /**
     * Prints the entire list of tasks with an accompanying header message.
     * Tasks are indexed starting from 1.
     * @param tasks The TaskList to iterate through.
     * @param message The header message (e.g., "Here are the tasks in your list:").
     */
    @Override
    public void printTaskList(TaskList tasks, String message) {
        assert tasks != null;

        String s = message + "\n";

        for (int i = 1; i <= tasks.getSize(); i++) {
            s += i + "." + tasks.getTaskAtIndex(i) + "\n";
        }

        printMessage(s);
    }
}

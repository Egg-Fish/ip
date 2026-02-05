package egg;

public class TextUi extends Ui {
    private static final int DEFAULT_INDENT_LEVEL = 4;

    private void print(String s, int indentLevel) {
        System.out.println(" ".repeat(indentLevel) + s);
    }

    private void print(String s) {
        print(s, DEFAULT_INDENT_LEVEL);
    }

    @Override
    public void printMessage(String message) {
        String hl = "---------------------------------------------";

        print(hl);

        for (String m : message.split("\n")) {
            print(m);
        }

        print(hl);
    }

    @Override
    public void printTaskAddedMessage(TaskList tasks, Task addedTask) {
        String l1 = "Got it. I've added this task:\n";
        String l2 = "  " + addedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    @Override
    public  void printTaskDeletedMessage(TaskList tasks, Task deletedTask) {
        String l1 = "Noted. I've removed this task:\n";
        String l2 = "  " + deletedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }

    @Override
    public void printTaskList(TaskList tasks, String message) {
        String s = message + "\n";
        
        for (int i = 1; i <= tasks.getSize(); i++) {
            s += i + "." + tasks.getTaskAtIndex(i) + "\n";
        }

        printMessage(s);
    }
}

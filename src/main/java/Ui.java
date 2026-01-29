import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Ui {
    private static final int DEFAULT_INDENT_LEVEL = 4;
        
    public static void print(String s, int indentLevel){
        System.out.println(" ".repeat(indentLevel) + s);
    }

    public static void print(String s){
        print(s, DEFAULT_INDENT_LEVEL);
    }

    public static void printMessage(String message) {
        String hl = "---------------------------------------------";
        
        print(hl);
        
        for (String m : message.split("\n")) {
            print(m);
        }
        
        print(hl);
    }

    public static void printTaskAddedMessage(TaskList tasks, Task addedTask) {
        String l1 = "Got it. I've added this task:\n";
        String l2 = "  " + addedTask + "\n";
        String l3 = "Now you have " + tasks.getSize() + " task(s) in the list.";

        printMessage(l1 + l2 + l3);
    }
}

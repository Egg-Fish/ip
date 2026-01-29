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
}

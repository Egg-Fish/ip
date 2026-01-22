import java.util.Scanner;
import java.util.ArrayList;

public class Egg {
    private static void print(String s, int indent){
        System.out.println(" ".repeat(indent) + s);
    }

     private static void print(String s){
         print(s, 4);
    }

    private static void printMessage(String message) {
        String hl = "------------------------------------------";
        
        print(hl);
        
        for (String m : message.split("\n")) {
            print(m);
        }
        
        print(hl);
    }
    
    public static void main(String[] args) {
        printMessage("Hello! I'm Egg \nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> messages = new ArrayList<>();
        
        while (true) {
            String message = scanner.nextLine();

            if (message.equals("bye")) {
                break;
            }

            else if (message.equals("list")) {
                String s = "";
                
                for (int i = 0; i < messages.size(); i++) {
                    s += (i + 1) + ". " + messages.get(i) + "\n";
                }

                printMessage(s);
            }
            
            else {
                printMessage("added: " + message);
                messages.add(message);
            }
        }

        printMessage("Bye. Hope to see you again soon!");
    }
}

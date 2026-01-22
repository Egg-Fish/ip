import java.util.Scanner;

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
        
        while (true) {
            String msg = scanner.nextLine();

            if (msg.equals("bye")) {
                break;
            }

            printMessage(msg);
        }

        printMessage("Bye. Hope to see you again soon!");
    }
}

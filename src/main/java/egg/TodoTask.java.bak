import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public static Pattern todoPattern = Pattern.compile("\\[T\\]\\[([ X])\\] ([^\\n]+)");

    public static Task fromString(String s) {
        Matcher matcher = todoPattern.matcher(s);
        
        if (matcher.matches()) {
            boolean isMarked = matcher.group(1).equals("X");
            String description = matcher.group(2);

            Task task = new TodoTask(description);
            if (isMarked) {
                task.mark();
            }

            return task;
        } else {
            throw new RuntimeException();
        }
    } 
}

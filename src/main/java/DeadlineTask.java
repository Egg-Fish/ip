import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DeadlineTask extends Task {
    protected String by;

    public DeadlineTask(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    public static Pattern deadlinePattern = Pattern.compile("\\[D\\]\\[([ X])\\] ([^\\n]+) \\(by: ([^\\n]+)\\)");

    public static Task fromString(String s) {
        Matcher matcher = deadlinePattern.matcher(s);
        if (matcher.matches()) {
            boolean isMarked = matcher.group(1).equals("X");
            String description = matcher.group(2);
            String by = matcher.group(3);

            Task task = new DeadlineTask(description, by);
            if (isMarked) {
                task.mark();
            }
            
            return task;
        }
        else {
            throw new RuntimeException();
        }
    }
}

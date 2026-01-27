import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EventTask extends Task {
    protected String from;
    protected String to;

    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    public static Pattern eventPattern = Pattern.compile("\\[E\\]\\[([ X])\\] ([^(]+) \\(from: ([^\\n]+) to: ([^\\n]+)\\)");

    public static Task fromString(String s) {
        Matcher matcher = eventPattern.matcher(s);
        if (matcher.matches()) {
            boolean isMarked = matcher.group(1).equals("X");
            String description = matcher.group(2);
            String from = matcher.group(3);
            String to = matcher.group(4);

            EventTask task = new EventTask(description, from, to);
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

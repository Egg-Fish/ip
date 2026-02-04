package egg;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventTask extends Task {
    private static Pattern eventPattern =
        Pattern.compile("\\[E\\]\\[([ X])\\] ([^(]+) \\(from: ([^\\n]+) to: ([^\\n]+)\\)");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy");

    protected LocalDate from;
    protected LocalDate to;

    public EventTask(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        String fromString = from.format(dateFormatter);
        String toString = to.format(dateFormatter);

        return "[E]" + super.toString() + " (from: " + fromString + " to: " + toString + ")";
    }

    public static Task fromString(String s) {
        Matcher matcher = eventPattern.matcher(s);
        if (matcher.matches()) {
            boolean isMarked = matcher.group(1).equals("X");
            String description = matcher.group(2);
            LocalDate from = LocalDate.parse(matcher.group(3), dateFormatter);
            LocalDate to = LocalDate.parse(matcher.group(4), dateFormatter);

            EventTask task = new EventTask(description, from, to);
            if (isMarked) {
                task.mark();
            }

            return task;
        } else {
            throw new RuntimeException();
        }
    }
}

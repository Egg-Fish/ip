package egg;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineTask extends Task {
    protected LocalDate by;

    public DeadlineTask(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy");

    @Override
    public String toString() {
        String byString = by.format(dateFormatter);

        return "[D]" + super.toString() + " (by: " + byString + ")";
    }

    public static Pattern deadlinePattern = Pattern.compile("\\[D\\]\\[([ X])\\] ([^\\n]+) \\(by: ([^\\n]+)\\)");

    public static Task fromString(String s) {
        Matcher matcher = deadlinePattern.matcher(s);
        if (matcher.matches()) {
            boolean isMarked = matcher.group(1).equals("X");
            String description = matcher.group(2);
            LocalDate by = LocalDate.parse(matcher.group(3), dateFormatter);

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

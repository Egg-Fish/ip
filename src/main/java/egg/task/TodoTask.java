package egg.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodoTask extends Task {
    private static Pattern todoPattern = Pattern.compile("\\[T\\]\\[([ X])\\] ([^\\n]+) \\((.*)\\)");

    public TodoTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + " (" + super.getTagString() + ")";
    }

    public static Task fromString(String s) {
        Matcher matcher = todoPattern.matcher(s);

        if (matcher.matches()) {
            boolean isMarked = matcher.group(1).equals("X");
            String description = matcher.group(2);
            String tagString = matcher.group(3);

            Task task = new TodoTask(description);
            if (isMarked) {
                task.mark();
            }

            task.addTagsFromString(tagString);

            return task;
        } else {
            throw new RuntimeException("Could not parse as todo task: " + s);
        }
    }
}

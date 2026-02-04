package egg;

import java.util.List;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    public int getSize() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskAtIndex(int index) {
        return tasks.get(index - 1);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public TaskList filterByKeyword(String keyword) {
        TaskList filteredTasks = new TaskList();

        for (Task t : this.getTasks()) {
            if (t.getDescription().contains(keyword)) {
                filteredTasks.addTask(t);
            }
        }

        return filteredTasks;
    }
}

package egg.command;

import egg.Storage;
import egg.task.TaskList;
import egg.Ui;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
   
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList filteredTasks = tasks.filterByKeyword(keyword);
        
        ui.printTaskList(filteredTasks, "Here are the matching tasks in your list:");
    }
}

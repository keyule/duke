package commands;

import storage.Storage;
import tasks.TaskList;
import ui.UI;

public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    private String searchTerm;

    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }


    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        TaskList tempList;
        tempList = taskList.getSearchResults(searchTerm);
        ui.printSearchResults(tempList);
    }

}
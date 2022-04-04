package commands;

import tasks.*;
import ui.UI;

public class IncorrectCommand extends Command {

    private String errorMessage;

    public IncorrectCommand(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public void execute(TaskList taskList, UI ui) {
        ui.printError(errorMessage);
    }
}
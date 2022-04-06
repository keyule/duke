package commands;

import java.util.Date;

import storage.Storage;
import tasks.TaskList;
import tasks.Todo;
import ui.UI;

public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    private int index;
    private String newContent;
    private String partToUpdate;
    private Date date;

    /**
     * Constructor for the Update Command Class
     *
     * @param index index of the task to be updated
     * @param partToUpdate which part of the task you want updated: date or description
     * @param newContent the new description to be updated
     * */
    public UpdateCommand(int index, String partToUpdate, String newContent) {
        this.index = index;
        this.partToUpdate = partToUpdate;
        this.newContent = newContent;
    }

    /**
     * Constructor for the Update Command Class
     *
     * @param index index of the task to be updated
     * @param partToUpdate which part of the task you want updated: date or description
     * @param date the new date to be updated
     * */
    public UpdateCommand(int index, String partToUpdate, Date date) {
        this.index = index;
        this.partToUpdate = partToUpdate;
        this.date = date;
    }


    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) {
        try {
            if (partToUpdate.equalsIgnoreCase("desc")) {
                taskList.get(index - 1).updateDescription(newContent);
                ui.printUpdatedTask(taskList.get(index - 1).toString());
            }

            if (taskList.get(index - 1).getClass().equals(Todo.class)) {
                ui.printTaskNotUpdated(taskList.get(index - 1).toString());
            } else {
                taskList.get(index - 1).updateDate(date);
                ui.printUpdatedTask(taskList.get(index - 1).toString());
            }

        } catch (IndexOutOfBoundsException e) {
            ui.printErrorTaskDoesNotExist(String.valueOf(index));
        } finally {
            storage.save(taskList);
        }
    }

}
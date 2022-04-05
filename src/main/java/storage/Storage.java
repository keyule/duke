package storage;

import exceptions.TooManyDatesException;
import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;
import parser.DateParser;

import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "data";
    private static final String FILE_NAME = "duke.txt";

    public void init(){
        Path dirPathObj = Paths.get(FILE_PATH);
        boolean dirExists = Files.exists(dirPathObj);
        if(dirExists) {
            //System.out.println("! Directory Already Exists !");
        } else {
            try {
                Files.createDirectories(dirPathObj);
                //System.out.println("! New Directory Successfully Created !");
            } catch (IOException ioExceptionObj) {
                System.out.println("Problem Occurred While Creating The Directory Structure= " + ioExceptionObj.getMessage());
            }
        }
    }

    public void checkIfFileExists(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch( IOException ioExceptionObj){
                System.out.println("Problem Occurred While Creating Save File= " + ioExceptionObj.getMessage());
            }
        }
    }

    public void save(TaskList taskList){
        File yourFile = new File(FILE_PATH,FILE_NAME);
        checkIfFileExists(yourFile);
        ArrayList<Task> allTasks = taskList.getAllTask();

        try{
            FileWriter myWriter = new FileWriter(yourFile);
            for(Task task: allTasks){
                String test = task.getSaveFormat() + "\n";
                myWriter.write (test);
            }
            myWriter.close();
        }catch( IOException ioExceptionObj) {
            System.out.println("Problem Occurred While Saving Data " + ioExceptionObj.getMessage());
        }
    }

    public TaskList loadTasks(){
        TaskList tempTaskList = new TaskList();

        File yourFile = new File(FILE_PATH,FILE_NAME);

        checkIfFileExists(yourFile);

        try (Scanner scanner = new Scanner(yourFile)) {
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                tempTaskList.addTask(convertStrToTask(line));
            }
        } catch (IOException ioExceptionObj) {
            System.out.println("Problem Occurred While Loading Data " + ioExceptionObj.getMessage());
        }

        return tempTaskList;
    }

    public Task convertStrToTask(String str){
        Task task = null;
        try {
            String[] splittedString = str.split("\\|");

            String taskType = splittedString[0];
            String status = splittedString[1];
            String description = splittedString[2];


            switch (taskType) {
                case "T":
                    task = new Todo(description);
                    break;

                case "D":
                    Date dateD = DateParser.parseDate(splittedString[3]);
                    task = new Deadline(description, dateD);
                    break;

                case "E":
                    Date dateE = DateParser.parseDate(splittedString[3]);
                    task = new Event(description, dateE);
                    break;
            }
            if (status.equals("1")) {
                task.markDone();
            }
        }
        catch(IndexOutOfBoundsException | TooManyDatesException e){
            System.out.println("Something went wrong with the safe file");
        }

        return task;
    }
}


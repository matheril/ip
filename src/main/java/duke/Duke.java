package duke;

import duke.command.Command;

import java.io.IOException;

public class Duke {
    Ui ui;
    Storage storage;
    TaskList tasks;

    public Duke(String filePath) {
        storage = new Storage(filePath);
        tasks = new TaskList(storage.getTasks());
        ui = new Ui(tasks);
    }

    public static void main(String[] args) {
        try {
            new Duke("data/duke.txt").start();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void start() {
        ui.printGreeting();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printDivider();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, tasks, storage);
                isExit = c.isExit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ui.printDivider();
        }
        try {
            storage.updateSaveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

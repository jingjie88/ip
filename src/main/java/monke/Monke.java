package monke;

import monke.commands.Command;

public class Monke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Monke(String filepath) {
        this.ui = new Ui();
        this.storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MonkeException e) {
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.printHorizontalLine();
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printHorizontalLine();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, storage, tasks);
                isExit = c.isExit();
            } catch (MonkeException e) {
                ui.print(e.getMessage());
            } finally {
                ui.printHorizontalLine();
            }
        }
    }

    public static void main(String[] args) {
        new Monke("./data/tasks.txt").run();
    }
}

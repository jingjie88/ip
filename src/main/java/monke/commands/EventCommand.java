package monke.commands;

import monke.*;

public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";
    private String description;
    private String start;
    private String end;

    public EventCommand(String description, String start, String end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList tasks) throws MonkeException {
        Event event = new Event(this.description, this.start, this.end);
        tasks.add(event);
        storage.saveData(tasks);
        ui.showAddTask(event, tasks.size());
    }
}

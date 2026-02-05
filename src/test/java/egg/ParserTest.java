package egg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class ParserTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
        ui = new TextUi();
        storage = new Storage("./data/test_tasks.txt");

        storage.store(tasks);
    }
    
    @Test
    public void parseDeadlineCommand_normal_success(){
        String s = "deadline my task there /by 2026-01-02";
        Command c = Parser.parseDeadlineCommand(s);
        c.execute(tasks, ui, storage);
        DeadlineTask task = (DeadlineTask) tasks.getTaskAtIndex(1);
        
        assertEquals("my task there", task.description);
        assertEquals(LocalDate.parse("2026-01-02"), task.by);
    }

    @Test
    public void parseDeadlineCommand_excessWhitespace_success(){
        String s = "deadline            my task here      /by     2026-01-02";
        Command c = Parser.parseDeadlineCommand(s);
        c.execute(tasks, ui, storage);
        DeadlineTask task = (DeadlineTask) tasks.getTaskAtIndex(1);
        
        assertEquals("my task here", task.description);
        assertEquals(LocalDate.parse("2026-01-02"), task.by);
    }

    @Test
    public void parseDeadlineCommand_invalidDate_exceptionThrown(){
        String s = "deadline my task there /by today";

        try {
            Command c = Parser.parseDeadlineCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as date: today", e.getMessage());
        }
    }

    @Test
    public void parseDeadlineCommand_missingBy_exceptionThrown(){
        String s = "deadline my task there by 2026-01-02";

        try {
            Command c = Parser.parseDeadlineCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as deadline command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseDeadlineCommand_missingByDate_exceptionThrown(){
        String s = "deadline my task there /by";

        try {
            Command c = Parser.parseDeadlineCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as deadline command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseDeadlineCommand_missingBody_exceptionThrown(){
        String s = "deadline /by 2026-01-02";

        try {
            Command c = Parser.parseDeadlineCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as deadline command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseEventCommand_normal_success(){
        String s = "event hackathon /from 2026-01-02 /to 2026-06-07";
        Command c = Parser.parseEventCommand(s);
        c.execute(tasks, ui, storage);
        EventTask task = (EventTask) tasks.getTaskAtIndex(1);
        
        assertEquals("hackathon", task.description);
        assertEquals(LocalDate.parse("2026-01-02"), task.from);
        assertEquals(LocalDate.parse("2026-06-07"), task.to);
    }

    @Test
    public void parseEventCommand_excessWhitespace_success(){
        String s = "event      hackathon    /from    2026-01-02   /to    2026-06-07  ";
        Command c = Parser.parseEventCommand(s);
        c.execute(tasks, ui, storage);
        EventTask task = (EventTask) tasks.getTaskAtIndex(1);
        
        assertEquals("hackathon", task.description);
        assertEquals(LocalDate.parse("2026-01-02"), task.from);
        assertEquals(LocalDate.parse("2026-06-07"), task.to);
    }

    @Test
    public void parseEventCommand_missingFrom_exceptionThrown(){
        String s = "event hackathon from 2026-01-02 /to 2026-06-07";

        try {
            Command c = Parser.parseEventCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as event command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseEventCommand_missingFromDate_exceptionThrown(){
        String s = "event hackathon /from  /to 2026-06-07";

        try {
            Command c = Parser.parseEventCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as event command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseEventCommand_missingTo_exceptionThrown(){
        String s = "event hackathon /from 2026-01-02 to 2026-06-07";

        try {
            Command c = Parser.parseEventCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as event command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseEventCommand_missingToDate_exceptionThrown(){
        String s = "event hackathon /from 2026-01-02 /to ";

        try {
            Command c = Parser.parseEventCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as event command: " + s, e.getMessage());
        }
    }

    @Test
    public void parseEventCommand_missingFromAndTo_exceptionThrown(){
        String s = "event hackathon from 2026-01-02 to 2026-06-07";

        try {
            Command c = Parser.parseEventCommand(s);
        } catch (Exception e) {
            assertEquals("Could not parse as event command: " + s, e.getMessage());
        }
    }
}

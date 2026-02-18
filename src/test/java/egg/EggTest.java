package egg;

import java.lang.AssertionError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EggTest {
    private Egg egg;

    @BeforeEach
    public void setUp() {
        egg = new Egg();
    }

    @Test
    public void runCommand_nullString_exceptionThrown(){
        String s = null;

        assertThrows(AssertionError.class, () -> {
                egg.runCommand(s);
        });
    }
}

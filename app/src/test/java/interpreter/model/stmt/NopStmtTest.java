package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NopStmtTest
{
    private PrgState state;

    @BeforeEach
    void setUp()
    {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.ex1);
    }

    @Test
    void executeCorrect()
    {
        assertEquals(state, new NopStmt().execute(state));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("nop", new NopStmt().toString());
    }
}

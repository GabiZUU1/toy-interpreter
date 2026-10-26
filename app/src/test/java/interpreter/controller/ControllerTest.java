package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest
{
    private static PrgState run(IStmt program) throws MyException
    {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), program);
        new Controller(new Repository(state), false).allStep(); // display off
        return state;
    }

    @Test
    void example2Prints18() throws MyException
    {
        PrgState st = run(Examples.ex2);

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(18), st.getOut().get(0));
    }
}

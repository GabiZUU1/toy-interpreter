package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest
{
    @TempDir
    private Path dir;

    private PrgState run(IStmt program) throws MyException
    {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, this.dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep(); // display off
        return state;
    }

    @Test
    void example1Prints2() throws MyException
    {
        PrgState st = run(Examples.ex1());

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(2), st.getOut().get(0));
    }

    @Test
    void example2Prints18() throws MyException
    {
        PrgState st = run(Examples.ex2());

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(18), st.getOut().get(0));
    }

    @Test
    void example3Prints2() throws MyException
    {
        PrgState st = run(Examples.ex3());

        assertTrue(st.getExeStack().isEmpty());
        assertEquals(new IntValue(2), st.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException
    {
        PrgState state = PrgState.of(new NopStmt());
        Controller controller = new Controller(new Repository(state, "testLog.txt"), false);
        state.getExeStack().pop();

        var ex = assertThrows(StatementException.class, () -> controller.oneStep(state));

        assertEquals("program state stack is empty", ex.getMessage());
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.ex4(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }
    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.ex1());
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"), d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }
}

package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.view.Examples;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class CloseRFileStmtTest
{
    private PrgState state;
    private final String file = "../test.in";

    BufferedReader failingReader = new BufferedReader(new StringReader(""))
    {
        @Override
        public void close() throws IOException
        {
            throw new IOException("boom");
        }
    };

    @BeforeEach
    void setUp()
    {
        this.state = PrgState.of(Examples.ex1);
    }

    @Test
    void expNotStringThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new CloseRFileStmt(new ValueExp(new IntValue(1))).execute(this.state));
        assertEquals("closeRFile: 1 is not a string", ex.getMessage());
    }

    @Test
    void fileNotOpenThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new CloseRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state));
        assertEquals("closeRFile: " + this.file + " is not open", ex.getMessage());
    }

    @Test
    void cannotCloseThrows()
    {
        var name = new StringValue("ee.in");
        this.state.getFileTable().put(name, failingReader);

        var ex = assertThrows(StatementException.class, () -> new CloseRFileStmt(new ValueExp(name)).execute(this.state));
        assertEquals("closeRFile: cannot close ee.in: boom", ex.getMessage());
    }

    @Test
    void executeCorrect() throws MyException
    {
        new OpenRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state);

        new CloseRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state);
        assertTrue(this.state.getSymTable().toMap().isEmpty());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("closeRFile(" + this.file + ")", new CloseRFileStmt(new ValueExp(new StringValue(this.file))).toString());
    }
}

package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
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

public class OpenRFileStmtTest
{
    private PrgState state;
    private final String file = "../test.in";

    @BeforeEach
    void setUp()
    {
        this.state = PrgState.of(Examples.ex1);
    }

    @Test
    void expNotStringThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new OpenRFileStmt(new ValueExp(new IntValue(1))).execute(this.state));
        assertEquals("openRFile: 1 is not a string", ex.getMessage());
    }

    @Test
    void fileAlreadyOpenThrows() throws MyException
    {
        new OpenRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state);
        var ex = assertThrows(StatementException.class, () -> new OpenRFileStmt(new ValueExp(new StringValue(this.file))).execute(this.state));
        assertEquals("openRFile: " + this.file + " is already open", ex.getMessage());
    }

    @Test
    void cannotOpenThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new OpenRFileStmt(new ValueExp(new StringValue("ok"))).execute(this.state));
        assertEquals("openRFile: cannot open ok: ok (No such file or directory)", ex.getMessage());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("openRFile(alo.txt)", new OpenRFileStmt(new ValueExp(new StringValue("alo.txt"))).toString());
    }
}

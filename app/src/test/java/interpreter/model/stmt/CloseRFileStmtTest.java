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
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class CloseRFileStmtTest
{
    private PrgState state;

    @TempDir
    private Path dir;
    private ValueExp file;

    BufferedReader failingReader = new BufferedReader(new StringReader(""))
    {
        @Override
        public void close() throws IOException
        {
            throw new IOException("boom");
        }
    };

    @BeforeEach
    void setUp() throws Exception
    {
        this.state = PrgState.of(new NopStmt());

        Path f = this.dir.resolve("file.in");

        Files.writeString(f, "7\n");

        this.file = new ValueExp(new StringValue(f.toString()));
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
        var ex = assertThrows(StatementException.class, () -> new CloseRFileStmt(this.file).execute(this.state));
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
        new OpenRFileStmt(this.file).execute(this.state);

        new CloseRFileStmt(this.file).execute(this.state);
        assertTrue(this.state.getFileTable().toMap().isEmpty());
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("closeRFile(" + this.file + ")", new CloseRFileStmt(this.file).toString());
    }
}

package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
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

public class OpenRFileStmtTest
{
    private PrgState state;

    @TempDir
    private Path dir;
    private ValueExp file;

    @BeforeEach
    void setUp() throws Exception
    {
        this.state = PrgState.of(new NopStmt());

        Path f = this.dir.resolve("numbers.in");
        Files.writeString(f, "7\n");

        this.file = new ValueExp(new StringValue(f.toString()));

        new VarDeclStmt("a", new IntType()).execute(this.state);
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
        new OpenRFileStmt(this.file).execute(this.state);
        var ex = assertThrows(StatementException.class, () -> new OpenRFileStmt(this.file).execute(this.state));
        assertEquals("openRFile: " + this.file + " is already open", ex.getMessage());
    }

    @Test
    void cannotOpenThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new OpenRFileStmt(new ValueExp(new StringValue("ok"))).execute(this.state));
        assertEquals("openRFile: cannot open ok: ok (No such file or directory)", ex.getMessage());
    }

    @Test
    void addFileToFileTable() throws MyException
    {
        new OpenRFileStmt(this.file).execute(this.state);

        assertTrue(this.state.getFileTable().isDefined(new StringValue(this.file.toString())));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("openRFile(alo.txt)", new OpenRFileStmt(new ValueExp(new StringValue("alo.txt"))).toString());
    }
}

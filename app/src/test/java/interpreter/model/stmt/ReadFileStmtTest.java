package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

public class ReadFileStmtTest
{
    private PrgState state;

    @TempDir
    private Path dir;
    private ValueExp goodFile;
    private ValueExp good0File;
    private ValueExp badFile;

    @BeforeEach
    void setUp() throws Exception
    {
        this.state = PrgState.of(new NopStmt());

        Path good = this.dir.resolve("good.in");
        Path good0 = this.dir.resolve("good0.in");
        Path bad = this.dir.resolve("bad.in");

        Files.writeString(good, "123\n67\n");
        Files.writeString(good0, "");
        Files.writeString(bad, "asd");

        this.goodFile = new ValueExp(new StringValue(good.toString()));
        this.good0File = new ValueExp(new StringValue(good0.toString()));
        this.badFile = new ValueExp(new StringValue(bad.toString()));

        new VarDeclStmt("a", new BoolType()).execute(this.state);
        new VarDeclStmt("b", new IntType()).execute(this.state);
    }

    @Test
    void expNotStringThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new IntValue(1)), "okay").execute(this.state));
        assertEquals("readFile: 1 is not a string", ex.getMessage());
    }

    @Test
    void fileNotOpenThrows()
    {
        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(new ValueExp(new StringValue("ok")), "a").execute(this.state));
        assertEquals("readFile: ok is not open", ex.getMessage());
    }

    @Test
    void varNotDefinedThrows() throws MyException
    {
        new OpenRFileStmt(this.goodFile).execute(this.state);

        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(this.goodFile, "ok").execute(this.state));
        assertEquals("readFile: ok is not defined", ex.getMessage());
    }

    @Test
    void varNotIntThrows() throws MyException
    {
        new OpenRFileStmt(this.goodFile).execute(this.state);

        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(this.goodFile, "a").execute(this.state));
        assertEquals("readFile: a is not of type int", ex.getMessage());
    }

    @Test
    void cantReadTest() throws MyException
    {
        new OpenRFileStmt(this.badFile).execute(this.state);

        var ex = assertThrows(StatementException.class, () -> new ReadFileStmt(this.badFile, "b").execute(this.state));
        assertEquals("readFile: cannot read " + this.badFile + ": For input string: \"asd\"", ex.getMessage());
    }

    @Test
    void reads0Value() throws MyException
    {
        new OpenRFileStmt(this.good0File).execute(this.state);
        new ReadFileStmt(this.good0File, "b").execute(this.state);

        assertEquals(new IntValue(0), this.state.getSymTable().lookup("b"));
    }

    @Test
    void readsAllValuesCorrectly() throws MyException
    {
        new OpenRFileStmt(this.goodFile).execute(this.state);

        new ReadFileStmt(this.goodFile, "b").execute(this.state);
        assertEquals(new IntValue(123), this.state.getSymTable().lookup("b"));

        new ReadFileStmt(this.goodFile, "b").execute(this.state);
        assertEquals(new IntValue(67), this.state.getSymTable().lookup("b"));

        new ReadFileStmt(this.goodFile, "b").execute(this.state);
        assertEquals(new IntValue(0), this.state.getSymTable().lookup("b"));
    }

    @Test
    void toStringCorrect()
    {
        assertEquals("readFile(ok.in)", new ReadFileStmt(new ValueExp(new StringValue("ok.in")), "a").toString());
    }
}

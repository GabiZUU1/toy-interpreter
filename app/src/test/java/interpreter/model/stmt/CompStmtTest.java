package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.view.Examples;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompStmtTest
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
        var x = new CompStmt(new VarDeclStmt("a", new IntType()), new AssignStmt("a", new ValueExp(new IntValue(12))));

        x.execute(state);

        Assertions.assertFalse(state.getExeStack().isEmpty());
    }

    @Test
    void toStringCorrect()
    {
        Assertions.assertEquals("(int a; a = 10)", new CompStmt(new VarDeclStmt("a", new IntType()), new AssignStmt("a", new ValueExp(new IntValue(10)))).toString());
    }
}

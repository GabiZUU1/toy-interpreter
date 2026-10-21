package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;

public record VarDeclStmt(String id, IType type) implements IStmt
{
    @Override
    public PrgState execute(PrgState state)
    {
        var symTable = state.getSymTable();
        if(symTable.isDefined(id))
        {
            throw new StatementException("");
        }

        symTable.
    }
}

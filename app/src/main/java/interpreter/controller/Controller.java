package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller
{
    IRepository repo;

    public Controller(IRepository repo)
    {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException
    {
        var stack = state.getExeStack();
        if(stack.isEmpty())
        {
            throw new StatementException("program state stack is empty");
        }

        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException
    {
        PrgState prg = repo.getCrtPrg();
        display(prg); // only if the display flag is on
        while(!prg.getExeStack().isEmpty())
        {
            oneStep(prg);
            display(prg);
        }
    }
}

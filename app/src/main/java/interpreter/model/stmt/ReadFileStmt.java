package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt
{
    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        if(!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
        {
            throw new StatementException("readFile: " + exp + " is not a string");
        }

        var fileTable = state.getFileTable();
        if(!fileTable.isDefined(fileName))
        {
            throw new StatementException("readFile: " + fileName + " is not open");
        }

        var symTable = state.getSymTable();
        if(!symTable.isDefined(varName))
        {
            throw new StatementException("readFile: " + varName + " is not defined");
        }

        if(!(symTable.lookup(varName) instanceof IntValue))
        {
            throw new StatementException("readFile: " + varName + " is not of type int");
        }

        try
        {
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();

            int n;

            if(line == null)
            {
                n = 0;
            }
            else
            {
                n = Integer.parseInt(line.strip());
            }
            symTable.update(varName, new IntValue(n));
        }
        catch (IOException | NumberFormatException e)
        {
            throw new StatementException("readFile: cannot read " + fileName + ": " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString()
    {
        return "readFile(" + exp + ")";
    }
}
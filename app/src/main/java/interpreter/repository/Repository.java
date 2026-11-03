package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository
{
    private final List<PrgState> states = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState state, String logFilePath)
    {
        this.states.add(state);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg()
    {
        return this.states.getFirst();
    }

    @Override
    public void logPrgStateExec() throws MyException
    {
        try (var logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true))))
        {
            logFile.println(getCrtPrg().toLogString());
        }
        catch (IOException e)
        {
            throw new RepositoryException("cannot write the log file " + this.logFilePath + ": " + e.getMessage());
        }
    }
}

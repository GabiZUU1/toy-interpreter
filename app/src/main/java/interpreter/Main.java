package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.*;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        List<IStmt> programs = List.of(Examples.ex1(), Examples.ex2(), Examples.ex3(), Examples.ex4("test.in"), Examples.ex5());
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        for(int i = 0; i < programs.size(); i++)
        {
            IStmt program = programs.get(i);
            var repo = new Repository(PrgState.of(program), "log" + (i + 1) + ".txt");
            var controller = new Controller(repo, true);
            menu.addCommand(new RunExampleCommand(String.valueOf(i + 1), program.toString(), controller));
        }
        menu.show();
    }
}

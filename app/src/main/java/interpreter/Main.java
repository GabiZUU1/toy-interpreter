package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import interpreter.view.View;

public class Main
{
    public static void main(String[] args)
    {
        PrgState state = PrgState.of(Examples.ex1);
        IRepository repo = new Repository(state, "log1.txt");
        Controller cont = new Controller(repo, true);
        View ui = new View(cont);

        ui.start();
    }
}

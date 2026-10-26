package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

import static java.lang.IO.print;
import static java.lang.IO.println;

public class View
{
    private IStmt selected = Examples.ex1;
    private Controller controller;
    private final Scanner scanner;

    public View(Controller controller)
    {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void start()
    {
        this.printMainMenu();
    }

    private void printMainMenu()
    {
        int o = -1;

        while(o != 0)
        {
            println("--------------------------------------------");
            println("Selected program: " + this.selected);
            println("1. Select a program");
            println("2. Run the selected program");
            println("0. Exit");
            println("--------------------------------------------");
            print("Choose an option: ");
            o = this.readInt();

            try
            {
                switch(o)
                {
                    case 0 ->
                    {
                        return;
                    }
                    case 1 -> printSelectMenu();
                    case 2 -> printRunMenu();
                    default -> this.printMessage("Not a valid option, try again!");
                }
            }
            catch (MyException e)
            {
                this.printMessage(e.getMessage());
            }
        }
    }

    private void printSelectMenu()
    {
        int o = -1;

        while(o != 0)
        {
            println("--------------------------------------------");
            println("0: Go back");
            println("1: int v; v = 2; print(v)");
            println("2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)");
            println("3: bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)");
            println("--------------------------------------------");
            print("Choose an program: ");

            o = readInt();

            switch (o)
            {
                case 0 -> {return;}
                case 1 ->
                {
                    this.selected = setNewState(Examples.ex1);
                    return;
                }
                case 2 ->
                {
                    this.selected = setNewState(Examples.ex2);
                    return;
                }
                case 3 ->
                {
                    this.selected = setNewState(Examples.ex3);
                    return;
                }
                default -> this.printMessage("Not a valid option, try again!");
            }
        }
    }

    private void printRunMenu() throws MyException
    {
        this.controller.allStep();
    }

    private IStmt setNewState(IStmt statement)
    {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), statement);
        IRepository repo = new Repository(state);
        Controller cont = new Controller(repo, true);
        this.controller = cont;

        return statement;
    }

    private int readInt()
    {
        while(true)
        {
            try
            {
                return Integer.parseInt(this.scanner.nextLine());
            }
            catch (NumberFormatException e)
            {
                this.printMessage("Please enter a number!");
            }
        }
    }

    private void printMessage(String message)
    {
        println("-".repeat(message.length() + 6));
        print("|\t" + message + "\t|\n");
        println("-".repeat(message.length() + 6));
    }
}

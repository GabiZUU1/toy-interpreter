package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.stmt.IStmt;

import java.util.Scanner;

import static java.lang.IO.print;
import static java.lang.IO.println;

public class View
{
    private IStmt selected = Examples.ex1;
    private final Controller controller;
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
                }
            }
            catch (Exception e)
            {
                this.printMessage(e.getMessage());
            }
        }
    }

    private void printSelectMenu()
    {
        int o = -1;

        while(o == -1)
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
                case 1 -> this.selected = Examples.ex1;
                case 2 -> this.selected = Examples.ex2;
                case 3 -> this.selected = Examples.ex3;
                default -> this.printMessage("Not a valid option, try again!");
            }
        }
    }

    private void printRunMenu()
    {

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
                this.printMessage(e.getMessage());
            }
        }
    }

    private void printMessage(String message)
    {
        println("-".repeat(message.length() + 6));
        print("|\tmessage\t|\n");
        println("-".repeat(message.length() + 6));
    }
}

package interpreter.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu
{
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void addCommand(Command c)
    {
        this.commands.put(c.getKey(), c);
    }

    private void printMenu()
    {
        for(Command c : this.commands.values())
        {
            System.out.printf("%4s : %s%n", c.getKey(), c.getDescription());
        }
    }

    public void show()
    {
        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            this.printMenu();
            System.out.print("Input the option: ");
            Command c = this.commands.get(scanner.nextLine().strip());

            if(c == null)
            {
                System.out.println("Invalid option!");
                continue;
            }
            c.execute();
        }
    }
}

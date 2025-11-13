package view.textUI;

import controller.Controller;
import exceptions.MyException;

public class RunExample extends Command {
    private final Controller ctr;

    public RunExample(String key, String desc, Controller _ctr) {
        super(key, desc);
        this.ctr = _ctr;
    }

    @Override
    public void execute() {
        try {
            this.ctr.allStep();
            System.out.println("Program finished.");
        } catch (MyException e) {
            System.out.println("Execution error: " + e.getMessage());
        }
    }
}
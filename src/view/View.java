package view;

import controller.Controller;
import exceptions.MyException;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.ArithExp;
import model.expressions.IExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

public class View {
    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print(">> ");
            String option = scanner.nextLine().trim();
            switch (option) {
                case "0" -> {
                    System.out.println("End of program.");
                    return;
                }
                case "1" -> runProgram(example1());
                case "2" -> runProgram(example2());
                case "3" -> runProgram(example3());
                default -> System.out.println("Comandă invalidă.\n");
            }
        }
    }
    // ex1:
    // int v;
    // v = 2;
    // Print(v);
    private IStmt example1() {
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
    }
    // ex2:
    // int a; int b;
    // a = 2 + 3 * 5;
    // b = a + 1;
    // Print(b);
    private IStmt example2() {
        IExp two = new ValueExp(new IntValue(2));
        IExp three = new ValueExp(new IntValue(3));
        IExp five = new ValueExp(new IntValue(5));
        IExp one = new ValueExp(new IntValue(1));
        IExp aVar = new VarExp("a");
        IExp threeMulFive = new ArithExp(three, five, 3);
        IExp aRhs = new ArithExp(two, threeMulFive, 1);
        IExp bRhs = new ArithExp(aVar, one, 1);
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", aRhs),
                                new CompStmt(
                                        new AssignStmt("b", bRhs),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
    }
    // ex3:
    // bool a; int v;
    // a = true;
    // If a Then v = 2 Else v = 3;
    // Print(v);
    private IStmt example3() {
        return new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
    }
    private void runProgram(IStmt program) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        PrgState prgState = new PrgState(exeStack, symTable, out, program);
        IRepository repo = new Repository();
        repo.addPrgState(prgState);
        Controller ctrl = new Controller(repo);
        try {
            ctrl.allStep();
            System.out.println("Program finish.");
            System.out.println("Output list: " + prgState.getOut().toString());
            System.out.println();
        } catch (MyException e) {
            System.out.println("Execution error: " + e.getMessage());
            System.out.println();
        }
    }
    private void printMenu() {
        System.out.println("0. Exit");
        System.out.println("1. Run example 1");
        System.out.println("2. Run example 2");
        System.out.println("3. Run example 3");
    }
}

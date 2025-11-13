import controller.Controller;
import model.PrgState;
import model.adt.*;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.textUI.ExitCommand;
import view.textUI.RunExample;
import view.textUI.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        // Ex 1: int v; v = 2; Print(v);
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        // Ex 2: int a; int b; a = 2 + 3 * 5; b = a + 1; Print(b);
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp(
                                        new ValueExp(new IntValue(2)),
                                        new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3),
                                        1
                                )),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1)),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );

        // Ex 3: bool a; int v; a = true; (If a Then v = 2 Else v = 3); Print(v);
        IStmt ex3 = new CompStmt(
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

        // Ex 4 (File Operations):
        // string varf; varf="test.in";
        // openRFile(varf);
        // int varc;
        // readFile(varf,varc); print(varc);
        // readFile(varf,varc); print(varc);
        // closeRFile(varf)
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // Ex 5 (Relational): int a; a = 10; int b; b = 12; bool c; c = (a < b); Print(c);
        IStmt ex5 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new AssignStmt("a", new ValueExp(new IntValue(10))),
                        new CompStmt(
                                new VarDeclStmt("b", new IntType()),
                                new CompStmt(
                                        new AssignStmt("b", new ValueExp(new IntValue(12))),
                                        new CompStmt(
                                                new VarDeclStmt("c", new BoolType()),
                                                new CompStmt(
                                                        new AssignStmt("c", new RelationalExp(new VarExp("a"), new VarExp("b"), "<")),
                                                        new PrintStmt(new VarExp("c"))
                                                )
                                        )
                                )
                        )
                )
        );

        Controller ctr1 = createController(ex1, "log1.txt");
        Controller ctr2 = createController(ex2, "log2.txt");
        Controller ctr3 = createController(ex3, "log3.txt");
        Controller ctr4 = createController(ex4, "log4.txt");
        Controller ctr5 = createController(ex5, "log5.txt");
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.show();
    }

    private static Controller createController(IStmt program, String logFilePath) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        PrgState prgState = new PrgState(exeStack, symTable, out, fileTable, program);
        IRepository repo = new Repository(prgState, logFilePath);
        return new Controller(repo);
    }
}
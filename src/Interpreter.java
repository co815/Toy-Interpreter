import controller.Controller;
import model.PrgState;
import model.adt.*;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import exceptions.MyException;

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

        // Ex 6 (Heap): Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(v); print(a);
        // print(rH(v)); print(rH(rH(a)) + 5);
        IStmt ex6 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("a")),
                                                        new CompStmt(
                                                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                                new PrintStmt(new ArithExp(
                                                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                                        new ValueExp(new IntValue(5)),
                                                                        1
                                                                ))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // Ex 7 (Heap wH): Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5);
        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(
                                                new ReadHeapExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5)),
                                                1
                                        ))
                                )
                        )
                )
        );

        // Ex 8 (While): int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2))
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );

        // Ex 9 (Garbage Collector): Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)));
        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );

        // Ex 10 (Concurrent): int v; Ref int a; v=10; new(a,22);
        // fork(wH(a,30); v=32; print(v); print(rH(a)));
        // print(v); print(rH(a))
        IStmt ex10 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        runProgram(ex1, "log1.txt", "1");
        runProgram(ex2, "log2.txt", "2");
        runProgram(ex3, "log3.txt", "3");
        runProgram(ex4, "log4.txt", "4");
        runProgram(ex5, "log5.txt", "5");
        runProgram(ex6, "log6.txt", "6");
        runProgram(ex7, "log7.txt", "7");
        runProgram(ex8, "log8.txt", "8");
        runProgram(ex9, "log9.txt", "9");
        runProgram(ex10, "log10.txt", "10");
    }

    private static void runProgram(IStmt program, String logFilePath, String exampleName) {
        try {
            System.out.println("Running Example " + exampleName + "...");
            MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
            program.typecheck(typeEnv);
            Controller ctr = createController(program, logFilePath);
            ctr.allStep();
        } catch (MyException e) {
            System.out.println("Error in Example " + exampleName + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error in Example " + exampleName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Controller createController(IStmt program, String logFilePath) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap<Integer, IValue> heap = new MyHeap<>();
        PrgState prgState = new PrgState(exeStack, symTable, out, fileTable, heap, program);
        IRepository repo = new Repository(prgState, logFilePath);
        return new Controller(repo);
    }
}

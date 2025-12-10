# Toy Interpreter

A Java-based interpreter for a simple toy programming language. This project implements a complete interpreter with support for variables, arithmetic operations, control flow, file operations, and dynamic memory management with garbage collection.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Running the Interpreter](#running-the-interpreter)
- [Language Syntax](#language-syntax)
- [Example Programs](#example-programs)
- [Understanding the Output](#understanding-the-output)

## Overview

The Toy Interpreter is an educational project that demonstrates the core concepts of programming language interpretation, including:

- Lexical and syntactic representation of programs through Abstract Syntax Trees (AST)
- Statement execution using an execution stack
- Variable management through a symbol table
- Dynamic memory allocation via a heap structure
- Automatic garbage collection for memory management
- File I/O operations

## Features

The interpreter supports the following language features:

### Data Types
- **int**: Integer numbers
- **bool**: Boolean values (true/false)
- **string**: String values
- **Ref T**: Reference types (pointers) to any type T

### Statements
- **Variable Declaration**: Declare variables with specific types
- **Assignment**: Assign values to variables
- **Print**: Output values to the console
- **If-Then-Else**: Conditional branching
- **While Loop**: Repetitive execution
- **File Operations**: Open, read, and close files
- **Heap Operations**: Allocate (new) and write (wH) to heap memory
- **Composite Statements**: Combine multiple statements

### Expressions
- **Arithmetic Expressions**: Addition, subtraction, multiplication, division
- **Logical Expressions**: AND, OR operations
- **Relational Expressions**: Comparisons (<, <=, ==, !=, >, >=)
- **Heap Read (rH)**: Read values from heap memory
- **Variable and Value Expressions**: Access variables and literal values

## Project Structure

```
Toy Interpreter/
├── src/
│   ├── Interpreter.java          # Main entry point with example programs
│   ├── controller/
│   │   └── Controller.java       # Execution controller with garbage collection
│   ├── exceptions/
│   │   └── MyException.java      # Custom exception handling
│   ├── model/
│   │   ├── PrgState.java         # Program state representation
│   │   ├── adt/                  # Abstract Data Types
│   │   │   ├── MyStack.java      # Execution stack implementation
│   │   │   ├── MyDictionary.java # Symbol table implementation
│   │   │   ├── MyList.java       # Output list implementation
│   │   │   └── MyHeap.java       # Heap memory implementation
│   │   ├── expressions/          # Expression types
│   │   │   ├── ArithExp.java     # Arithmetic expressions
│   │   │   ├── LogicExp.java     # Logical expressions
│   │   │   ├── RelationalExp.java# Relational expressions
│   │   │   ├── ReadHeapExp.java  # Heap read expression
│   │   │   ├── VarExp.java       # Variable expression
│   │   │   └── ValueExp.java     # Value expression
│   │   ├── statements/           # Statement types
│   │   │   ├── AssignStmt.java   # Assignment statement
│   │   │   ├── VarDeclStmt.java  # Variable declaration
│   │   │   ├── PrintStmt.java    # Print statement
│   │   │   ├── IfStmt.java       # If-then-else statement
│   │   │   ├── WhileStmt.java    # While loop statement
│   │   │   ├── NewStmt.java      # Heap allocation
│   │   │   ├── WriteHeapStmt.java# Heap write
│   │   │   ├── OpenRFileStmt.java# Open file for reading
│   │   │   ├── ReadFileStmt.java # Read from file
│   │   │   └── CloseRFileStmt.java# Close file
│   │   ├── types/                # Type definitions
│   │   │   ├── IntType.java
│   │   │   ├── BoolType.java
│   │   │   ├── StringType.java
│   │   │   └── RefType.java
│   │   └── values/               # Value representations
│   │       ├── IntValue.java
│   │       ├── BoolValue.java
│   │       ├── StringValue.java
│   │       └── RefValue.java
│   ├── repository/
│   │   ├── IRepository.java      # Repository interface
│   │   └── Repository.java       # State storage and logging
│   └── view/
│       └── textUI/               # Text-based user interface
│           ├── TextMenu.java     # Menu system
│           ├── Command.java      # Command base class
│           ├── RunExample.java   # Run program command
│           └── ExitCommand.java  # Exit command
├── test.in                       # Sample input file for file operations
└── README.md
```

## Prerequisites

Before running the Toy Interpreter, ensure you have the following installed:

1. **Java Development Kit (JDK)**: Version 17 or higher
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify installation by running:
     ```bash
     java -version
     javac -version
     ```

2. **IDE (Recommended)**: IntelliJ IDEA, Eclipse, or any Java IDE
   - The project includes IntelliJ IDEA configuration files

## Installation and Setup

### Option 1: Using IntelliJ IDEA (Recommended)

1. **Clone or download the repository**:
   ```bash
   git clone https://github.com/co815/Toy-Interpreter.git
   ```
   Or download and extract the ZIP file.

2. **Open the project in IntelliJ IDEA**:
   - Launch IntelliJ IDEA
   - Select "Open" and navigate to the project folder
   - Select the "Toy Interpreter" directory and click "OK"

3. **Configure the project SDK**:
   - Go to File > Project Structure
   - Under Project Settings > Project, set the SDK to JDK 17 or higher
   - Set the language level to match your JDK version
   - Click "Apply" and "OK"

4. **Mark the source folder**:
   - Right-click on the `src` folder
   - Select "Mark Directory as" > "Sources Root"

### Option 2: Using Command Line

1. **Clone or download the repository**:
   ```bash
   git clone https://github.com/co815/Toy-Interpreter.git
   cd "Toy Interpreter"
   ```

2. **Compile the source files**:
   ```bash
   # Create output directory
   mkdir -p out

   # Compile all Java files
   javac -d out src/*.java src/**/*.java src/**/**/*.java
   ```

3. **Run the interpreter**:
   ```bash
   java -cp out Interpreter
   ```

## Running the Interpreter

### Running from IntelliJ IDEA

1. Open `src/Interpreter.java`
2. Right-click inside the file and select "Run 'Interpreter.main()'"
3. Alternatively, click the green play button next to the `main` method

### Running from Command Line

After compilation, run:
```bash
java -cp out Interpreter
```

### What Happens When You Run

The `Interpreter.java` file contains 9 predefined example programs. When executed, each program runs automatically and logs its execution steps to corresponding log files (log1.txt through log9.txt).

## Language Syntax

### Variable Declaration
```
int variableName;
bool variableName;
string variableName;
Ref int variableName;       // Reference to int
Ref Ref int variableName;   // Reference to reference to int
```

### Assignment
```
variableName = expression;
```

### Arithmetic Operations
```
expression1 + expression2   // Addition
expression1 - expression2   // Subtraction
expression1 * expression2   // Multiplication
expression1 / expression2   // Division
```

### Relational Operations
```
expression1 < expression2
expression1 <= expression2
expression1 == expression2
expression1 != expression2
expression1 > expression2
expression1 >= expression2
```

### Control Flow
```
// If-Then-Else
if (condition) then statement1 else statement2

// While Loop
while (condition) statement
```

### Heap Operations
```
new(variableName, expression);      // Allocate on heap
rH(expression);                     // Read from heap
wH(variableName, expression);       // Write to heap
```

### File Operations
```
openRFile(expression);                     // Open file for reading
readFile(expression, variableName);        // Read integer from file
closeRFile(expression);                    // Close file
```

### Print Statement
```
print(expression);
```

## Example Programs

Here are the predefined examples included in `Interpreter.java`:

### Example 1: Basic Variable Usage
```
int v;
v = 2;
print(v);
```
Output: 2

### Example 2: Arithmetic Operations
```
int a;
int b;
a = 2 + 3 * 5;
b = a + 1;
print(b);
```
Output: 18

### Example 3: Conditional Statement
```
bool a;
int v;
a = true;
if a then v = 2 else v = 3;
print(v);
```
Output: 2

### Example 4: File Operations
```
string varf;
varf = "test.in";
openRFile(varf);
int varc;
readFile(varf, varc);
print(varc);
readFile(varf, varc);
print(varc);
closeRFile(varf);
```
Note: Requires a `test.in` file with integer values.

### Example 5: Relational Expression
```
int a;
a = 10;
int b;
b = 12;
bool c;
c = (a < b);
print(c);
```
Output: true

### Example 6: Heap Allocation and Reading
```
Ref int v;
new(v, 20);
Ref Ref int a;
new(a, v);
print(v);
print(a);
print(rH(v));
print(rH(rH(a)) + 5);
```

### Example 7: Heap Writing
```
Ref int v;
new(v, 20);
print(rH(v));
wH(v, 30);
print(rH(v) + 5);
```

### Example 8: While Loop
```
int v;
v = 4;
while (v > 0) {
    print(v);
    v = v - 1;
}
print(v);
```
Output: 4, 3, 2, 1, 0

### Example 9: Garbage Collection Demo
```
Ref int v;
new(v, 20);
Ref Ref int a;
new(a, v);
new(v, 30);
print(rH(rH(a)));
```

## Understanding the Output

### Log Files

Each example program generates a log file (log1.txt, log2.txt, etc.) that shows the step-by-step execution:

- **ExeStack**: The current execution stack (statements to be executed)
- **SymTable**: The symbol table (variable names and their values)
- **Out**: The output list (printed values)
- **FileTable**: Currently open files
- **Heap**: The heap memory contents

### Sample Log Output

```
ExeStack:
print(v)
SymTable:
v --> 2
Out:
2
FileTable:

Heap:
```

## Extending the Interpreter

To add new features to the interpreter:

1. **New Statement**: Create a class implementing `IStmt` in `src/model/statements/`
2. **New Expression**: Create a class implementing `IExp` in `src/model/expressions/`
3. **New Type**: Create a class implementing `IType` in `src/model/types/`
4. **New Value**: Create a class implementing `IValue` in `src/model/values/`

Each new component should implement the required interface methods, particularly `execute()` for statements and `eval()` for expressions.

## Troubleshooting

### Common Issues

1. **"Class not found" error**:
   - Ensure all files are compiled correctly
   - Check that the classpath includes the output directory

2. **"File not found" error for test.in**:
   - Make sure `test.in` exists in the project root directory
   - The file should contain integer values, one per line

3. **Compilation errors**:
   - Verify you are using JDK 17 or higher
   - Ensure the `src` folder is marked as the source root

4. **Log files not created**:
   - Check that you have write permissions in the project directory

## License

This project is distributed under the MIT License. See the LICENSE file for details.
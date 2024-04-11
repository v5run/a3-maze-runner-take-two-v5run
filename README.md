# Assignment A3 - Maze Runner (again)

* **Student**: [VARUN PATHAK](pathav4@mcmaster.ca)
* **Program**: B. Eng. In Software Engineering
* **Course code**: SFWRENG 2AA4
* **Course Title**: Software Design I - Introduction to Software Development
* Term: *Level II - Winter 2024*

## **Credit**: [Alexandre Lachance](alexandrelachance@me.com) and [Dr. Jelle Hellings](jhellings@mcmaster.ca)

### The two people mentioned are referenced as they helped in creating the solution provided. Alexandre provided the base solution of the project, whereas Dr. Hellings' algorithm's were used and referenced in the culmination of the project. Thanks to them!
#### Content Used:
- (9) Graphs: Elementary Graph Algorithms, Winter 2024, SFWRENG 2C03, McMaster University
- (10) Graph Algorithms: Minimal Spanning Trees and Shortest Paths, Winter 2024, SFWRENG 2C03, McMaster University

## My Contribution

`-method BFS` results in the provided maze to be solved with the Breadth-First graph algorithm

```
varun@DESKTOP-B1M8S22:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -method bfs -i ./examples/tiny.ma
z.txt
3F L 4F R 3F
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```

If `-baseline XXX -method YYY` is called then the speedup and runtimes of the algorithms `XXX` in terms of `YYY` are provided

```
varun@DESKTOP-B1M8S22:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -baseline righthand -method bfs -i ./examples/straight.maz.txt
Maze elapsed-time: 3ms
-baseline algorithm time: 4ms
-method algorithm time: 2ms
4F
Speedup: path_baseline/path_method = 1.00
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```

## Business Logic Specification

This program explores a maze, finding a path from an entry point to an exit one.

- The maze is stored in a text file, with `#` representing walls and `␣` (_empty space_) representing passages.
- You’ll find examples of such mazes in the [`examples`](./examples) directory.
    - You can also use the [Maze Generator](https://github.com/ace-lectures/maze-gen) to generate others.
- The Maze is surrounded by walls on its four borders, except for its entry/exit points.
    - Entry and exit points are always located on the East and West border.
    - The maze is not directed. As such, exit and entry can be interchanged.
- At the beginning of the exploration, we're located on the entry tile, facing the opposite side (e.g., if entering by
  the eastern entry, you're facing West).
- The program generates a sequence of instructions to reach the opposite exit (i.e., a "path"):
    - `F` means 'move forward' according to your current direction
    - `R` means 'turn right' (does not move, just change direction), and `L` means ‘turn left’.
- A canonical path contains only `F`, `R` and `L` symbols
- A factorized path squashes together similar instructions (i.e., `FFF` = `3F`, `LL` = `2L`).
- Spaces are ignored in the instruction sequence (only for readability: `FFLFF` = `FF L FF`)
- The program takes as input a maze and print the path on the standard output.
    - For this assignment, the path does not have to be the shortest one.
- The program can take a path as input and verify if it's a legit one.

The following project has **three methods of solving a maze!** This includes ...
-     RightHand Algorithm -> Stuck in a maze? Put your right hand on wall and follow it out.
-     Tremaux's Algorithm -> Been there? Done that! Mark a path and forget about it.
-     Breadth-First Search -> turning the Maze -> Graph w/ Nodes and Edges to solve!

## How to run this software?

To build the program, simply package it with Maven:

```
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$ mvn -q clean package 
```

### Provided version (starter code)

The starter code assumes the maze file name when -i is provided in the command line.

```
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar ./examples/small.maz.txt
** Starting Maze Runner
**** Reading the maze from file ./examples/small.maz.txt
WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL 
WALL PASS PASS PASS PASS PASS PASS PASS PASS PASS WALL 
WALL WALL WALL PASS WALL WALL WALL PASS WALL WALL WALL 
WALL PASS PASS PASS PASS PASS WALL PASS PASS PASS WALL 
WALL PASS WALL PASS WALL WALL WALL WALL WALL PASS WALL 
WALL PASS WALL PASS PASS PASS PASS PASS WALL PASS PASS 
WALL WALL WALL PASS WALL PASS WALL WALL WALL WALL WALL 
WALL PASS PASS PASS WALL PASS PASS PASS PASS PASS WALL 
PASS PASS WALL PASS WALL PASS WALL WALL WALL PASS WALL 
WALL PASS WALL PASS WALL PASS WALL PASS PASS PASS WALL 
WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL 
**** Computing path
PATH NOT COMPUTED
** End of MazeRunner
```

When called on a non-existing file. it prints an error message

```
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar ./examples/small.maz.txtd
** Starting Maze Runner
**** Reading the maze from file ./examples/small.maz.txtd
/!\ An error has occured /!\
**** Computing path
PATH NOT COMPUTED
** End of MazeRunner
```

### Delivered version

#### Command line arguments

The delivered program at the end of this assignment should use the following flags:

- `-i MAZE_FILE`: specifies the filename to be used;
- `-p PATH_SEQUENCE`: activates the path verification mode to validate that PATH_SEQUENCE is correct for the maze
- `-method method`: specifies the method you wish to run;
- `-baseline method`: specifies the method you wish to run a baseline test to compare -method with;


If you are also delivering the bonus, your program will react to a third flag:

- `-method {tremaux, righthand}`: specifies which path computation method to use. (default is right hand)

#### Examples

When no logs are activated, the programs only print the computed path on the standard output.

```
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -i ./examples/straight.maz.txt
4F
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```

If a given path is correct, the program prints the message `correct path` on the standard output.

```
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 4F
correct path
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```

If a given path is incorrect, the program prints the message `incorrect path` on the standard output.

```
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 3F
inccorrect path
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```

If `-baseline` is called then the speedup and runtimes of the algorithms are provided

```
varun@DESKTOP-B1M8S22:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -baseline righthand -method bfs -i ./examples/straight.maz.txt
Maze elapsed-time: 3ms
-baseline algorithm time: 4ms
-method algorithm time: 2ms
4F
Speedup: path_baseline/path_method = 1.00
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```

`-method BFS` results in the provided maze to be solved with the Breadth-First graph algorithm

```
varun@DESKTOP-B1M8S22:~/a3-maze-runner-take-two-v5run$ java -jar target/mazerunner.jar -method bfs -i ./examples/tiny.ma
z.txt
3F L 4F R 3F
pathav4@LAPTOP:~/a3-maze-runner-take-two-v5run$
```


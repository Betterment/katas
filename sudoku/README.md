### Problem Description

"The objective of Sudoku puzzles is to replace the blanks (or zeros) in a 9 by 9 grid in such that each row, column, and 3 by 3 box contains each of the digits 1 to 9. Below is an example of a typical starting puzzle grid and its solution grid.

"A well constructed Sudoku puzzle has a unique solution and can be solved by logic, although it may be necessary to employ "guess and test" methods in order to eliminate options (there is much contested opinion over this). The complexity of the search determines the difficulty of the puzzle." - <a href="http://projecteuler.net/index.php?section=problems&id=96">Project Euler</a>

#### Objectives

Sudoku can obviously be solved by pure guess and check, regardless of the logical difficulty. This is forbidden.[1]

This kata has several goals:
1) Solve a problem for which TDD is unsuitable; a puzzle is either correct or incorrect.
2) Allow flexibility is data structures by providing puzzles as text files.
3) Promote diversity in implementations and encourage unique techniques.
4) Work in small teams!

Most importantly, think creatively about algorithms and techniques. Does your solution perform differently for puzzles of varied logical difficulty? What trade-offs did you consider in your design?

I will ask two people to talk for 5 minutes about their solution -- have fun!

[1] OK, fine. For some puzzles you might need to guess and check. But that's not very interesting, is it?
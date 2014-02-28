#!/usr/bin/env ruby

require './sudoku_solver'

SudokuSolver.new(ARGV[0]).solve

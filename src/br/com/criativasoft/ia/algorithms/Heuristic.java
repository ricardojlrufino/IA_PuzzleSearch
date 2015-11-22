package br.com.criativasoft.ia.algorithms;

import br.com.criativasoft.ia.puzzle.SolutionAction;

public interface Heuristic {
    public double calculate(Problem problem, Problem solution);
}

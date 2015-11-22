package br.com.criativasoft.ia.puzzle.model;

import java.util.List;

import br.com.criativasoft.ia.algorithms.Heuristic;
import br.com.criativasoft.ia.algorithms.Problem;

/**
 * Heuristica simples que verifica a quantidade de peças na posição correta.
 * @author Ricardo JL Rufino - (ricardo.jl.rufino@gmail.com)
 * @date 21/11/2015
 */
public class BoardHeuristicA implements Heuristic {

    @Override
    public double calculate( Problem problem , Problem solution) {
        double c = 0;
        
        List<Cell> cells1 = ((Board) solution).getCells();
        List<Cell> cells2 = ((Board) problem).getCells();
        
        for (int i = 0; i < cells1.size(); i++) {
            if(cells1.get(i).getValue() == cells2.get(i).getValue()){
                c++;
            }
        }
        
        return c;
    }

}

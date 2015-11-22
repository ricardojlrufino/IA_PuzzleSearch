package br.com.criativasoft.ia.algorithms;

import br.com.criativasoft.ia.algorithms.model.Statistics;
import br.com.criativasoft.ia.algorithms.model.Tree;

public interface Search {
    
    void setProblem(Problem problem);
    
//    void setSolution(Problem solution);

    void setExpected(Problem solution);
    
    boolean execute();
    
    public Tree getTree();
    
    Problem getSolution();
    
    Statistics getStatistics();

}

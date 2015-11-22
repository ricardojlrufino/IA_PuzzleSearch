package br.com.criativasoft.ia.algorithms;

import java.util.ArrayList;
import java.util.List;

import br.com.criativasoft.ia.algorithms.model.Node;
import br.com.criativasoft.ia.algorithms.model.Statistics;
import br.com.criativasoft.ia.algorithms.model.Tree;

public abstract class AbstractSearch implements Search {
    
    protected Problem problem;
    protected Problem expected;
    protected Problem solution;
    protected Tree tree;
    protected Statistics statistics;
    
    public AbstractSearch() {
        statistics = new Statistics();
        tree = new Tree();
    }
    
    @Override
    public void setProblem( Problem problem ) {
        tree.setRootElement(new Node(problem));
        this.problem = problem;
    }
    
    @Override
    public void setExpected( Problem expected ) {
        this.expected = expected;
    }
    
    public void setSolution( Problem solution ) {
        this.solution = solution;
    }
    
    @Override
    public Problem getSolution() {
        return solution;
    }
    
    @Override
    public Statistics getStatistics() {
        return statistics;
    }
    
    @Override
    public Tree getTree() {
        return tree;
    }
    
    protected boolean isSolution( Node node ) {
        if (expected.equals(node.problem)) {
            setSolution(node.problem);
            statistics.end();
            return true;
        }
        return false;
    }
    
    /** Monta a árvore reversa da solução */
    protected void buildSolutionTree(Node found){
        if(found == tree.getRootElement()) return;
        Node parent = found.getParent();
        if(parent == null) return;
        parent.addChild(found);
        buildSolutionTree(parent);
    }
    
    protected List<Node> open( Node current, boolean storeChilds ) {

        List<Problem> derivates = current.getProblem().derivate();
        List<Node> list = new ArrayList<Node>();
        for (Problem problem : derivates) {
            Node node = new Node(problem);
            node.setParent(current);
            statistics.newCreated();
            if(storeChilds) current.addChild(node);
            list.add(node);
        }

        return list;
    }
    
    @Override
    public boolean execute() {
        statistics.start();
        
        if(expected.equals(problem)){
           return true;
        }
        
        return false;
    }


}

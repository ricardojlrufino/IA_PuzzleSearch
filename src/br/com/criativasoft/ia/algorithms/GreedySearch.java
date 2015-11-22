package br.com.criativasoft.ia.algorithms;

import java.util.*;

import br.com.criativasoft.ia.algorithms.model.Node;

/**
 * Busca Gulosa (Heurística) - Busca pela melhor escolha
 * @author Ricardo JL Rufino - (ricardo.jl.rufino@gmail.com)
 * @date 21/11/2015
 */
public class GreedySearch extends AbstractSearch {

    private Heuristic heuristic;

    private PriorityQueue<Node> queue;
    private Set<Node> visited = new HashSet<Node>();

    public GreedySearch(Heuristic heuristic) {
        super();
        this.heuristic = heuristic;
        this.queue = new PriorityQueue<Node>(24, new HeuristComparator());
    }

    @Override
    public boolean execute() {

        queue.add(getTree().getRootElement());

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            statistics.setInstances(queue.size()); 
            
            if (isSolution(current)) {
                System.out.println("SOLUTION FOUND.....");
                buildSolutionTree(current);
                return true;
            }

            if (!visited.contains(current)) {
                visited.add(current);
                statistics.newVisited();
                List<Node> childs = open(current, false);
                for (Node o : childs) {
                    queue.add(o);
                }
            }

        }
        
        return false;

    }

    private class HeuristComparator implements Comparator<Node> {

        @Override
        public int compare( Node o1 , Node o2 ) {

            double v1 = heuristic.calculate(o1.getProblem(), expected);
            double v2 = heuristic.calculate(o2.getProblem(), expected);

            return Double.compare(v2, v1);
        }

    }
}

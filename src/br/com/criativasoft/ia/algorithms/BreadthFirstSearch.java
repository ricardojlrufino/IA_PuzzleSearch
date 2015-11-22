package br.com.criativasoft.ia.algorithms;

import java.util.*;

import br.com.criativasoft.ia.algorithms.model.Node;

/**
 * Busca em Extensão / Largura
 * @author Ricardo JL Rufino (ricardo@criativasoft.com.br) 
 * @date 22/11/2015
 */
public class BreadthFirstSearch extends AbstractSearch implements BlindSearch {
    
    private Queue<Node> queue = new LinkedList<Node>();
    private Set<Node> visited = new HashSet<Node>();

    @Override
    public boolean execute() {
        
        super.execute();
        
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
    

}

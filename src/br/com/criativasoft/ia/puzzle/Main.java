package br.com.criativasoft.ia.puzzle;

import java.util.List;

import br.com.criativasoft.ia.algorithms.*;
import br.com.criativasoft.ia.algorithms.model.Node;
import br.com.criativasoft.ia.algorithms.model.Statistics;
import br.com.criativasoft.ia.algorithms.model.Tree;
import br.com.criativasoft.ia.puzzle.model.Board;
import br.com.criativasoft.ia.puzzle.model.BoardHeuristicA;

/**
 * Source: https://github.com/ricardojlrufino/IA_PuzzleSearch
 * @author Ricardo JL Rufino (ricardo@criativasoft.com.br) 
 * @date 22/11/2015
 */
public class Main {
    
    public static void main( String[] args ) {
        
//        int[] initial = {
//                1, 4, 2,
//                3, 0, 5,
//                6, 7, 8
//        };
        
        
//        int[] initial = {
//                7, 2, 4,
//                5, 0, 6,
//                8, 3, 1
//        };
//
//        int[] solution = {
//                0, 1, 2,
//                3, 4, 5,
//                6, 7, 8
//        };
        
        
        int[] initial = {
                8, 4, 0, 5, 1, 7, 6, 2, 3
        };

        int[] expected = {
                0, 1, 2, 3, 4, 5, 6, 7, 8 
        };
        
        SearchType searchType = SearchType.Greedy;
        Search search = null;
         
        switch (searchType) {
        case BreadthFirst:
            search = new BreadthFirstSearch();
            break;
        case Greedy:
            search = new GreedySearch(new BoardHeuristicA());
            break;
        default:
            break;
        }
        
        search.setProblem(Board.build(initial, 3, 3));
        search.setExpected(Board.build(expected, 3, 3));
        
        if(search.execute()){
            Problem solution2 = search.getSolution();
            Tree tree = search.getTree();
            List<Node> list = tree.toList();
            
            Statistics statistics = search.getStatistics();
            
            System.out.println("Statistics: ");
            System.out.println("=======================");
            System.out.println("ElapsedTime: " + statistics.getElapsedTime());
            System.out.println("Visited: " + statistics.getVisited());
            System.out.println("Instances: " + statistics.getInstances());
            System.out.println("Created: " + statistics.getCreated());
            
            System.out.println("Childs : " + list.size());
            for (Node node : list) {
                System.out.println(node.problem);
            } 
        }
    }

}

package br.com.criativasoft.ia.puzzle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
         
    static int[] initial = {
            8, 4, 0, 
            5, 1, 7, 
            6, 2, 3
    };

    static int[] expected = {
            0, 1, 2, 
            3, 4, 5, 
            6, 7, 8 
    };
        
    public static void main( String[] args ) {
        
        while(true){
            System.out.println();
            System.out.println("=====================================");
            System.out.println("Puzzle game");
            System.out.println("=====================================");
            System.out.println("Escolha o Algoritmo:");
            SearchType[] searchTypes = SearchType.values();
            for (int i = 0; i < searchTypes.length; i++) {
                System.out.println((i+1) +") " + searchTypes[i]);
            }
            System.out.println("q) Sair");
            
            SearchType searchType = null;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                
                String string = in.readLine();
                if(string.contains("q"))  System.exit(0);
                int option = Integer.parseInt(string); 
                if ((option < 1) || (option > SearchType.values().length)) {
                    System.out.println("Opção Inválida / SAIR !");
                   
                }
                searchType = SearchType.values()[option-1];
                
                run(searchType);
                
            }catch(Exception ex){ ex.printStackTrace(); System.exit(-1);}     
        }
        
    }
    
    public static void run(SearchType searchType){
        Search search = null;
        switch (searchType) {
        case BreadthFirst:
            search = new BreadthFirstSearch(); // Busca em Largura
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
            
            for (Node node : list) {
                System.out.println(node.problem);
            } 
            
            System.out.println("Statistics: ");
            System.out.println("=======================");
            System.out.println("ElapsedTime: " + statistics.getElapsedTime()+ " ms");
            System.out.println("Visited: " + statistics.getVisited());
            System.out.println("Instances: " + statistics.getInstances());
            System.out.println("Created: " + statistics.getCreated());
            System.out.println("Childs : " + list.size());
        }
    }

}

package br.com.criativasoft.ia.puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import br.com.criativasoft.ia.puzzle.model.Board;
import br.com.criativasoft.ia.puzzle.model.Board.MoveAction;
import br.com.criativasoft.ia.puzzle.model.Cell;

/**
 * Source: https://github.com/ricardojlrufino/IA_PuzzleSearch
 * @author Ricardo JL Rufino (ricardo@criativasoft.com.br) 
 * @date 22/11/2015
 */
public class MainInteractive {
    
    static int[] initial = {
            7, 2, 4,
            5, 0, 6,
            8, 3, 1
    };

    static  int[] solution = {
            0, 1, 2,
            3, 4, 5,
            6, 7, 8
    };
    
    public static void main( String[] args ) throws IOException {
        
        Board initalBoard = Board.build(initial, 3, 3);
        Board solutionBoard = Board.build(solution, 3, 3);
        
        System.out.println(initalBoard);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int key = 0;
        
        Cell emptyCell = initalBoard.getEmptyCell();
        
        while(!solutionBoard.equals(initalBoard) || key == 'q') {
            
            System.out.print("\nFaça o movimento [w/a/s/d]: ");
            key = in.read();
            in.readLine();
            
            switch (key) {
            case 'w':
                System.err.println("Move: TOP");
                initalBoard.move(emptyCell, MoveAction.TOP);
                break;
            case 'a':
                System.err.println("Move: LEFT");
                initalBoard.move(emptyCell, MoveAction.LEFT);
                break;
            case 's':
                System.err.println("Move: DOWN");
                initalBoard.move(emptyCell, MoveAction.DOWN);
                break;
            case 'd':
                System.err.println("Move: RIGHT");
                initalBoard.move(emptyCell, MoveAction.RIGHT);
                break;

            default:
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            
            System.out.println(initalBoard);
            // clear state
            List<Cell> cells = initalBoard.getCells();
            for (Cell cell : cells) {
                cell.setMoved(false);
            }
        }
    }
}

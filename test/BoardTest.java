import junit.framework.TestCase;

import org.junit.Test;

import br.com.criativasoft.ia.puzzle.model.Board;
import br.com.criativasoft.ia.puzzle.model.Board.MoveAction;
import br.com.criativasoft.ia.puzzle.model.Cell;


public class BoardTest extends TestCase  {
    Board initalBoard;
    Board solutionBoard;
    
    int[] initial = {
            7, 2, 4,
            5, 0, 6,
            8, 3, 1
    };

    int[] solution = {
            0, 1, 2,
            3, 4, 5,
            6, 7, 8
    };
    
    @Override
    protected void setUp() throws Exception {
        initalBoard = Board.build(initial, 3, 3);
        solutionBoard = Board.build(solution, 3, 3);
    }
    
    @Test
    public void testMoveTOP() throws Exception {
        System.out.println("-- TOP --");
        Cell emptyCell = initalBoard.getEmptyCell();
        int x = emptyCell.getX();
        int y = emptyCell.getY();
        initalBoard.move(emptyCell, MoveAction.TOP);
        initalBoard.print();
        
        Cell target = initalBoard.getCell(x, y);
        assertEquals(2, target.getValue());
        
    }
    
    @Test
    public void testMoveDOWN() throws Exception {
        System.out.println("-- DOWN --");
        Cell emptyCell = initalBoard.getEmptyCell();
        int x = emptyCell.getX();
        int y = emptyCell.getY();
        initalBoard.move(emptyCell, MoveAction.DOWN);
        initalBoard.print();
        
        Cell target = initalBoard.getCell(x, y);
        assertEquals(3, target.getValue());
        
    }
    
    @Test
    public void testMoveLEFT() throws Exception {
        System.out.println("-- LEFT --");
        Cell emptyCell = initalBoard.getEmptyCell();
        int x = emptyCell.getX();
        int y = emptyCell.getY();
        initalBoard.move(emptyCell, MoveAction.LEFT);
        initalBoard.print();
        
        Cell target = initalBoard.getCell(x, y);
        assertEquals(5, target.getValue());
        
    }
    
    @Test
    public void testMoveRIGHT() throws Exception {
        System.out.println("-- RIGHT --");
        Cell emptyCell = initalBoard.getEmptyCell();
        int x = emptyCell.getX();
        int y = emptyCell.getY();
        initalBoard.move(emptyCell, MoveAction.RIGHT);
        initalBoard.print();
        
        Cell target = initalBoard.getCell(x, y);
        assertEquals(6, target.getValue());
        
    }
    
    @Test
    public void testMoveRIGHT2() throws Exception {
        System.out.println("-- RIGHT2 --");
        Cell emptyCell = initalBoard.getEmptyCell();
        int x = emptyCell.getX();
        int y = emptyCell.getY();
        initalBoard.move(emptyCell, MoveAction.RIGHT);
        initalBoard.print();
        initalBoard.move(emptyCell, MoveAction.RIGHT);
        initalBoard.print();
        
        Cell target = initalBoard.getCell(x, y);
        assertEquals(6, target.getValue());
    }
    
    @Test
    public void testMoveTopLeft() throws Exception {
        System.out.println("-- Sequence --");
        Cell emptyCell = initalBoard.getEmptyCell();
        int x = emptyCell.getX();
        int y = emptyCell.getY();
        initalBoard.move(emptyCell, MoveAction.TOP);
        initalBoard.print();
        initalBoard.move(emptyCell, MoveAction.LEFT);
        initalBoard.print();
        
        Cell target = initalBoard.getCell(x, y);
        assertEquals(2, target.getValue());
    }
    
    @Test
    public void testDerivate() throws Exception {
        System.out.println("-- initalBoard --");
        initalBoard.print();
        assertEquals(4, initalBoard.derivate().size());
    }

    @Test
    public void testSolution() throws Exception {
        assertFalse(solutionBoard.equals(initalBoard));
        assertTrue(solutionBoard.equals(Board.build(solution, 3, 3)));
    }

}

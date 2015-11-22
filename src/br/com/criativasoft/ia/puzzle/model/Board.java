package br.com.criativasoft.ia.puzzle.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.com.criativasoft.ia.algorithms.Problem;

/**
 * @author Ricardo JL Rufino (ricardo@criativasoft.com.br) 
 * @date 22/11/2015
 */
public class Board implements Problem, Cloneable{
    
    private List<Cell> cells;
    
    private int rows;
    
    private int cols;
    
    private MoveAction action;
    
    public enum MoveAction{
        TOP, LEFT, RIGHT, DOWN
    }
    
    public Board(List<Cell> cells, int rows, int cols) {
        super();
        this.cells = cells;
        this.rows = rows;
        this.cols = cols;
    }

    public static Board build(int[] state, int rows, int cols){
        List<Cell> cells = new LinkedList<Cell>();
        int c = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int index = c++;
//                System.out.println(x +"," + y + ": " + index);
                Cell cell = new Cell(state[index], x, y);
                cells.add(cell);
            }
            
        }
        Board board = new Board(cells, rows, cols);
        return board;
    }

     public boolean move(Cell cell, MoveAction direction){
        
        switch (direction) {
        case TOP:
            if(cell.y > 0){
                cell.y--;
                cell.setMoved(true);
                Cell cell2 = getCell(cell.x, cell.y);
                cell2.y++;
                cell2.setMoved(true);
                Collections.sort(cells);
                return true;
            }
            break;
        case RIGHT:
            if(cell.x < cols-1){
                cell.x++;
                cell.setMoved(true);
                Cell cell2 = getCell(cell.x, cell.y);
                cell2.x--;
                cell2.setMoved(true);
                Collections.sort(cells);
                return true;
            }
            break;
        case DOWN:
            if(cell.y < rows-1){
                cell.y++;
                cell.setMoved(true);
                Cell cell2 = getCell(cell.x, cell.y);
                cell2.y--;
                cell2.setMoved(true);
                Collections.sort(cells);
                return true;
            }
            break;
        case LEFT:
            if(cell.x > 0){
                cell.x--;
                cell.setMoved(true);
                Cell cell2 = getCell(cell.x, cell.y);
                cell2.x++;
                cell2.setMoved(true);
                Collections.sort(cells);
                return true;
            }
            break;
        default:
            break;
        }
        
        return false;
        
    }
 
    /**
     * Gera novos sucessores 
     */
    @Override
    public List<Problem> derivate() {
        
        List<Problem> boards = new LinkedList<Problem>();
        
        // Tenta mover o celula vazia em todas as direções, e cria novas instancias do Board
        // apenas onde conseguir fazer o movimento.
        for (MoveAction direction : MoveAction.values()) {
            try {
                Board newBoard = (Board) this.clone();
                Cell empty = newBoard.getEmptyCell();
                if(empty == null) throw new IllegalStateException("Empty cell not found !");
                if(newBoard.move(empty, direction)){
                    newBoard.action = direction;
                    boards.add((Problem) newBoard);
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
       
        }
        
        return boards;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cells == null) ? 0 : cells.hashCode());
        return result;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Board other = (Board) obj;
        if (cells == null) {
            if (other.cells != null) return false;
        } else if (!cells.equals(other.cells)) return false;
        return true;
    }
    
    public List<Cell> getCells() {
        return cells;
    }
    
    public void setCells( List<Cell> cells ) {
        this.cells = cells;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }

    
    public Cell getEmptyCell(){
        
        for (Cell cell : cells) {
            if(cell.getValue() == 0) return cell;
        }
        
        return null;
    }
    
    public Cell getCell(int value){
        for (Cell cell : cells) {
            if(cell.getValue() == value) return cell;
        }
        
        return null;
     }
    
    public Cell getCell(int x, int y){
       return getCell(x, y, true);
    }
    
    public Cell getCell(int x, int y, boolean ignoreEmpty){
        
        for (Cell cell : cells) {
            if(cell.x == x && cell.y == y){
                if(ignoreEmpty){
                    if(cell.getValue() != 0)  return cell;
                }else{
                    return cell;
                }
            }
        }
        
        return null;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board board = (Board) super.clone();
        
        List<Cell> cells = board.getCells();
        List<Cell> listClone = new LinkedList<Cell>();
        
        for (Cell cell : cells) {
            Cell clone = (Cell) cell.clone();
            clone.setMoved(false);
            listClone.add(clone);
        }
        
        board.setCells(listClone);
        
        return board;
    }
    
    
    public void print() {
        System.out.println(toString());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("+-----------+").append('\n');
        int c = 1;
        for (Cell cell : cells) {
            sb.append("|");
            if(cell.getValue() != 0){
                sb.append((cell.isMoved() ? "*" : " "));
                sb.append(cell.getValue());
                sb.append((cell.isMoved() ? "*" : " "));
            }else{
                if(action != null){
                    if(action == MoveAction.TOP) sb.append(" \u25B2 ");
                    if(action == MoveAction.DOWN) sb.append(" \u25BC ");
                    if(action == MoveAction.LEFT) sb.append(" \u25C0 ");
                    if(action == MoveAction.RIGHT) sb.append(" \u25B6 ");
                    
                }else{
                    sb.append("   ");
                }
                
            }

            if(c % cols == 0) sb.append("|\n");
            c++;
        }
        sb.append("+-----------+");
        return sb.toString();
    }

}

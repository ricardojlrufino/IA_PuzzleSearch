package br.com.criativasoft.ia.algorithms.model;

import java.util.ArrayList;
import java.util.List;

import br.com.criativasoft.ia.algorithms.Problem;

/**
 * Represents a node of the Tree class. The Node is also a container, and
 * can be thought of as instrumentation to determine the location of the type T
 * in the Tree.
 */
public class Node {
 
    public Problem problem;
    public List<Node> children;
    private int level = 0;
    private Node parent;
 
    public Node() {
        super();
    }
 
    public Node(Problem data) {
        this();
        setProblem(data);
    }
     
    /**
     * Return the children of Node. The Tree is represented by a single
     * root Node whose children are represented by a List<Node>. Each of
     * these Node elements in the List can have children. The getChildren()
     * method will return the children of a Node.
     * @return the children of Node
     */
    public List<Node> getChildren() {
        if (this.children == null) {
            return new ArrayList<Node>();
        }
        return this.children;
    }
 
    /**
     * Sets the children of a Node object. See docs for getChildren() for
     * more information.
     * @param children the List<Node> to set.
     */
    public void setChildren(List<Node> children) {
        this.children = children;
    }
 
    /**
     * Returns the number of immediate children of this Node.
     * @return the number of immediate children.
     */
    public int getNumberOfChildren() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }
     
    /**
     * Adds a child to the list of children for this Node. The addition of
     * the first child will create a new List<Node>.
     * @param child a Node object to set.
     */
    public void addChild(Node child) {
        if (children == null) {
            children = new ArrayList<Node>();
        }
        children.add(child);
        child.level = this.level + 1;
        child.parent = this;
    }
     
    /**
     * Inserts a Node at the specified position in the child list. Will     * throw an ArrayIndexOutOfBoundsException if the index does not exist.
     * @param index the position to insert at.
     * @param child the Node object to insert.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void insertChildAt(int index, Node child) throws IndexOutOfBoundsException {
        if (index == getNumberOfChildren()) {
            // this is really an append
            addChild(child);
            return;
        } else {
            children.get(index); //just to throw the exception, and stop here
            children.add(index, child);
        }
    }
     
    /**
     * Remove the Node element at index index of the List<Node>.
     * @param index the index of the element to delete.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }
 
    public Problem getProblem() {
        return this.problem;
    }
 
    public void setProblem(Problem data) {
        this.problem = data;
    }
    
    public void setParent( Node parent ) {
        this.parent = parent;
    }
    
    public Node getParent() {
        return parent;
    }
    
    @Override
    public String toString() {
        return problem.toString();
    }
     
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("{").append(getProblem().toString()).append(",[");
//        int i = 0;
//        for (Node e : getChildren()) {
//            if (i > 0) {
//                sb.append(",");
//            }
//            sb.append(e.getProblem().toString());
//            i++;
//        }
//        sb.append("]").append("}");
//        return sb.toString();
//    }
    
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((problem == null) ? 0 : problem.hashCode());
      return result;
    }

    @Override
    public boolean equals( Object obj ) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Node other = (Node) obj;
      if (problem == null) {
        if (other.problem != null) return false;
      } else if (!problem.equals(other.problem)) return false;
      return true;
    }
    
    
}
package br.com.criativasoft.ia.algorithms.model;

public class Statistics {
    
    private long instances;
    private long visited;
    private long created;
    
    private long startTime;
    private long endTime;
    
    public void start() {
        this.startTime = System.currentTimeMillis();
    }
    
    public void end() {
        this.endTime = System.currentTimeMillis();
    }
    
    public void setInstances( long instances ) {
        this.instances = instances;
    }
    
    public void newInstances() {
        this.instances++;
    }
    public void newVisited() {
        this.visited++;
    }
    public void newCreated() {
        this.created++;
    }
    
    public long getElapsedTime(){
        return (endTime - startTime);
    }

    public long getInstances() {
        return instances;
    }

    public long getVisited() {
        return visited;
    }

    public long getCreated() {
        return created;
    }
    
}

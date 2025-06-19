

package main.java;

import main.resources.LinkedQueue;
import main.resources.Queue;



public class Printer {
    private final int printerId;
    private Queue<Job> priorityQueue;
    private Queue<Job> normalQueue;
    
    private  int currentTime;
    private int totalHigh;
    private int totalNormal;
    private StringBuilder sb;
    
    
    
    public static final String EOL = System.lineSeparator();

    /**
     * 
     * @param printerId
     * @param sb
     */
    public Printer(int printerId, StringBuilder sb) {
     this.sb = sb;   
     this.printerId = printerId;
     
     priorityQueue = new LinkedQueue<>();
     normalQueue = new LinkedQueue<>();
     
     
    sb.append("[TIME: 0] Printer "+printerId+" online, ready to take jobs.");
    sb.append("\n");
     
    }

    /**
     * 
     * @return
     */
    public int getPrinterId() {
        return printerId;
    }

    /**
     * 
     * @return
     */
    public int currentTime() {
        return currentTime;
    }

    /**
     * 
     * @return
     */
    public int timeToFinishHigh() {
            
        return totalHigh;
    }

    /**
     * 
     * @return
     */
    public int timeToFinishNormal() {

        return totalNormal;
    }

    /**
     * 
     * @param job
     */
    public void addJob(Job job) {
        if(job.getPriority().name() == "HIGH") {
            priorityQueue.enqueue(job);
            totalHigh+= job.getPrintingDuration();
        }
        else {
            normalQueue.enqueue(job);
            totalNormal+= job.getPrintingDuration();
        }
        
        sb.append("[TIME: "+job.getArrivalTime()+"] Job "+job.getModelName()+" ("+
                job.getPriority().name()+") scheduled to printer "+getPrinterId()+
                 ", printing will take "+job.getPrintingDuration()+".");
                sb.append("\n");
            
        job.setStatus(Status.WAITING);
    }

    /**
     * 
     * @return
     */
    public Job currentJob() {
      
        
       if(priorityQueue.size()!=0 && (normalQueue.size()==0 || normalQueue.front().getStatus() != Status.PRINTING )) {
           return priorityQueue.front();
       }
       else if(normalQueue.size()!=0) {   
           return normalQueue.front();
       }
       else {
           return null;
       }
    }

    /**
     * 
     * @param duration
     * @requires duration <= currentJob().getPrintingDuration()
     */
    public void printCurrentJob(int duration) {
        if(isEmpty()) {
            return;
        }
        
        //int time = currentJob().getModelPrintTime();
        //int num = (duration/time);
        if(duration == currentJob().getPrintingDuration() ) {
         
            finishCurrentJob();
            
        }
        else {
        currentJob().setPrintingDuration(currentJob().getPrintingDuration()-duration);
        currentJob().setStatus(Status.PRINTING);
        if(currentJob().getPriority() == Priority.HIGH) {
            totalHigh -= duration;
        }
        else {
            totalNormal -= duration;
        }
        
       currentTime += duration;
       }
    }

    /**
     * @requires !isEmpty()
     */
    public void finishCurrentJob() {
        
      
        currentJob().setStatus(Status.FINISHED);
        currentTime += currentJob().getPrintingDuration();
        
        sb.append("[TIME: "+currentTime+"] Job "+currentJob().getModelName()+
                " has finished printing. Total wait time: "+
               (currentTime-currentJob().getArrivalTime())+".");
      sb.append("\n");
        
        if(currentJob().getPriority()==Priority.HIGH) {
            totalHigh -= currentJob().getPrintingDuration();
            priorityQueue.dequeue();
        }
        else {
            totalNormal -= currentJob().getPrintingDuration();
            normalQueue.dequeue();
        }
        
            
        
      

  
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
        return (priorityQueue.size()==0 && normalQueue.size()==0);
    }

    /**
     * 
     * @param duration
     */
    public void printForDuration(int duration) {
        
        while(!isEmpty() && duration!=0) {
            int time = currentJob().getPrintingDuration();
            if(duration >= time) {
            printCurrentJob(time);
            duration-= time;
            }
            else {
                printCurrentJob(duration);
                duration=0;
            }         
        }
        if(isEmpty()) {
            currentTime+= duration;
            
        }
        
    }

    /**
     * 
     * @param time
     */
    public void printUntilTime(int time) {
        int duration = time-currentTime;
        printForDuration(duration);
     
    }
}


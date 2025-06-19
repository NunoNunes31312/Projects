package main.java;

/**
 * Classe que representa um pedido de impressão de um número de cópias de um
 * modelo 3D.
 * 
 * @author EquipaLabP2425
 */
public class Job {

    private final String modelName;
    private final int modelPrintTime;
    private final int numberCopies;
    private final int arrivalTime;
    private int printingDuration;
    private final Priority priority;
    private Status status;

    /**
     * Construtor da classe Job
     * 
     * @param modelName
     * @param modelPrintTime
     * @param numberCopies
     * @param arrivalTime
     * @param priority
     */
    public Job(String modelName, int modelPrintTime, int numberCopies, int arrivalTime, Priority priority) {
        this.modelName = modelName;
        this.modelPrintTime = modelPrintTime;
        this.numberCopies = numberCopies;
        this.arrivalTime = arrivalTime;
        this.printingDuration = modelPrintTime*numberCopies;
        this.priority = priority;
        this.status = Status.WAITING;
    }

    /**
     * @return the arrivalTime
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @return the printingDuration
     */
    public int getPrintingDuration() {
        return printingDuration;
    }

    /**
     * @param printingDuration the printingDuration to set
     */
    public void setPrintingDuration(int printingDuration) {
        this.printingDuration = printingDuration;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @return the modelPrintTime
     */
    public int getModelPrintTime() {
        return modelPrintTime;
    }

    /**
     * @return the numberCopies
     */
    public int getNumberCopies() {
        return numberCopies;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }



}

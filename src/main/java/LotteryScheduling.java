import java.util.*;

public class LotteryScheduling {
    private List<Process> processes;
    private List<Process> waitingProcesses;
    private Process currentProcess;
    private int finishedNumber;
    private int time;

    public LotteryScheduling() {
        processes = new ArrayList<>();
        waitingProcesses = new ArrayList<>();
        finishedNumber = 0;
    }
    public void createProcesses(int numberOfProcesses, int maxArrivalTime, int minBurstTime,  int maxBurstTime){
        for (int i = 0; i < numberOfProcesses; ++i) {
            processes.add(Process.createProcess(maxArrivalTime, minBurstTime, maxBurstTime));
        }
    }
    public void printProcesses(){
        System.out.printf("%15s %15s %13s %n", "Process", "Arrival Time", "Burst Time");
        for (Process process : processes) {
            System.out.printf("%10s %10s %15s %n", process, process.getArrivalTime(), process.getInitialBurstTime());
        }
    }
    private void arriveNewProcesses(){
        for (Process process : processes) {
            if (process.getArrivalTime() == time) {
                waitingProcesses.add(process);
                System.out.println("Process" + process + " arrived");
            }
        }
    }

    private long getTickets(){
        long ticket = 0;
        if(currentProcess != null) {
            ticket = currentProcess.setTickets(ticket);
        }
        for(int i = 0; i < waitingProcesses.size(); ++i){
            ticket = waitingProcesses.get(i).setTickets(ticket);
        }
        printTickets();
        return ticket;
    }

    private void printTickets(){
        System.out.println("Tickets");
        if(currentProcess != null) {
            System.out.print(currentProcess + ": ");
            System.out.print(currentProcess.getTickets());
            System.out.print("\n");
        }
        for (int i = 0; i < waitingProcesses.size(); i++) {
            System.out.print(waitingProcesses.get(i) + ": ");
            System.out.print(waitingProcesses.get(i).getTickets());
            System.out.print("\n");
        }
    }
    private boolean lottery(){
        if(waitingProcesses.size() == 0){
            System.out.println(currentProcess + " win without competition");
            return false;
        }
        Process previous = currentProcess;
        Process next = null;
        long maxTicket = getTickets();
        long winner = new Random().nextLong(maxTicket) + 1;
        System.out.println("Winner " + winner);
        if(currentProcess != null && currentProcess.getTickets().get(0) <= winner &&
                winner <= currentProcess.getTickets().get(currentProcess.getTickets().size() - 1)){
            return false;
        }
        for(int i = 0; i < waitingProcesses.size(); ++i){
            if(waitingProcesses.get(i).getTickets().get(0) <= winner &&
                    winner <= waitingProcesses.get(i).getTickets().get(waitingProcesses.get(i).getTickets().size() - 1)){
                next = waitingProcesses.get(i);
                break;
            }
        }
        if(next != null){
            if(currentProcess != null) {
                waitingProcesses.add(currentProcess);
            }
            currentProcess = next;
            waitingProcesses.remove(next);
        }
        return previous != currentProcess;
    }

    public void run() {
        time = 0;
        int previousTime = 0;
        while (finishedNumber != processes.size()) {
            arriveNewProcesses();
            lottery();
            ++time;
            if (currentProcess != null) {
                currentProcess.run();
                System.out.println("Process " + currentProcess + " start executing");
                print(previousTime);
                previousTime = time;
                if (currentProcess.getRemainingBurstTime() == 0){
                    System.out.println("The process " + currentProcess + " finished its execution");
                    ++finishedNumber;
                    currentProcess = null;
                }
            }
        }
        System.out.println("All processes finished their execution");
    }
    private void print(int previousTime){
        System.out.println("Time " + previousTime + ":" + time);
        System.out.printf("%30s %15s %22s %22s %n", "Process", "Arrival Time", "Initial Burst Time",
                "Remaining Burst Time");
        if(currentProcess != null){
            System.out.printf("%10s %13s %10s %16s %20s %n", "Current", currentProcess,
                    currentProcess.getArrivalTime(), currentProcess.getInitialBurstTime(),
                    currentProcess.getRemainingBurstTime());
        }
        for (Process e : waitingProcesses) {
            System.out.printf("%10s %13s %10s %16s %20s %n","Waiting", e, e.getArrivalTime(),
                    e.getInitialBurstTime(), e.getRemainingBurstTime());
        }
        System.out.println("==============================================================");

    }
}

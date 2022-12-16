import java.util.ArrayList;
import java.util.List;

public class Process {
    private final int number;
    private static int nextProcessNumber;
    private final int arrivalTime;
    private final int initialBurstTime;
    private int remainingBurstTime;
    private List<Long> tickets;

    public Process(int arrivalTime, int burstTime ) {
        this.number = ++nextProcessNumber;
        this.arrivalTime = arrivalTime;
        this.initialBurstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.tickets = new ArrayList<>();
    }
    public static Process createProcess(int maxArrivalTime, int minBurstTime, int maxBurstTime) {
        return new Process(
                (int)(Math.random() * maxArrivalTime),
                (int)(Math.random() * (maxBurstTime - minBurstTime) + minBurstTime));
    }

    public long setTickets(long ticket){
        tickets = new ArrayList<>();
        long ticketsNumber = (remainingBurstTime + 1) / 2;
        for (int j = 0; j < ticketsNumber; ++j) {
            tickets.add(++ticket);
        }
        return ticket;
    }

    public List<Long> getTickets() {
        return tickets;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getInitialBurstTime() {
        return initialBurstTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void run(){
        if(remainingBurstTime > 0)
            --remainingBurstTime;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}

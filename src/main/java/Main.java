public class Main {
    public static void main(String[] args) {
        LotteryScheduling lotteryScheduling = new LotteryScheduling();

        lotteryScheduling.createProcesses(5, 10, 1, 10);

        lotteryScheduling.printProcesses();
        lotteryScheduling.run();
    }
}

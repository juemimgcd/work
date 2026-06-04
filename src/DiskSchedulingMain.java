import java.util.List;

public class DiskSchedulingMain {
    private static final int REQUEST_COUNT = 10;
    private static final int START_POSITION = 100;

    public static void main(String[] args) {

        List<Integer> requests = RequestGenerator.generateRequests(REQUEST_COUNT);
        System.out.println("初始磁头位置: " + START_POSITION);
        System.out.println("请求序列: " + requests);


        SchedulingAlgorithm[] algorithms = {
                new FIFOScheduler(),
                new SSTFScheduler(),
                new SCANScheduler(),
                new LOOKScheduler(),
                new CSCANScheduler()

        };


        for (SchedulingAlgorithm algorithm : algorithms) {
            SchedulingResult result = algorithm.execute(START_POSITION, requests);
            System.out.println("\n" + result);
        }
    }
}
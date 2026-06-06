import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RequestGenerator {
    private static final int MAX_TRACK = 199;
    
    public static List<Integer> generateRequests(int count) {
        List<Integer> requests = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            requests.add(rand.nextInt(MAX_TRACK + 1));
        }
        requests.sort(Integer::compareTo);
        return requests;
    }
}
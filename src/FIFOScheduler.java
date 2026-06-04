import java.util.ArrayList;
import java.util.List;

public class FIFOScheduler implements SchedulingAlgorithm {
    @Override
    public SchedulingResult execute(int startPos, List<Integer> requests) {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(startPos);
        int totalMovement = 0;
        int currentPos = startPos;
        
        for (int req : requests) {
            totalMovement += Math.abs(currentPos - req);
            currentPos = req;
            sequence.add(req);
        }
        
        return new SchedulingResult(
            "FIFO", sequence, totalMovement, 
            (double)totalMovement/(sequence.size()-1)
        );
    }
}
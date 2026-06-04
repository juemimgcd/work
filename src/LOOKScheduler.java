import java.util.ArrayList;
import java.util.List;

public class LOOKScheduler implements SchedulingAlgorithm {
    private static final int MAX_TRACK = 199;
    
    @Override
    public SchedulingResult execute(int startPos, List<Integer> requests) {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(startPos);
        int totalMovement = 0;
        int currentPos = startPos;
        List<Integer> remaining = new ArrayList<>(requests);
        int direction = 1; // 1=向外, -1=向内
        
        while (!remaining.isEmpty()) {
            if (direction == 1) {
                int farthest = findFarthest(currentPos, remaining, 1);
                if (farthest == -1) {
                    direction = -1;
                } else {
                    totalMovement += farthest - currentPos;
                    currentPos = farthest;
                    sequence.add(farthest);
                    remaining.remove((Integer)farthest);
                }
            } else {
                int farthest = findFarthest(currentPos, remaining, -1);
                if (farthest == -1) {
                    direction = 1;
                } else {
                    totalMovement += currentPos - farthest;
                    currentPos = farthest;
                    sequence.add(farthest);
                    remaining.remove((Integer)farthest);
                }
            }
        }
        
        return new SchedulingResult(
            "LOOK", sequence, totalMovement,
            (double)totalMovement/(sequence.size()-1)
        );
    }
    
    private int findFarthest(int current, List<Integer> requests, int direction) {
        int farthest = direction == 1 ? -1 : MAX_TRACK + 1;
        for (int req : requests) {
            if (direction == 1 && req >= current) {
                if (req > farthest) farthest = req;
            } else if (direction == -1 && req <= current) {
                if (req < farthest) farthest = req;
            }
        }
        return farthest == (direction == 1 ? -1 : MAX_TRACK + 1) ? -1 : farthest;
    }
}
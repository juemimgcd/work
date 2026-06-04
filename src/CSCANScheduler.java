import java.util.ArrayList;
import java.util.List;

public class CSCANScheduler implements SchedulingAlgorithm {
    private static final int MAX_TRACK = 199;
    
    @Override
    public SchedulingResult execute(int startPos, List<Integer> requests) {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(startPos);
        int totalMovement = 0;
        int currentPos = startPos;
        List<Integer> remaining = new ArrayList<>(requests);
        
        // 向外扫描
        while (true) {
            int next = findNext(currentPos, remaining);
            if (next == -1) {
                totalMovement += MAX_TRACK - currentPos;
                sequence.add(MAX_TRACK);
                currentPos = MAX_TRACK;
                break;
            } else {
                totalMovement += next - currentPos;
                currentPos = next;
                sequence.add(next);
                remaining.remove((Integer)next);
            }
        }
        
        // 如果还有剩余请求，跳转到最内道
        if (!remaining.isEmpty()) {
            totalMovement += MAX_TRACK;
            sequence.add(0);
            currentPos = 0;
            
            // 向内扫描
            while (!remaining.isEmpty()) {
                int next = findNext(currentPos, remaining);
                if (next == -1) break;
                totalMovement += next - currentPos;
                currentPos = next;
                sequence.add(next);
                remaining.remove((Integer)next);
            }
        }
        
        return new SchedulingResult(
            "C-SCAN", sequence, totalMovement,
            (double)totalMovement/(sequence.size()-1)
        );
    }
    
    private int findNext(int current, List<Integer> requests) {
        int next = -1;
        for (int req : requests) {
            if (req >= current) {
                if (next == -1 || req < next) {
                    next = req;
                }
            }
        }
        return next;
    }
}
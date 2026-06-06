import java.util.List;

public class SchedulingResult {
    private final String algorithmName;
    private final List<Integer> sequence;
    private final int totalMovement;
    private final double avgSeekLength;
    
    public SchedulingResult(String algorithmName, List<Integer> sequence, 
                          int totalMovement, double avgSeekLength) {
        this.algorithmName = algorithmName;
        this.sequence = sequence;
        this.totalMovement = totalMovement;
        this.avgSeekLength = avgSeekLength;
    }
    
    // Getters
    public String getAlgorithmName() { return algorithmName; }
    public List<Integer> getSequence() { return sequence; }
    public int getTotalMovement() { return totalMovement; }
    public double getAvgSeekLength() { return avgSeekLength; }
    
    @Override
    public String toString() {
        return String.format(
            "算法: %s\n调度序列: %s\n总移动磁道数: %d\n平均寻道长度: %.2f",
            algorithmName, sequence, totalMovement, avgSeekLength
        );
    }
}
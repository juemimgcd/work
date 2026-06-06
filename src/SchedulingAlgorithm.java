import java.util.List;

public interface SchedulingAlgorithm {
    SchedulingResult execute(int startPos, List<Integer> requests);
}
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiskSchedulingVisualization extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MAX_TRACK = 199;
    private static final int REQUEST_COUNT = 10;
    private static final int START_POS = 100;
    
    private List<Integer> requests;
    private SchedulingResult currentResult;
    private GraphPanel graphPanel;
    private JTextArea resultArea;
    
    public DiskSchedulingVisualization() {
        setTitle("磁盘调度算法可视化");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 生成随机请求
        requests = RequestGenerator.generateRequests(REQUEST_COUNT);
        
        // 控制面板
        JPanel controlPanel = createControlPanel();
        
        // 结果显示区域
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // 图形展示面板
        graphPanel = new GraphPanel();
        graphPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT/2));
        
        // 添加组件
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(graphPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        String[] algorithms = {"FIFO", "SSTF", "SCAN", "LOOK", "C-SCAN"};
        
        for (String algo : algorithms) {
            JButton btn = new JButton(algo);
            btn.addActionListener(e -> {
                SchedulingAlgorithm scheduler = createScheduler(algo);
                currentResult = scheduler.execute(START_POS, requests);
                updateDisplay();
            });
            panel.add(btn);
        }
        
        return panel;
    }
    
    private SchedulingAlgorithm createScheduler(String algorithm) {
        switch(algorithm) {
            case "FIFO": return new FIFOScheduler();
            case "SSTF": return new SSTFScheduler();
            case "SCAN": return new SCANScheduler();
            case "LOOK": return new LOOKScheduler();
            case "C-SCAN": return new CSCANScheduler();
            default: return new FIFOScheduler();
        }
    }
    
    private void updateDisplay() {
        resultArea.setText(currentResult.toString());
        graphPanel.setSequence(currentResult.getSequence());
        graphPanel.repaint();
    }
    
    // 图形绘制面板
    class GraphPanel extends JPanel {
        private List<Integer> sequence = new ArrayList<>();
        
        public void setSequence(List<Integer> seq) {
            this.sequence = seq;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (sequence.isEmpty()) return;
            
            int width = getWidth();
            int height = getHeight();
            int padding = 50;
            
            // 绘制坐标轴
            g.drawLine(padding, height - padding, width - padding, height - padding); // X轴
            g.drawLine(padding, height - padding, padding, padding); // Y轴
            
            // 绘制刻度
            for (int i = 0; i <= MAX_TRACK; i += 20) {
                int x = padding + (width - 2 * padding) * i / MAX_TRACK;
                g.drawLine(x, height - padding, x, height - padding + 5);
                g.drawString(String.valueOf(i), x - 10, height - padding + 20);
            }
            
            // 绘制请求点和移动路径
            for (int i = 0; i < sequence.size(); i++) {
                int pos = sequence.get(i);
                int x = padding + (width - 2 * padding) * pos / MAX_TRACK;
                int y = height - padding - 30 - i * 20;
                
                // 绘制请求点
                g.setColor(Color.BLUE);
                g.fillOval(x - 3, y - 3, 6, 6);
                
                // 绘制磁头移动路径
                if (i > 0) {
                    int prevPos = sequence.get(i - 1);
                    int prevX = padding + (width - 2 * padding) * prevPos / MAX_TRACK;
                    int prevY = height - padding - 30 - (i - 1) * 20;
                    
                    g.setColor(Color.RED);
                    g.drawLine(prevX, prevY, x, y);
                }
            }
            
            // 绘制初始位置标记
            int startX = padding + (width - 2 * padding) * START_POS / MAX_TRACK;
            g.setColor(Color.GREEN);
            g.fillOval(startX - 5, height - padding - 35, 10, 10);
            g.drawString("起始位置", startX - 20, height - padding - 45);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiskSchedulingVisualization::new);
    }
}
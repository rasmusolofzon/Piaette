package server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class TestStartServer extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestStartServer() {
        
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);
        panel.setToolTipText("Tip");

        JButton startButton = new JButton("Start");
        startButton.setBounds(0, 0, 100, 30);
        startButton.setToolTipText("Klicka här");
       
        JButton stopButton = new JButton("Stop");
        stopButton.setBounds(100, 0, 100, 30);
        stopButton.setToolTipText("Klicka här med");

        panel.add(stopButton);
        panel.add(startButton);

        setTitle("Server");
        setSize(200, 70);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TestStartServer ex = new TestStartServer();
                ex.setVisible(true);
            }
        });
    }
}
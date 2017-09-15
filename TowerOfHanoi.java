import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Disk extends JPanel implements Runnable {
	static final int W_WIDTH = 1400;
	static final int W_HEIGHT = 500;
	
	static final int R1 = 300;
	static final int R2 = 700;
	static final int R3 = 1100;
	static final int RW = 10;
	static final int DISK_HEIGHT = 20;
	static final int DISK_DISTANCE = 10;
	static final int DISK_STEP = 20;
	static final int MIN_DISK_WIDTH = 30;
	static final String[] ABC = {"0", "A", "B", "C"};
	
	static int step = 0;
	static int DELAY = 100;
	
	int nDisk;
	int[] disks = new int[1000];
	
	
	Disk() {
		initPanel();
	}
	
	void initPanel() {
        setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
        setDoubleBuffered(true);
	}
	
	void drawRod(Graphics g, int x, int w, String s) {
		g.fillRect(x-RW/2, 100, RW, W_HEIGHT-100);
		Font font = new Font(g.getFont().getFontName(), Font.TRUETYPE_FONT, 30);
		g.setFont(font);
		
		g.drawString(s, x - 12, 100-20);
	}
	
	void drawRods(Graphics g) {
		drawRod(g,R1,RW, "A");
		drawRod(g,R2,RW, "B");
		drawRod(g,R3,RW, "C");
	}
	
	void drawDisk(Graphics g, int r, int hth, int nth) {
		int len = MIN_DISK_WIDTH + 2 * DISK_STEP * nth;
		int x = r - len/2;
		int y = W_HEIGHT - (hth-1)*DISK_DISTANCE - hth*DISK_HEIGHT;
		g.fillRect(x, y, len, DISK_HEIGHT);
		
		Font font = new Font(g.getFont().getFontName(), Font.TRUETYPE_FONT, 20);
		g.setFont(font);
		
		g.drawString(String.valueOf(nth), x-15, y+DISK_HEIGHT);
	}
	
	void drawDisks(Graphics g, int[] rods, int n) {
		int n1, n2, n3;
		n1=n2=n3=0;
		for (int i=n; i>0; i--) {
			if (rods[i] == 1) {
				n1++;
				drawDisk(g, R1, n1, i);
			}
			if (rods[i] == 2) {
				n2++;
				drawDisk(g, R2, n2, i);
			}
			if (rods[i] == 3) {
				n3++;
				drawDisk(g, R3, n3, i);
			}
		}
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
        Thread animator = new Thread(this);
        animator.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// pain here
		drawRods(g);
		drawDisks(g, disks, nDisk);
	}
	
	@Override
	public void run() {
		System.out.print("Enter number of disks: ");
		Scanner scan = new Scanner(System.in);
		nDisk = scan.nextInt();
		System.out.print("Enter delay time: ");
		DELAY = scan.nextInt();
		scan.close();
		repaint();

		System.out.println("Starting after 3s...");

		for (int i=1; i<=nDisk; i++)
			disks[i] = 1;
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			
		}
		Hanoi(nDisk, 1,2,3);
		System.out.println("Done!");
	}
	
	public void moveDisk(int n, int nguon, int dich) {
		disks[n] = dich;
		step++;
		System.out.println("Step " + step + ". Disk " + n + " from " + ABC[nguon] + " to " + ABC[dich]);
		repaint();
		try {
			Thread.sleep(DELAY);
		} catch (Exception e) {
			
		}
	} 
	
	public void Hanoi(int n, int nguon, int tam, int dich) {
		if (n==0)
			return;
		Hanoi(n-1, nguon, dich, tam);
		moveDisk(n, nguon, dich);
		Hanoi(n-1, tam, nguon, dich);
	}
	
}





public class TowerOfHanoi extends JFrame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {                
                JFrame ex = new TowerOfHanoi();
                ex.setVisible(true);                
            }
        });

	}

	TowerOfHanoi() {
		// TODO Auto-generated constructor stub
        add(new Disk());

        setResizable(false);
        pack();
        
        setTitle("Tower of Hanoi");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	}
}

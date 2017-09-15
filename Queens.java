import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Scanner;


public class Queens extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {                
                JFrame ex = new Queens();
                ex.setVisible(true);                
            }
        });

	}

	public Queens() {
		// TODO Auto-generated constructor stub
        add(new Board());

        setResizable(false);
        pack();
        
        setTitle("Queen");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	}
}




class Board extends JPanel implements Runnable {
	
	static final int CELL_SIZE = 100;
	static final int BODER = 10;
	int DELAY = 700;
	int n;
	int[] queens;
	boolean[] hang, cot, cheoTong, cheoHieu;
	int step=0;
	

	public Board() {
		System.out.print("Enter size of board: ");
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
		System.out.print("Enter delay time: ");
		DELAY = scan.nextInt();
		scan.close();
		repaint();

		init(n);

		queens = new int[n];
		setPreferredSize(new Dimension(n*CELL_SIZE, n*CELL_SIZE));
        setDoubleBuffered(true);
	}
	
	
	public void drawCell(Graphics g, int h, int c) {
		if ((h+c)%2==0)
			g.setColor(Color.BLACK);
		else 
			g.setColor(Color.WHITE);
		g.fillRect(h*CELL_SIZE, c*CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}
	
	public void drawBoard(Graphics g) {
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				drawCell(g, i, j);
	}
	
	public void drawQueen(Graphics g, int h, int c) {
		g.setColor(Color.BLUE);
		
		g.fillOval(c*CELL_SIZE + BODER, h*CELL_SIZE + BODER, CELL_SIZE - 2*BODER, CELL_SIZE-2*BODER);
	}
	
	public void drawQueens(Graphics g, int[] x, int num) {
		for (int i=0; i<num; i++) {
			int c = i;
			int h = x[i];
			drawQueen(g, h, c);
		}
	}
	
	
	public void init(int n) {
		queens = new int [n];
		hang = new boolean[n];
		cot = new boolean[n];
		cheoTong = new boolean[2*n];
		cheoHieu = new boolean[2*n];

		for (int i=0; i<n; i++)
			hang[i] = cot[i] = cheoTong[i*2] = cheoTong[i*2+1] = cheoHieu[i*2] = cheoHieu[i*2+1] = false;
	}
	
	boolean isFree(int h, int c) {
		if (hang[h] || cot[c])
			return false;
		if (cheoTong[h+c])
			return false;
		if (cheoHieu[h-c+n])
			return false;
		return true;
	}
	
	void set(int h, int c) {
		hang[h] = cot[c] = cheoTong[h+c] = cheoHieu[h-c+n] = true;
	}

	void unSet(int h, int c) {
		hang[h] = cot[c] = cheoTong[h+c] = cheoHieu[h-c+n] = false;
	}
	
	void updatePaint(int num) {
		step = num + 1;
		repaint();
		try {
			Thread.sleep(DELAY);
		} catch (Exception e) {
			
		}
		// for (int i=0; i<n; i++)
		// 	System.out.print(queens[i] + " ");
		// System.out.println("-" + step);
	}
	
	@SuppressWarnings("deprecation")
	void printResult(int num) {
		step = num + 1;
		repaint();
		System.out.println("Done!");
		try {
			Thread.currentThread().stop();
		} catch (Exception e) {
			
		}
	}
	
	void attemp(int i) { // xet cot i
		for (int j=0; j<n; j++) // dat hau o cot j
			if (isFree(i, j)) {
				queens[i] = j;
//				updatePaint(i);
				if (i==n-1) {
					printResult(i);
				}
				else {
					set(i,j);
					updatePaint(i);
					attemp(i+1);
					updatePaint(i);
					unSet(i,j);
				}

			} else {
				queens[i] = j;
				updatePaint(i);
			}
	}
	
	
	@Override
	public void addNotify() {
		// TODO Auto-generated method stub
		super.addNotify();
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		drawBoard(g);
		drawQueens(g, queens, step);
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Starting after 3s...");
			Thread.sleep(3000);
		} catch (Exception e) {
			
		}
		attemp(0);
	}
	
	
}




import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import java.util.TimerTask;

import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {
	
	
	final static int maxRows = 8;
	final static int maxCols = 8;
	final static int WIDTH = 760;
	final static int HEIGHT = 760;
	
	public static Color unselected = new Color(0, 0, 0, 255);
	public static Color selectedLow = new Color(200, 10, 10, 255);
	public static Color selectedMid = new Color(10, 200, 10, 255);
	public static Color selectedHigh = new Color(10, 10, 200, 255);
	
	public static Color highlightedOff = new Color(30, 30, 30, 255);
	public static Color highlightedLow = new Color(255, 200, 200, 255);
	public static Color highlightedMid = new Color(200, 255, 200, 255);
	public static Color highlightedHigh = new Color(200, 200, 255, 255);
	
	
	enum heightValue {off, low, mid, high;
	};

	public static class Cell extends Point {

		heightValue height;
		public boolean highlighted;

		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
			height = heightValue.off;
			highlighted = false;
		}
		
		
	}

	public static Cell[][]  cells;
	static int cellWidth = WIDTH / maxCols;
	static int cellHeight = HEIGHT / maxRows;
	public static int frameRate = 24;

	public Grid() {
		cells = new Cell[maxRows][maxCols];
		addMouseListener(this);
		addAnimationTimer();
		populateGrid();
	}
	
	public void addAnimationTimer(){
		Timer animationTimer = new Timer(1000/frameRate, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		animationTimer.start();
	}

	public static void populateGrid(){
		for (int i = 0; i < maxRows; i++){
			for (int j = 0; j < maxCols; j++){
				cells[i][j] = new Cell(i, j);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int x = 0; x < maxRows; x++) {
			for (int y = 0; y < maxCols; y++){
				int cellX = 10 + (x * cellWidth);
				int cellY = 10 + (y * cellHeight);
				if (cells[x][y].highlighted)
					if (cells[x][y].height == heightValue.low)
						g.setColor(highlightedLow);
					else if (cells[x][y].height == heightValue.mid)
						g.setColor(highlightedMid);
					else if (cells[x][y].height == heightValue.high)
						g.setColor(highlightedHigh);
					else g.setColor(highlightedOff);
				else if (cells[x][y].height == heightValue.low)
					g.setColor(selectedLow);	
				else if (cells[x][y].height == heightValue.mid)
					g.setColor(selectedMid);
				else if (cells[x][y].height == heightValue.high)
					g.setColor(selectedHigh);
				else
					g.setColor(unselected);
				g.fillRect(cellX, cellY, cellWidth, cellHeight);
			}		
		}
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, WIDTH, HEIGHT);

		for (int i = 10; i <= WIDTH+10; i += cellWidth){
			g.drawLine(i, 10, i, WIDTH+10);
		}
		for (int i = 10; i <= HEIGHT+10; i += cellHeight) {
			g.drawLine(10, i, HEIGHT+10, i);
		}
	}

	public void toggleCell(int x, int y, int depthVal) {
		Cell c = cells[x][y];
		System.out.println(x + " " + y + " " + depthVal);
		switch(depthVal){
		case 0:
			c.height = heightValue.off;
			break;
		case 1:
			c.height = heightValue.low;
			break;
		case 2:
			c.height = heightValue.mid;
			break;
		case 3:
			c.height = heightValue.high;
			break;
		}

		//			switch (c.height){
		//				case off: c.height = heightValue.low;
		//				break;
		//				case low: c.height = heightValue.mid;
		//				break;
		//				case mid: c.height = heightValue.high;
		//				break;
		//				case high: c.height= heightValue.off;
		//				break;
		//			}
		//			if (c.height == heightValue.off) {
		//				c.height = heightValue.low;
		//			}
		//			else
		//				c.height = heightValue.off;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = (e.getX()-11)/cellWidth;
		int y = (e.getY()-11)/cellHeight;
		if (!(e.getX()>WIDTH+10||e.getX()<10||e.getY()>HEIGHT+10||e.getY()<10)){
			toggleCell(x, y, (cells[x][y].height.ordinal() + 1) % 4);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
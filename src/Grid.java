import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {
	
	enum heightValue {low, mid, high, off};
	
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
		final static int maxRows = 8;
		final static int maxCols = 8;
		
		public Grid() {
			cells = new Cell[maxRows][maxCols];
			addMouseListener(this);
			populateGrid();
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
					int cellX = 10 + (x * 50);
					int cellY = 10 + (y * 50);
					if (cells[x][y].highlighted)
						if (cells[x][y].height == heightValue.low)
							g.setColor(Color.BLUE);
						else if (cells[x][y].height == heightValue.mid)
							g.setColor(Color.YELLOW);
						else g.setColor(Color.RED);
					else if (cells[x][y].height == heightValue.low)
						g.setColor(Color.LIGHT_GRAY);	
					else if (cells[x][y].height == heightValue.mid)
						g.setColor(Color.GRAY);
					else if (cells[x][y].height == heightValue.high)
						g.setColor(Color.DARK_GRAY);
					else
						g.setColor(Color.BLACK);
					g.fillRect(cellX, cellY, 50, 50);
				}		
			}
			g.setColor(Color.WHITE);
			g.drawRect(10, 10, 400, 400);

			for (int i = 10; i <= 410; i += 50){
				g.drawLine(i, 10, i, 410);
			}
			for (int i = 10; i <= 410; i += 50) {
				g.drawLine(10, i, 410, i);
			}
		}
		
		public void toggleCell(int x, int y) {
			Cell c = cells[x][y];
			switch (c.height){
				case off: c.height = heightValue.low;
				break;
				case low: c.height = heightValue.mid;
				break;
				case mid: c.height = heightValue.high;
				break;
				case high: c.height= heightValue.off;
				break;
			}
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
			if (!(e.getX()>410||e.getX()<10||e.getY()>410||e.getY()<10)){
				toggleCell((e.getX()-11)/50, (e.getY()-11)/50);
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
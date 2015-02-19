import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Grid extends JPanel implements MouseListener {
	
	public static class Cell extends Point {
		
		public boolean selected;
		public boolean highlighted;
		
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
			selected = false;
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
						g.setColor(Color.BLUE);
					else if (cells[x][y].selected)
						g.setColor(Color.GRAY);	
					else
						g.setColor(Color.WHITE);
					g.fillRect(cellX, cellY, 50, 50);
				}		
			}
			g.setColor(Color.BLACK);
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
			if (c.selected) {
				c.selected = false;
			}
			else
				c.selected = true;
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
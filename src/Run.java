import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Run {
	public static void main (String[] args){
		final Grid grid = new Grid();
		EventQueue.invokeLater(new Runnable() {
			@Override 
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}
	
				JFrame window = new JFrame();
				window.setSize(420, 440);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.add(grid);
				window.setVisible(true);
				
			}
		});
		MusicPlayer musicPlayer = new MusicPlayer();
		musicPlayer.run(grid);
	}
	
}

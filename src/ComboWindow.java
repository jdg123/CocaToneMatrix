import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GraphicsDevice;
public class ComboWindow extends JFrame{
	Grid grid;
	KinectToMatrix km;
	JTabbedPane jtb = new JTabbedPane();
	JSlider lowToMidSlider, midToHighSlider, minDistSlider;
	JButton calibrateButton;
	JPanel distanceSlidersPanel, settingsPanel;

	final int MAX_SCALE = 30;
	final int TICK = 15;



	public ComboWindow(Grid g, KinectToMatrix k){
		grid = g;
		km = k;



		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createComponents();
		setupGUI();
		setEventHandlers();


		GraphicsDevice d = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if(d.isFullScreenSupported()){
			d.setFullScreenWindow(this);
		}else{
			setExtendedState(Frame.MAXIMIZED_BOTH);
			setLocationRelativeTo(null);
		}
		setVisible(true);

	}


	public void createComponents(){
		minDistSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_SCALE, 2);
		lowToMidSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_SCALE, 8);
		midToHighSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_SCALE, 12);

		lowToMidSlider.setMajorTickSpacing(TICK);
		midToHighSlider.setMajorTickSpacing(TICK);
		minDistSlider.setMajorTickSpacing(TICK);

		lowToMidSlider.setPaintTicks(true);
		midToHighSlider.setPaintTicks(true);
		minDistSlider.setPaintTicks(true);

		calibrateButton = new JButton("Recalibrate");

		distanceSlidersPanel = new JPanel();
		settingsPanel = new JPanel();


	}

	public void setupGUI(){

		distanceSlidersPanel.setLayout(new BoxLayout(distanceSlidersPanel, BoxLayout.Y_AXIS));
		distanceSlidersPanel.add(minDistSlider);
		distanceSlidersPanel.add(lowToMidSlider);
		distanceSlidersPanel.add(midToHighSlider);

		distanceSlidersPanel.add(calibrateButton);

		km.setPreferredSize(new Dimension(680, 680));

		settingsPanel.add(km);
		settingsPanel.add(distanceSlidersPanel);

		
		
		jtb.add(grid, "CocaTone");
		jtb.add(settingsPanel, "Settings");
		add(jtb);

	}

	public void setEventHandlers(){
		calibrateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				km.calibrate();
			}
		});

		lowToMidSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				km.lowToMid = lowToMidSlider.getValue()/10.0f;
				System.out.println(km.minDist + " - " + km.lowToMid + " - " + km.midToHigh + " ->");
			}

		});
		midToHighSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				km.midToHigh = midToHighSlider.getValue()/10.0f;
				System.out.println(km.minDist + " - " + km.lowToMid + " - " + km.midToHigh + " ->");

			}

		});

		minDistSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				km.minDist = minDistSlider.getValue()/10.0f;
				System.out.println(km.minDist + " - " + km.lowToMid + " - " + km.midToHigh + " ->");

			}

		});


	}

}

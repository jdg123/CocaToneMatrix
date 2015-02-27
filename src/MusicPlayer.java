import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;

public final class MusicPlayer implements JMC {

	Integer [] scaleValue = new Integer[8];
	
	public void run (Grid grid){
		
		SetPentatonicScale();
		
		Score s = new Score("JMDemo1 - Scale");	
	
		Part p1 = new Part("Flute", RHODES, 0);
		s.addPart(p1);
		
		Part p2 = new Part("Guitar", JAZZ_GUITAR, 1);
		s.addPart(p2);
		
		Part p3 = new Part("Piano", CELLO, 2);
		s.addPart(p3);
		
		Part[] parts = new Part[] {p1, p2, p3};
		
		
		//Play.midi(s, false);
		
		Phrase [] phrases = new Phrase[8];
			for (int ii = 0; ii < 3; ii++){
				for (int i = 0; i < 8; i++){
					phrases[i] = new Phrase();
					parts[ii].addPhrase(phrases[i]);
				}
			}
		while (true) {
			for (int x = 0; x < grid.maxRows; x++){
				for (int y = 0; y < grid.maxCols; y++){
					p1.getPhrase(y).empty();
					p2.getPhrase(y).empty();
					p3.getPhrase(y).empty();
					//Turn off highlighting in previous column
					//If first row, turn off the last column
					if (x == 0){		
						grid.cells[grid.maxCols-1][y].highlighted = false;
					}
					else {
						grid.cells[x-1][y].highlighted = false;
					}
					if (grid.cells[x][y].height != Grid.heightValue.off){
						grid.cells[x][y].highlighted = true;
						if (grid.cells[x][y].height == Grid.heightValue.low){ 
							Note n = new Note (C4+scaleValue[y], SIXTEENTH_NOTE);
							p1.getPhrase(y).addNote(n);
						} else if (grid.cells[x][y].height == Grid.heightValue.mid) {
							Note n = new Note (C4+scaleValue[y], SIXTEENTH_NOTE);
							p2.getPhrase(y).addNote(n);
						} else {
							Note n = new Note (C4+scaleValue[y], SIXTEENTH_NOTE);
							p3.getPhrase(y).addNote(n);
						}
					}
					else {
						Note n = new Note (REST, SIXTEENTH_NOTE);
						p1.getPhrase(y).addNote(n);
					}	
				}
				grid.repaint();
				Play.midi(s, false);	
			}
		}
	}
	
	public void SetPentatonicScale(){
		scaleValue[7] = 0;
		scaleValue[6] = 3;
		scaleValue[5] = 5;
		scaleValue[4] = 7;
		scaleValue[3] = 10;
		scaleValue[2] = 12;
		scaleValue[1] = 15;
		scaleValue[0] = 17;		
	}
}
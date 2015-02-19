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
		Score s1 = new Score();
		
		Part p = new Part("Flute", RHODES, 0);
		s.addPart(p);
		Play.midi(s, false);
		
		Phrase [] phrases = new Phrase[8];
		for (int i = 0; i < 8; i++){
			phrases[i] = new Phrase();
			p.addPhrase(phrases[i]);
		}
		while (true) {
			for (int x = 0; x < grid.maxRows; x++){
				for (int y = 0; y < grid.maxCols; y++){
					phrases[y].empty();
					//Turn off highlighting in previous column
					//If first row, turn off the last column
					if (x == 0){		
						grid.cells[grid.maxCols-1][y].highlighted = false;
					}
					else {
						grid.cells[x-1][y].highlighted = false;
					}
					if (grid.cells[x][y].selected){
						grid.cells[x][y].highlighted = true;
						Note n = new Note (C4+scaleValue[y], SIXTEENTH_NOTE);
						phrases[y].addNote(n);
					}
					else {
						Note n = new Note (REST, SIXTEENTH_NOTE);
						phrases[y].addNote(n);
					}	
				}
				grid.repaint();
				Play.updateScore(s1);
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
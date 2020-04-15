package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SATBGenerator {
	private static final String[] files = new String[] {
			"data/test1.txt",
			"data/test2.txt"
	};
	
	public static void main(String[] args) {
		BufferedReader reader;
		for(String filepath : files) {
			try {
				reader = new BufferedReader(new FileReader(filepath));
				processFile(reader);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Chord chordFromStr(String str) throws Exception {
		str = str.toLowerCase();
		switch(str) {
		// MAJOR TRIADS
		case "maj":
			return Triad.MAJ;
		case "maj6":
		case "maj63":
			return Triad.MAJ63;
		case "maj64":
			return Triad.MAJ64;
		// MINOR TRIADS
		case "min":
			return Triad.MIN;
		case "min6":
		case "min63":
			return Triad.MIN63;
		case "min64":
			return Triad.MIN64;
		// AUGMENTED TRIADS
		case "aug":
			return Triad.AUG;
		case "aug6":
		case "aug63":
			return Triad.AUG63;
		case "aug64":
			return Triad.AUG64;
		// DIMINISHED TRIADS
		case "dim":
			return Triad.DIM;
		case "dim6":
		case "dim63":
			return Triad.DIM63;
		case "dim64":
			return Triad.DIM64;
		// SEVENTHS
		// MAJOR SEVENTHS
		case "maj7":
			return Seventh.MAJ7;
		case "maj65":
			return Seventh.MAJ65;
		case "maj43":
			return Seventh.MAJ43;
		case "maj42":
			return Seventh.MAJ42;
		// DOMINANT SEVENTHS
		case "dom7":
		case "dom":
			return Seventh.DOM7;
		case "dom65":
			return Seventh.DOM65;
		case "dom43":
			return Seventh.DOM43;
		case "dom42":
			return Seventh.DOM42;
		// MAJOR SEVENTHS
		case "min7":
			return Seventh.MIN7;
		case "min65":
			return Seventh.MIN65;
		case "min43":
			return Seventh.MIN43;
		case "min42":
			return Seventh.MIN42;
		// MAJOR SEVENTHS
		case "halfdim7":
		case "halfdim":
		case "hdim7":
		case "hdim":
			return Seventh.HALFDIM7;
		case "halfdim65":
		case "hdim65":
			return Seventh.HALFDIM65;
		case "halfdim43":
		case "hdim43":
			return Seventh.HALFDIM43;
		case "halfdim42":
		case "hdim42":
			return Seventh.HALFDIM42;
		// MAJOR SEVENTHS
		case "dim7":
			return Seventh.DIM7;
		case "dim65":
			return Seventh.DIM65;
		case "dim43":
			return Seventh.DIM43;
		case "dim42":
			return Seventh.DIM42;
		}
		throw new Exception("Chord parsing failed for string " + str);
	}
	
	private static void processFile(BufferedReader reader) throws IOException, Exception {
		String keyStr = reader.readLine();
		String isToneStr = reader.readLine();
		Tone tonic = Tone.fromString(keyStr);
		tonic = Tone.LOWEST_TONE.nextInstanceOf(tonic);
		boolean isTone = isToneStr.toLowerCase().equals("tones");
		
		ArrayList<Interval> bassIntervals = new ArrayList<Interval>();
		ArrayList<Chord> chords = new ArrayList<Chord>();
		
		String next = reader.readLine();
		while(next != null) {
			String[] parts = next.split(":");
			try {
				if(parts.length < 2) throw new IllegalArgumentException("':' character missing to separate interval/tone and chord");
				Chord currChord = chordFromStr(parts[1]);
				chords.add(currChord);
				
				if(isTone) {
					Tone temp = Tone.fromString(parts[0]);
					temp = temp.up(currChord.rootToBass());
					temp = tonic.nextInstanceOf(temp);
					Interval i = Interval.intervalBetween(tonic, temp);
					bassIntervals.add(i);
				}else {
					bassIntervals.add(Interval.normalize(Interval.fromString(parts[0]).add(currChord.rootToBass())));
				}
			}catch(Exception e) {
				System.err.println("Failed to parse line \"" + next + "\" into interval/tone and chord.");
				e.printStackTrace();
				return;
			}
			
			next = reader.readLine();
		}
		
		System.out.println(tonic);
//		System.out.println(bassIntervals);
//		System.out.println(chords);
		for(int i = 0; i < bassIntervals.size(); i++) {
			System.out.println(chords.get(i).baseChord(tonic.up(bassIntervals.get(i))));
		}

	}
}

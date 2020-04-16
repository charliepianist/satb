package com.charliepianist.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.charliepianist.out.PatternOutput;

public class SATB {
	
	public static final int ROMAN = 0;
	public static final int INTERVALS = 1;
	public static final int TONES = 2;
	
	private static final String[] ioNames = new String[] {
			"data/standard.txt",
			"out/out.midi"
	};
	
	public static Chord chordFromStr(String str) {
		str = str.toLowerCase();
		switch(str) {
		// MAJOR TRIADS
		case "maj":
		case "maj53":
			return Triad.MAJ;
		case "maj6":
		case "maj63":
			return Triad.MAJ63;
		case "maj64":
			return Triad.MAJ64;
		// DOMINANT TRIADS (Note: the only difference between this and major is that the 3rd MUST either remain unchanged OR resolve to a 4th.
		case "dom":
		case "dom53":
			return Triad.DOM;
		case "dom6":
		case "dom63":
			return Triad.DOM6;
		case "dom64":
			return Triad.DOM64;
		// MINOR TRIADS
		case "min":
		case "min53":
			return Triad.MIN;
		case "min6":
		case "min63":
			return Triad.MIN63;
		case "min64":
			return Triad.MIN64;
		// AUGMENTED TRIADS
		case "aug":
		case "aug53":
			return Triad.AUG;
		case "aug6":
		case "aug63":
			return Triad.AUG63;
		case "aug64":
			return Triad.AUG64;
		// DIMINISHED TRIADS
		case "dim":
		case "dim53":
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
		case "hdim7":
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
		throw new IllegalArgumentException("Chord parsing failed for string " + str);
	}
	
	// Interval from tonic to ROOT of chord
	// NOTE: ALL METHODS USE HARMONIC MINOR SCALE
	public static Interval intervalFromRoman(String str, boolean isMinor) {
		str = str.replaceAll("[0-9]", "").replaceAll(" ", "");
		switch(str) {
		// Unison
		case "I":
		case "i":
			return Interval.UNISON;
		// 2nds
		case "bII":
		case "bii":
			return Interval.m2;
		case "II":
		case "ii":
			return Interval.M2;
		case "#II":
		case "#ii":
			return Interval.A2;
		// 3rds
		case "bIII":
		case "biii":
			if(!isMinor)
				return Interval.m3;
		case "III":
		case "iii":
			if(isMinor) return Interval.m3;
			return Interval.M3;
		case "#III":
		case "#iii":
			if(isMinor) return Interval.M3;
			return Interval.A3;
		// 4ths
		case "bIV":
		case "biv":
			return Interval.d4;
		case "IV":
		case "iv":
			return Interval.P4;
		case "#IV":
		case "#iv":
			return Interval.A4;
		// 5ths
		case "bV":
		case "bv":
			return Interval.d5;
		case "V":
		case "v":
			return Interval.P5;
		case "#V":
		case "#v":
			return Interval.A5;
		// 6ths
		case "bVI":
		case "bvi":
			if(!isMinor)
				return Interval.m6;
		case "VI":
		case "vi":
			if(isMinor) return Interval.m6;
			return Interval.M6;
		case "#VI":
		case "#vi":
			if(isMinor) return Interval.M6;
			return Interval.A6;
		// 7ths
		case "bVII":
		case "bvii":
			return Interval.m7;
		case "VII":
		case "vii":
			return Interval.M7;
		case "#VII":
		case "#vii":
			return Interval.A7;
		}
		throw new IllegalArgumentException("Could not parse roman numeral \"" + str + "\" into an interval");
	}
	
	public static Chord impliedChordFromRoman(String str, boolean isMinor) {
		str = str.replaceAll(" ", "");
		if(!isMinor) {
			switch(str) {
			// Triads
			case "I":
			case "I53":
			case "IV":
			case "IV53":
			case "V":
			case "V53":
			case "bII":
			case "bII53":
			case "bIII":
			case "bIII53":
			case "bVI":
			case "bVI53":
			case "bVII":
			case "bVII53":
				return Triad.MAJ53;
			case "I6":
			case "I63":
			case "V6":
			case "V63":
			case "IV6":
			case "IV63":
			case "bII6":
			case "bII63":
			case "bIII6":
			case "bIII63":
			case "bVI6":
			case "bVI63":
			case "bVII6":
			case "bVII63":
				return Triad.MAJ63;
			case "I64":
			case "IV64":
			case "V64":
			case "bII64":
			case "bIII64":
			case "bVI64":
			case "bVII64":
				return Triad.MAJ64;
			case "i":
			case "i53":
			case "iv":
			case "iv53":
			case "ii":
			case "ii53":
			case "iii":
			case "iii53":
			case "vi":
			case "vi53":
				return Triad.MIN53;
			case "i6":
			case "i63":
			case "iv6":
			case "iv63":
			case "ii6":
			case "ii63":
			case "iii6":
			case "iii63":
			case "vi6":
			case "vi63":
				return Triad.MIN63;
			case "i64":
			case "iv64":
			case "ii64":
			case "iii64":
			case "vi64":
				return Triad.MIN64;
			case "vii":
			case "vii53":
				return Triad.DIM53;
			case "vii6":
			case "vii63":
				return Triad.DIM63;
			case "vii64":
				return Triad.DIM64;
			// Sevenths
			case "I7":
			case "IV7":
			case "bII7":
			case "bIII7":
			case "bVI7":
				return Seventh.MAJ7;
			case "I65":
			case "IV65":
			case "bII65":
			case "bIII65":
			case "bVI65":
				return Seventh.MAJ65;
			case "I43":
			case "IV43":
			case "bII43":
			case "bIII43":
			case "bVI43":
				return Seventh.MAJ43;
			case "I42":
			case "IV42":
			case "bII42":
			case "bIII42":
			case "bVI42":
				return Seventh.MAJ42;
			case "V7":
			case "bVII7":
				return Seventh.DOM7;
			case "V65":
			case "bVII65":
				return Seventh.DOM65;
			case "V43":
			case "bVII43":
				return Seventh.DOM43;
			case "V42":
			case "bVII42":
				return Seventh.DOM42;
			case "i7":
			case "iv7":
			case "ii7":
			case "iii7":
			case "vi7":
				return Seventh.MIN7;
			case "i65":
			case "iv65":
			case "ii65":
			case "iii65":
			case "vi65":
				return Seventh.MIN65;
			case "i43":
			case "iv43":
			case "ii43":
			case "iii43":
			case "vi43":
				return Seventh.MIN43;
			case "i42":
			case "iv42":
			case "ii42":
			case "iii42":
			case "vi42":
				return Seventh.MIN42;
			case "vii7":
				return Seventh.HALFDIM7;
			case "vii65":
				return Seventh.HALFDIM65;
			case "vii43":
				return Seventh.HALFDIM43;
			case "vii42":
				return Seventh.HALFDIM42;
			}
		}else {
			// Minor key chords
			switch(str) {
			// Triads
			// III are typically augmented, but augmented is usually specified separately, and will have to be specified in format like "III:aug" as well
			case "III":
			case "III53":
				return Triad.MAJ53;
			case "III6":
			case "III63":
				return Triad.MAJ63;
			case "III64":
				return Triad.MAJ64;
			case "I":
			case "I53":
			case "IV":
			case "IV53":
			case "V":
			case "V53":
			case "bII":
			case "bII53":
			case "VI":
			case "VI53":
			case "bVII":
			case "bVII53":
				return Triad.MAJ53;
			case "I6":
			case "I63":
			case "V6":
			case "V63":
			case "IV6":
			case "IV63":
			case "bII6":
			case "bII63":
			case "VI6":
			case "VI63":
			case "bVII6":
			case "bVII63":
				return Triad.MAJ63;
			case "I64":
			case "IV64":
			case "V64":
			case "bII64":
			case "VI64":
			case "bVII64":
				return Triad.MAJ64;
			case "i":
			case "i53":
			case "iv":
			case "iv53":
			case "iii":
			case "iii53":
			case "vi":
			case "vi53":
				return Triad.MIN53;
			case "i6":
			case "i63":
			case "iv6":
			case "iv63":
			case "iii6":
			case "iii63":
			case "vi6":
			case "vi63":
				return Triad.MIN63;
			case "i64":
			case "iv64":
			case "iii64":
			case "vi64":
				return Triad.MIN64;
			case "vii":
			case "vii53":
			case "ii":
			case "ii53":
				return Triad.DIM53;
			case "vii6":
			case "vii63":
			case "ii6":
			case "ii63":
				return Triad.DIM63;
			case "vii64":
			case "ii64":
				return Triad.DIM64;
			// Sevenths
			case "I7":
			case "IV7":
			case "bII7":
			case "VI7":
			case "III7":
				return Seventh.MAJ7;
			case "I65":
			case "IV65":
			case "bII65":
			case "VI65":
			case "III65":
				return Seventh.MAJ65;
			case "I43":
			case "IV43":
			case "bII43":
			case "VI43":
			case "III43":
				return Seventh.MAJ43;
			case "I42":
			case "IV42":
			case "bII42":
			case "VI42":
			case "III42":
				return Seventh.MAJ42;
			case "V7":
			case "bVII7":
				return Seventh.DOM7;
			case "V65":
			case "bVII65":
				return Seventh.DOM65;
			case "V43":
			case "bVII43":
				return Seventh.DOM43;
			case "V42":
			case "bVII42":
				return Seventh.DOM42;
			case "i7":
			case "iv7":
			case "vi7":
				return Seventh.MIN7;
			case "i65":
			case "iv65":
			case "vi65":
				return Seventh.MIN65;
			case "i43":
			case "iv43":
			case "vi43":
				return Seventh.MIN43;
			case "i42":
			case "iv42":
			case "vi42":
				return Seventh.MIN42;
			case "ii7":
				return Seventh.HALFDIM7;
			case "ii65":
				return Seventh.HALFDIM65;
			case "ii43":
				return Seventh.HALFDIM43;
			case "ii42":
				return Seventh.HALFDIM42;
			case "vii7":
				return Seventh.DIM7;
			case "vii65":
				return Seventh.DIM65;
			case "vii43":
				return Seventh.DIM43;
			case "vii42":
				return Seventh.DIM42;
			}
		}
		throw new IllegalArgumentException("Could not find an implied chord from \"" + str + "\"");
	}
	
	public static String satbToString(Voice[] voices) {
		String output = "";
		for(int i = 0; i < voices.length; i++) {
			for(Tone t : voices[i].getLine()) {
				output += String.format("%1$-6s", t.toString());
			}
			if(i < voices.length - 1) output += "\n";
		}
		return output;
	}
	
	private static void processFile(BufferedReader reader) throws IOException, Exception {
		// Configuration variables
		String keyStr = reader.readLine().toLowerCase();
		String modeStr = reader.readLine();
		String entropyStr = reader.readLine();
		boolean isMin = false;
		
		if(keyStr.endsWith("min")) {
			isMin = true;
			keyStr = keyStr.substring(0, keyStr.length() - 3);
		}else if(keyStr.endsWith("minor")) {
			isMin = true;
			keyStr = keyStr.substring(0, keyStr.length() - 5);
		}else if(keyStr.endsWith("m")) {
			isMin = true;
			keyStr = keyStr.substring(0, keyStr.length() - 1);
		}
		Tone tonic = Tone.fromString(keyStr);
		tonic = Tone.LOWEST_TONE.nextInstanceOf(tonic);
		
		int readMode = ROMAN;
		switch(modeStr.toLowerCase()) {
		case "roman":
		case "romans":
			readMode = ROMAN;
			break;
		case "intervals":
		case "interval":
			readMode = INTERVALS;
			break;
		case "tones":
		case "tone":
			readMode = TONES;
			break;
		}
		
		int entropy = SATBGenerator.ENTROPY_START;
		switch(entropyStr.substring(entropyStr.indexOf(':') + 1).replace(" ", "").toLowerCase()) {
		case "all":
			entropy = SATBGenerator.ENTROPY_ALL;
			break;
		case "start":
			entropy = SATBGenerator.ENTROPY_START;
			break;
		case "none":
			entropy = SATBGenerator.ENTROPY_NONE;
			break;
		}
		
		// Start reading the rest of the file
		ArrayList<Interval> bassIntervals = new ArrayList<Interval>();
		ArrayList<Chord> chords = new ArrayList<Chord>();
		
		String next = reader.readLine();
		while(next != null) {
			if(next.length() == 1 || (next.length() > 1 && !next.substring(0, 2).equals("//"))) {
				try {
					String[] parts = next.split(":");
					// Read in tones or intervals format
					if(readMode == TONES || readMode == INTERVALS) {
						if(parts.length < 2) throw new IllegalArgumentException("':' character missing to separate interval/tone and chord");
						Chord currChord = chordFromStr(parts[1]);
						chords.add(currChord);
						
						if(readMode == TONES) {
							Tone temp = Tone.fromString(parts[0]);
							temp = temp.up(currChord.rootToBass());
							temp = tonic.nextInstanceOf(temp);
							Interval i = Interval.intervalBetween(tonic, temp);
							bassIntervals.add(i);
						}else {
							bassIntervals.add(Interval.normalize(Interval.fromString(parts[0]).add(currChord.rootToBass())));
						}
					}else if(readMode == ROMAN) {
						// Read as Roman format
						if(parts.length == 0) throw new IllegalArgumentException("Cannot read empty line.");
						
						Chord currChord = null;
						Interval i;
						if(parts.length == 1) {
							i = intervalFromRoman(next, isMin);
							currChord = impliedChordFromRoman(next, isMin);
						}else {
							i = intervalFromRoman(parts[0], isMin);
							currChord = chordFromStr(parts[1]);
						}
						chords.add(currChord);
						bassIntervals.add(Interval.normalize(i).add(currChord.rootToBass()));
						
					}
				}catch(Exception e) {
					System.err.println("Failed to parse line \"" + next + "\" into interval/tone and chord.");
					e.printStackTrace();
					return;
				}
			}
			
			next = reader.readLine();
		}
		
		SATBGenerator generator = new SATBGenerator();
		Voice[] satb = generator.generateSATB(tonic, bassIntervals, chords, entropy);
		output(satb, false);
	}
	
	private static void output(Voice[] satb, boolean play) {
		if(satb == null) {
			System.out.println("Failed to generate SATB for given file (could not find voicing that follows SATB rules).");
		}else {
			System.out.println(satbToString(satb));
			if(play) {
				PatternOutput.playSATB(satb);
				System.out.println("Playing.");
			}
			try {
				PatternOutput.saveSATB(satb, ioNames[1]);
				System.out.println("Saved to MIDI at '" + ioNames[1] + "'");
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		BufferedReader reader;
		String filepath = ioNames[0];
		try {
			reader = new BufferedReader(new FileReader(filepath));
			processFile(reader);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

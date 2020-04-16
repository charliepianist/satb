package com.charliepianist.out;

import java.util.List;
import java.util.stream.Collectors;

import org.jfugue.pattern.Pattern;

import com.charliepianist.main.Tone;
import com.charliepianist.main.Voice;

public class PatternBuilder {
	
	public static int CHOIR_AAHS = 52;
	public static int PIANO = 0;
	
	// Create single voice from tones
	public static Pattern voicePatternFromTones(List<Tone> tones) {
		List<String> toneStrings = tones.stream()
				.map(t -> t.normalize().toString(1) + "q") // Adding one to octave because for some reason, the player is playing it down an octave
				.collect(Collectors.toList());
		String last = toneStrings.remove(toneStrings.size() - 1);
		last = last.substring(0, last.length() - 1) + "/1";
		toneStrings.add(last);
		// Make last note 2 whole notes long
		
		String patternString = String.join(" ", toneStrings);
		Pattern pattern = new Pattern(patternString);
		return pattern;
	}
	
	// Default tempo is 120 bpm
	public static Pattern songFromVoices(Voice[] voices, int tempo, int instrument) {
		Pattern song = new Pattern();
		Pattern header = new Pattern("T" + tempo + " V0 I" + instrument + 
				" V1 I" + instrument + 
				" V2 I" + instrument + 
				" V3 I" + instrument);
		song.add(header);
				
		for(int i = 0; i < voices.length; i++) {
			String voiceStr = "V" + i + " " + voicePatternFromTones(voices[i].getLine());
			song.add(voiceStr);
		}
		return song;
	}
}

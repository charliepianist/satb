package com.charliepianist.out;

import java.io.File;
import java.io.IOException;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import com.charliepianist.main.Voice;

public class PatternOutput {
	private static final Player player = new Player();
	
	public static void playPattern(Pattern pattern) {
		player.play(pattern);
	}
	
	public static void playSATB(Voice[] satb) {
		Pattern pattern = PatternBuilder.songFromVoices(satb, 20, PatternBuilder.PIANO);
		playPattern(pattern);
	}
	
	public static void saveSATB(Voice[] satb, String filepath) throws IOException {
		Pattern pattern = PatternBuilder.songFromVoices(satb, 20, PatternBuilder.PIANO);
		MidiFileManager.savePatternToMidi(pattern, new File(filepath));
	}
}

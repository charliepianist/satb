package com.charliepianist.out;

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
}

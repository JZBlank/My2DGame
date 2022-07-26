package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	float prevVolume = 0;
	float currentVolume = 0;
	FloatControl fc;
	boolean mute = false;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/meow.wav");
		soundURL[1] = getClass().getResource("/sound/Spring-Flowers.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		} catch(Exception e) {
		}
	}
	
	public void play() {
	  clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void setGameVolume() {
		fc.setValue((float) 0);
	}
	
	public void seteffectVolume() {
		fc.setValue((float) 4);
	}
	
}

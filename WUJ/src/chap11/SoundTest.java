package chap11;

import java.applet.Applet;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundTest extends Applet implements ActionListener{

	Button playButton, loopButton, stopButton;
//	AudioClip sound;
	File file = new File("D:\\LakeSamples\\midnight_ride.wav");
	Clip clip;
	
	@Override
	public void init() {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
//		sound = getAudioClip(getCodeBase(), "Button1.mp3");
//			URL url = new URL("file://D:\\LakeSamples\\button-4.mp3");
//			URL url = new URL("https://gamesounds.xyz/wowamusic/Cristian%20R.%20Aguiar%20-%20Dancing%20in%20the%20South.mp3");
//			sound = Applet.newAudioClip(url);
			
			clip.start();
			System.out.println("AUdio loaded");
			
		} catch (Exception e) {
			System.out.println("url주소가 올바르지 않습니다. ");
		}
	
		playButton = new Button("play");
		playButton.addActionListener(this);
		add(playButton);
		
		loopButton = new Button("loop");
		loopButton.addActionListener(this);
		add(loopButton);
		
		stopButton = new Button("stop");
		stopButton.addActionListener(this);
		add(stopButton);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == playButton ) {
			clip.start(); System.out.println("played");}
		else if(e.getSource() == loopButton){clip.loop(Clip.LOOP_CONTINUOUSLY);;}
		else if(e.getSource() == stopButton){clip.stop();}
	}

	
}

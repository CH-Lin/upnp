package tara.ismp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends Thread {
	private AudioFormat format;

	private MusicModel model;

	private String filename;

	private boolean running, pause;

	public Player(MusicModel model) {
		this.model = model;
	}

	public void run() {
		// TODO Auto-generated method stub
		running = true;
		pause = false;
		play();
	}

	public boolean openAudioFile(String name) {
		try {
			filename = name;
			// open the audio input stream
			File file = new File(filename);
			if (file.exists()) {
				AudioInputStream stream = AudioSystem
						.getAudioInputStream(new File(filename));

				format = stream.getFormat();
				stream.close();
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
			return false;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void exit() {
		running = false;
		pause = false;
	}

	public void playOrpause(boolean p) {
		pause = p;
	}

	private void play() {

		FileInputStream source = null;
		try {
			source = new FileInputStream(filename);

			int bufferSize = format.getFrameSize()
					* Math.round(format.getSampleRate() * format.getChannels()
							/ 10);
			byte[] buffer = new byte[bufferSize];
			// create a line to play to
			SourceDataLine line;
			try {
				DataLine.Info info = new DataLine.Info(SourceDataLine.class,
						format);
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(format, bufferSize);
			} catch (LineUnavailableException ex) {
				ex.printStackTrace();
				return;
			}
			// start the line
			line.start();
			// copy data to the line
			try {
				int numBytesRead = 0;
				while (numBytesRead != -1 & running) {
					numBytesRead = source.read(buffer, 0, buffer.length);
					if (numBytesRead != -1) {
						line.write(buffer, 0, numBytesRead);
						// do something when playing
					}
					while (pause) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// wait until all data is played, then close the line
			line.drain();
			line.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			source.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.stop();

	}
}

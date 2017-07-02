package AdventureLand.Manager;

import javax.sound.sampled.*;

public class AudioPlayer {

	private static Clip clip;
	
	public AudioPlayer(String s ) {
		try {
			
			AudioInputStream ais = 
					AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
					AudioFormat baseFormat = ais.getFormat();
					AudioFormat decodeFormat = new AudioFormat (
						AudioFormat.Encoding.PCM_SIGNED, 
						baseFormat.getSampleRate(), 
						16, 
						baseFormat.getChannels(),
						baseFormat.getChannels()*2, 
						baseFormat.getSampleRate(), 
						false
					);
					AudioInputStream dais = 
						AudioSystem.getAudioInputStream(
							decodeFormat, ais);
					clip = AudioSystem.getClip();
					clip.open(dais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		if (clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
	public static void setVolume(float f) {
		Clip c = null;
		try {
			c = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c == null) return;
		FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		vol.setValue(f);
	}
	
}

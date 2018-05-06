package MiniProject.MP3;

import java.io.File;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class MP3PlayerV4 {
	//private boolean isStart = false;

	/** 일시 정지 판별 */
	private boolean isPause = false;

	//private boolean isStop = false;

	BasicPlayer player = null;
	
	
	public MP3PlayerV4() {
		player = new BasicPlayer();
	}
	
	public void open(String path) {
		try {
			player.open(new File(path));
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
			try {
				player.play();
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void pause() {
		try {
			player.pause();
			isPause = true;
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			player.stop();
			isPause = false;
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void resume() {
		try {
			if(isPause) {
				player.resume();
				isPause = false;
			}
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getStatus() {
		return player.getStatus();
	}
	
	public boolean isPaused(){
		//return player.getStatus() == BasicPlayer.STOPPED;
		return isPause;
	}
	
		
		

}

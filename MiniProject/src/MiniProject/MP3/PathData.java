package MiniProject.MP3;

import java.awt.Image;

public class PathData {
	private String filePath;
	private String albumPath;
	private Image image = null;
	
	public PathData(String mp3Path, String albumPath) {
		filePath = mp3Path;
		this.albumPath = albumPath;
	}
	
	public void setImage(Image bi) {
		this.image = bi;
	}
	public Image getImage() {
		return image;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getAlbumPath() {
		return albumPath;
	}
	public void setAlbumPath(String albumPath) {
		this.albumPath = albumPath;
	}
	
	
}

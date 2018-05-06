package MiniProject.VO;

public class Song {
	private String title;
	private String singer;
	private int play_count;
	private int song_code;
	private int genre_code;
	private String genre_name;
	private String Lyric;
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getSinger() {return singer;}
	public void setSinger(String singer) {this.singer = singer;}
	public int getPlay_count() {return play_count;}
	public void setPlay_count(int play_count) {this.play_count = play_count;}
	public int getSong_code() {return song_code;}
	public void setSong_code(int song_code) {this.song_code = song_code;}
	public int getGenre_code() {return genre_code;}
	public void setGenre_code(int genre_code) {this.genre_code = genre_code;}
	public String getGenre_name() {	return genre_name;}
	public void setGenre_name(String genre_name) {this.genre_name = genre_name;}
	public String getLyric() {return Lyric;}
	public void setLyric(String lyric) {Lyric = lyric;}
	
	public Song() {
	}
	public Song(String title, String singer, String genre_name) {
		this.title = title;
		this.singer = singer;
		this.genre_name = genre_name;
	}
	public String toString() {
		return "노래 제목 : " + title + " / 가수 : " + singer + " / 장르 : " + genre_name;
	}
}

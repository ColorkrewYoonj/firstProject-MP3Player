package MiniProject.MP3;


import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import MiniProject.DAO.MiniProjectDAO;

public class MP3Tag {
	private MiniProjectDAO  dao = new MiniProjectDAO();

	
	/**
	 * 태그 정보를 Map 에 담아서 리턴
	 * @param filePath Mp3 경로
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getTagInfo(String filePath) {
		MP3File mp3 = null;
		HashMap<String, String> map = new HashMap<>();
		
		try {
			mp3 = (MP3File) AudioFileIO.read(new File(filePath));
			AbstractID3v2Tag tag2 = mp3.getID3v2Tag();
			map.put("title", tag2.getFirst(FieldKey.TITLE));
			map.put("singer", tag2.getFirst(FieldKey.ARTIST));
			map.put("genre_name", tag2.getFirst(FieldKey.GENRE));
			map.put("album", tag2.getFirst(FieldKey.ALBUM));
			map.put("years", tag2.getFirst(FieldKey.YEAR));
			map.put("composer", tag2.getFirst(FieldKey.COMPOSER));
			map.put("lyrics", tag2.getFirst(FieldKey.LYRICS));
			map.put("track", tag2.getFirst(FieldKey.TRACK));
			map.put("encoder", tag2.getFirst(FieldKey.ENCODER));
			map.put("language", tag2.getFirst(FieldKey.LANGUAGE));
			map.put("url_lyrics_site", tag2.getFirst(FieldKey.URL_LYRICS_SITE));
			map.put("duration", getDuration(filePath));
			map.put("bitRate", mp3.getMP3AudioHeader().getBitRate()+" kbit/s");
			map.put("bitPerSample", Integer.toString(mp3.getMP3AudioHeader().getBitsPerSample()) + " Bit");
			map.put("sampleRate", mp3.getMP3AudioHeader().getSampleRate()+" Hz");
			map.put("fileSize", getFileSize(mp3.getFile().length()));
			map.put("codec", mp3.getAudioHeader().getEncodingType());
			map.put("comment", tag2.getFirst(FieldKey.COMMENT));
			
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			//e.printStackTrace();
		}
		return map;

	}
	
	/**
	 * 파일 크기 구하기
	 * @param size
	 * @return
	 */
	public String getFileSize(String size) {
		String gubn[] = { "Byte", "KB", "MB" };
		String returnSize = new String();
		int gubnKey = 0;
		double changeSize = 0;
		long fileSize = 0;
		try {
			fileSize = Long.parseLong(size);
			for (int x = 0; (fileSize / (double) 1024) > 0; x++, fileSize /= (double) 1024) {
				gubnKey = x;
				changeSize = fileSize;
			}
			returnSize = changeSize + gubn[gubnKey];
		} catch (Exception ex) {
			returnSize = "0.0 Byte";
		}
		return returnSize;
	}

	private String getFileSize(long bytes) {
		double LengthbyUnit = (double)bytes;
		int Unit=0;
		while (LengthbyUnit > 1024 && Unit < 5) { // 단위 숫자로 나누고 한번 나눌 때마다 Unit 증가
			LengthbyUnit = LengthbyUnit / 1024;
			Unit++;
		}
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String fileSize = df.format(LengthbyUnit);
		
		switch(Unit) {
		case 0:
		fileSize += "Bytes";
		break;
		case 1:
			fileSize += " KB";
		break;
		case 2:
			fileSize += " MB";
		break;
		case 3:
			fileSize += " GB";
		break;
		case 4:
			fileSize += " TB";
		}
		return fileSize;
	}
	
	/**
	 * HashMap 객체로 넘어온 정보를 실제 MP3파일에 저장함
	 * @param filePath MP3 파일전체경로
	 * @param map
	 */
	public void setTagInfo(String filePath, HashMap<String, String> map) {
		MP3File mp3 = null;
		try {
			mp3 = (MP3File)AudioFileIO.read(new File(filePath));
			AbstractID3v2Tag tag2 = mp3.getID3v2Tag();
			tag2.setField(FieldKey.TITLE, map.get("title"));
			tag2.setField(FieldKey.ARTIST, map.get("singer"));
			tag2.setField(FieldKey.GENRE, map.get("genre_name"));
			tag2.setField(FieldKey.ALBUM, map.get("album"));
			tag2.setField(FieldKey.YEAR, map.get("years"));
			tag2.setField(FieldKey.COMPOSER, map.get("composer"));
			tag2.setField(FieldKey.LYRICS, map.get("lyrics"));
			tag2.setField(FieldKey.TRACK, map.get("track"));
			tag2.setField(FieldKey.ENCODER, map.get("encoder"));
			tag2.setField(FieldKey.LANGUAGE, map.get("language"));
			tag2.setField(FieldKey.URL_LYRICS_SITE, map.get("url_lyrics_site"));
			AudioFileIO.write(mp3); // 실제로 write 해야 파일에 저장됨
			dao.songRegister(map);
		} catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * 총 재생시간을 구하는 함수
	 * @param filePath MP3 파일 경로
	 * @return 문자열
	 */
	public String getDuration(String filePath) {
		String time = null;
		try {
			AudioFile audioFile = AudioFileIO.read(new File(filePath));
			int duration = audioFile.getAudioHeader().getTrackLength();
			int seconds = duration % 60;
			int minute = (duration - seconds) / 60;

			String mp3Second;
			
			if (seconds < 10) {
				mp3Second = "0" + seconds;
			} else {
				mp3Second = "" + seconds;
			}

			
			String mp3Minutes = null;
			if (minute < 10) {
				mp3Minutes = "0" + minute;
			} else {
				mp3Minutes = "" + minute;
			}
			time = mp3Minutes + ":" + mp3Second;
		} catch (Exception e) {
			System.out.print("ERROR " + e);
		}
		return time;
	}
}

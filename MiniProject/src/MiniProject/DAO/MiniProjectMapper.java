package MiniProject.DAO;

import java.util.ArrayList;
import java.util.HashMap;

import MiniProject.VO.Private;
import MiniProject.VO.Song;

public interface MiniProjectMapper {
	public int SignUp(Private p);
	public int LogIn(HashMap<String,String>map);
	public int Revise(HashMap<String,Object>map);
	public ArrayList<Song> genreRecommendation(String id);
	public ArrayList<Song> singerRecommendation(String id);
	public ArrayList<Song> genderRecommendation(String id);
	public ArrayList<Song> ageRecommendation(String id);
	public ArrayList<Song> totalRecommendation();
	public ArrayList<Song> totalGenreRecommendation(int genre_code);
	public int listRegister(HashMap<String,String>map);
	public int SongRegister(HashMap<String,String>map);
	public int SingerRegister(HashMap<String,String>map);
	public int overlapCheck(HashMap<String,String>map);
	public int SongOverlapCheck(HashMap<String,String>map);
	public int SingerOverlapCheck(HashMap<String,String>map);
	public int Play_Count(HashMap<String,String> map);
	public ArrayList<String> myList(String id);
	public int RemoveList(HashMap<String,String> map);
}

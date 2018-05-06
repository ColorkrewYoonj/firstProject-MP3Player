package MiniProject.DAO;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import MiniProject.DAO.MiniProjectMapper;
import MiniProject.DAO.MybatisConfig;
import MiniProject.VO.Private;
import MiniProject.VO.Song;

public class MiniProjectDAO {
	private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	private  SqlSession ss = null;
	private  MiniProjectMapper mapper = null;
	/////회원가입//////
	public boolean SignUp(Private p) {
		boolean res = false;
		int num = 0;
		try {
			ss =factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			num = mapper.SignUp(p);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {			
			if (num == 0) {
				res = false;
				ss.close();
			}else {
				res = true;
				ss.close();
			}
		}
		return res;
	}
	public boolean Revise(HashMap<String,Object> map) {
		boolean res = false;
		int num = 0;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			num = mapper.Revise(map);
			ss.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {			
			if (num == 0) {
				res = false;
				ss.close();
			}else {
				res = true;
				ss.close();
			}
		}
		return res;
	}
	/////로그인/////
	public boolean LogIn (String id, String password) {
		//SqlSession ss = null;
		//MiniProjectMapper mapper = null;
		HashMap<String,String> map = null;
		boolean res = false;
		int num = 0;
		try {
			ss = factory.openSession();
			mapper  = ss.getMapper(MiniProjectMapper.class);
			map = new HashMap<String,String>();
			map.put("id", id);
			map.put("password", password);
			num = mapper.LogIn(map);
			ss.commit();		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {			
			if (num == 0) {
				res = false;
				ss.close();
			}else {
				res = true;
				ss.close();
			}
		}
	return res;
	}
	/////추천/////
	//장르//
	public ArrayList<Song> genreRecommendation(String id){
		//SqlSession ss =null;
		//MiniProjectMapper mapper = null;
		ArrayList<Song> list = null;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.genreRecommendation(id);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
		return list;
	}
	//가수//
	public ArrayList<Song> singerRecommendation(String id){
		//SqlSession ss =null;
		//MiniProjectMapper mapper = null;
		ArrayList<Song> list = null;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.singerRecommendation(id);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
		return list;
	}
	//성별//
	public ArrayList<Song> genderRecommendation(String id){
		//SqlSession ss =null;
		//MiniProjectMapper mapper = null;
		ArrayList<Song> list = null;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.genderRecommendation(id);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
		return list;
	}
	//나이//
	public ArrayList<Song> ageRecommendation(String id){
		//SqlSession ss =null;
		//MiniProjectMapper mapper = null;
		ArrayList<Song> list = null;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.ageRecommendation(id);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
		return list;
	}
	//전체//
	public ArrayList<Song> totalRecommendation(){
		//SqlSession ss = null;
		//MiniProjectMapper mapper = null;
		ArrayList<Song> list = null;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.totalRecommendation();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
		return list;
	}
	//전체 장르별//
	public ArrayList<Song> totalGenreRecommendation(int genre_code){
		//SqlSession ss = null;
		//MiniProjectMapper mapper = null;
		ArrayList<Song> list = null;
		try {
			ss = factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.totalGenreRecommendation(genre_code);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
		return list;
	}
	/////DB등록/////
	
	public void songRegister(HashMap<String,String> map) {
		//SqlSession ss = null;
		//MiniProjectMapper mapper = null;
		int num = 0;
		int num2 = 0;
		try {
			ss =factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			num = mapper.SongOverlapCheck(map);
			num2 = mapper.SingerOverlapCheck(map);
			if (num2 == 0) {
				mapper.SingerRegister(map);
				ss.commit();
			}
			if (num == 0) {
				mapper.SongRegister(map);
				ss.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {	
			ss.close();
		}
	}
	/////MyList 등록/////
	public boolean listRegister(String path,String id, HashMap<String,String> map) {
		//SqlSession ss = null;
		//MiniProjectMapper mapper = null;
		boolean res = false;
		int num = 0;
		try {
			ss =factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			map.put("id",id);
			map.put("file_path", path);
			num = mapper.overlapCheck(map);
			if (num == 0) {
				mapper.listRegister(map);
			}
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {			
			if (num == 0) {
				res = true;
				ss.close();
			}else {
				res = false;
				ss.close();
			}
		}
		return res;
	}
	/////카운트/////
	public void Play_Count(HashMap<String,String> map) {
		try {
			ss =factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			mapper.Play_Count(map);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {	
			ss.close();
		}
	}
	/////마리이스트/////
	public ArrayList<String> myList(String id){
		ArrayList<String> list = null;
		try {
			ss =factory.openSession();
			mapper = ss.getMapper(MiniProjectMapper.class);
			list = mapper.myList(id);
			ss.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {	
			ss.close();
		}
		return list;
	}
	public void RemoveList(String path, String id) {
		try {
			ss = factory.openSession();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("path", path);
			map.put("id",id);
			mapper = ss.getMapper(MiniProjectMapper.class);
			mapper.RemoveList(map);
			ss.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ss.close();
		}
	}
}
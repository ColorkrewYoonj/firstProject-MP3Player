package MiniProject.UI;

import java.util.*;
import java.io.*;
import MiniProject.DAO.MiniProjectDAO;
import MiniProject.MP3.MP3PlayerV4;
import MiniProject.MP3.SearchFile;
import MiniProject.MP3.SongInfoFrame;
import MiniProject.VO.Private;
import MiniProject.VO.Song;
import etc.Encryption;


public class MiniProjectUI {
	private Scanner sc = new Scanner(System.in);
	private MiniProjectDAO  dao = new MiniProjectDAO();
	private Encryption ec = new Encryption();
	private static String login = null;
	private MP3PlayerV4 player = new MP3PlayerV4();
	private SongInfoFrame siff = new SongInfoFrame();
	private String mp3FolderPath = ".\\MP3";
	
	public MiniProjectUI() {
		
		int no = 0;
		while(true) {
			System.out.println();
			Manu();
			try {
				System.out.println("명령어를 입력해주세요");
				no = sc.nextInt();
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			switch (no) {
			case 0:
				clear();
				if (login == null) {
					SignUp();
					break;
				}else {
					Revise();
					break;
				}
			case 1:
				clear();
				if (login == null) {
					LogIn();
					break;
				}else {
					LogOut();
					break;
				}
			case 2:
				mp3Tag();
				clear();
				break;
			case 3:
				clear();
				if (login == null) {
					System.out.println("로그인 해주세요");
					//break;
				}else {
					myList();
					//break;
				}
				break;
			case 4: 
				if (login == null) {
					System.out.println("로그인 해주세요");
					//break;
				}else {
					myListDelete();
					//break;
				}
				break;
			case 5:
				clear();
				recommendationManu();
				break;
			case 6:
				System.exit(0);
			default:
				break;
			}
		}
	}
	void Manu() {
		if (login == null) {
			System.out.println("[♬♪MP3 PLAYER♬♪] - 로그인 해주세요");
			System.out.println("┌───────────────────┐");
			System.out.println("│0. 회원 가입                                    ");	
			System.out.println("│1. 로그인                                        ");	
			System.out.println("│2. MP3 PLAYER                                ");	
			System.out.println("│3. My List                                       ");
			System.out.println("│4. My List 삭제                                 ");
			System.out.println("│5. 추천 노래                                      ");	
			System.out.println("│6. 종료                                   ");
			System.out.println("└───────────────────┘");
		}else {
			System.out.println("[♬♪MP3 PLAYER♬♪] - 안녕하세요." + login+"님");
			System.out.println("┌────────────────────────┐");
			System.out.println("│0. 회원정보 수정                                            ");	
			System.out.println("│1. 로그 아웃                                                 ");	
			System.out.println("│2. 음악 재생                                                 ");	
			System.out.println("│3. My List                                                    ");	
			System.out.println("│4. My List 삭제                                			   ");
			System.out.println("│5. 추천 노래                                      			   ");	
			System.out.println("│6. 종료                                   ");
			System.out.println("└───────────────────┘");
		}
	}			
	
	//회원가입 성별에 사용하는 enum 
	public enum Gender{  
		M , F
	}
	//회원가입 절차(잘못된 입력이 나올경우 취소)
	void SignUp() {
		char pass[] = null;
		try {
			sc.nextLine();
			Private p = null;
			Gender gender  = null;
			System.out.println("[회원 가입]");
			System.out.print("아이디 : ");
			String id  =  sc.nextLine();
			System.out.print("비밀번호 : ");
			Console cons = System.console();
			pass = cons.readPassword();
			String enter = new String(pass);
			String password = ec.encrypton(enter);
			System.out.print("이름 : ");
			String name = sc.nextLine();
			System.out.print("성별 : ");
			enter = sc.nextLine();
			if (enter.equals("남")||enter.toUpperCase().equals("M")|| enter.toUpperCase().equals("MALE")|| enter.equals("남자")) {
				gender = Gender.M; 
			}else if (enter.equals("여")||enter.toUpperCase().equals("F")||enter.toUpperCase().equals("FEMALE")||enter.equals("여자")) {
				gender = Gender.F;
			}else {
				System.out.println("잘못된 입력 입니다.");
				return;
			}
			System.out.print("나이 : ");
			int age = sc.nextInt();
			p = new Private(id, password, name,gender, age);
			if (dao.SignUp(p)) {
				System.out.println("회원 가입에 성공하셨습니다.");
			}else {
				System.out.println("회원 가입에 실패하셨습니다.");
			}
		}catch(Exception e) {
			System.out.println("잘못된 입력 입니다.");
		}
	}
	//로그인 절차 (아이디와 비밀번호를 DB에 보내서 둘이 동시에 일치하는 ROW가 있을경우)
	void LogIn() {
		char pass[] = null;
		sc.nextLine();
		System.out.println("[로그인]");
		System.out.print("아이디 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호 : ");
		Console cons = System.console();
		pass = cons.readPassword();
		String enter = new String(pass);
		String password = ec.encrypton(enter);
		if (dao.LogIn(id, password)) {
			login = id;
			System.out.println("로그인에 성공하셨습니다.");
		}else {
			System.out.println("로그인에 실패하셨습니다.");
		}
	}
	void LogOut() {
		System.out.println("로그아웃 되었습니다.");
		login = null;
	}
	void Revise() {
		char pass[] = null;
		try {
		sc.nextLine();
		HashMap<String,Object> map = new HashMap<String,Object>();
		Gender gender  = null;
		System.out.print("비밀번호 : ");
		Console cons = System.console();
		pass = cons.readPassword();
		String enter = new String(pass);
		String password = ec.encrypton(enter);
		System.out.print("이름 : ");
		String name = sc.nextLine();
		System.out.print("성별 : ");
		enter = sc.nextLine();
		if (enter.equals("남")||enter.toUpperCase().equals("M")|| enter.toUpperCase().equals("MALE")|| enter.equals("남자")) {
			gender = Gender.M; 
		}else if (enter.equals("여")||enter.toUpperCase().equals("F")||enter.toUpperCase().equals("FEMALE")||enter.equals("여자")) {
			gender = Gender.F;
		}else {
			System.out.println("잘못된 입력 입니다.");
			return;
		}
		System.out.print("나이 : ");
		int age = sc.nextInt();
		map.put("password", password);
		map.put("name",name);
		map.put("gender", gender);
		map.put("age", age);
		map.put("id", login);
		if (dao.Revise(map)) {
			System.out.println("수정되었습니다");
		}
		}catch(Exception e){
			System.out.println("잘못된 입력 입니다");
		}
	}
	//노래 추천
	void recommendationManu() {
		System.out.println("1. 맞춤 추천  2.통계 추천 ");
		int no = 0;
		try {
			no = sc.nextInt();
		} catch (Exception e) {
			sc.nextLine();
			System.out.println("잘못 입력하였습니다.");
		}
		switch (no) {
		case 1 :
			clear();
			if (login == null) {
				System.out.println("로그인이 필요합니다.");
				break;
			}
			genreRecommendation();
			singerRecommendtaion();
			genderRecommendation();
			ageRecommedation();
			break;
		case 2 :
			totalRecommendation();
			totalGenreRecommendation();
			break;
		}
	}
	void genreRecommendation() {	
		ArrayList<Song> list = null;
		list = dao.genreRecommendation(login);
		if (list.isEmpty()) {
			System.out.println("MyList에 좋아하는 노래를 등록해 보세요!");
		}else {
			System.out.println("[회원님이 좋아할 <장르>의 추천곡]");
			for (Song song : list) {
				System.out.print(song);
				System.out.println();
			}
			System.out.println();
		}
	}
	void singerRecommendtaion() {
		ArrayList<Song> list = null;
		list = dao.singerRecommendation(login);
		if (list.isEmpty()) {
		}else {
			System.out.println("[회원님이 좋아할 <가수>의 추천곡]");
			for (Song song : list) {
				System.out.print(song);
				System.out.println();
			}
			System.out.println();
		}
	}
	void genderRecommendation() {
		ArrayList<Song> list = null;
		list = dao.genderRecommendation(login);
		if (list.isEmpty()) {
		}else {
			System.out.println("[다른 회원들의 추천곡<성별>]");
			for (Song song : list) {
				System.out.print(song);
				System.out.println();
			}
			System.out.println();
		}
	}
	void ageRecommedation() {
		ArrayList<Song> list = null;
		list = dao.ageRecommendation(login);
		if (list.isEmpty()) {
		}else {
			System.out.println("[다른 회원들의 추천곡<연령>]");
			for (Song song : list) {
				System.out.print(song);
				System.out.println();
			}
			System.out.println();
		}
	}
	void totalRecommendation() {
		ArrayList<Song> list = null;
		list = dao.totalRecommendation();
		System.out.println("[최신 인기곡 베스트3]");
		for (Song song : list) {
			System.out.print(song);
			System.out.println();
		}
		System.out.println();
	}
	void totalGenreRecommendation() {
		ArrayList<Song> list = null;
		System.out.println("[장르별 최신 인기곡 베스트3]");
		for (int i = 1; i <= 7; i++) {
			list = dao.totalGenreRecommendation(i);
			System.out.println("<"+list.get(0).getGenre_name()+">");
			for (Song song : list) {
				System.out.print(song);
				System.out.println();
			}
			System.out.println();
		}
	}
	void listRegister(String path, HashMap<String,String> map) {
			if (dao.listRegister(path,login,map)) {
				System.out.println("등록되었습니다.");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				System.out.println("등록에 실패했습니다.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	void songRegister(HashMap<String,String> map) {
		dao.songRegister(map);
	}
	void mp3Player(String path, HashMap<String,String> map, int mode) {
		int option = 0;
		
		player.open(path);
		player.start();
		Play_Count(map);
		
		while (true) {
			clear();
			System.out.println("" + (player.isPaused() ? "일시정지 중" : "음악 재생중"));
			
			System.out.println("" + (!player.isPaused() ? "1. 일시정지" : "1. 다시 재생") + (mode == 0 ? ", 2. My List 등록, 3. 정지(상위메뉴)" : ", 3. 정지(상위메뉴)"));
			try {
				option = sc.nextInt();
				if (option < 1 && option > 2) {
					continue;
				}
				if (option == 1) {
					if (player.isPaused()) {
						player.resume();
					} else {
						player.pause();
					}
				}else if(option == 2 && mode == 0) {
					if (login == null) {
						System.out.println("로그인 해주세요");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						listRegister(path, map);
					}
				}
				else if (option == 3) {
					player.stop();
					return;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자만 입력해줘요");
			} finally {
				sc.nextLine();
			}
		}
	}
	void mp3Tag() {
		SearchFile sf = new SearchFile(mp3FolderPath);
		sf.setExtensionName("mp3");
		sf.Start();
		ArrayList<String> list = sf.getFilePathList();
		
		siff = new SongInfoFrame();
		siff.setPathList(list);
		
		while(true) {
			clear();
			int option = 0;
			System.out.println("===목록====");
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				int pos = s.lastIndexOf( "\\" );
				String ext = s.substring(pos+1);
				System.out.println("[" + (i+1) + "]" + ext);
			}
			System.out.println("[0] 종료");
			System.out.println();
			System.out.println("실행할 MP3파일을 고르시오");
			
			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력하세요.");
				continue;
			} finally {
				sc.nextLine();
			}
			
			if(option > list.size() || option < 0) {
				System.out.println("인덱스 초과");
				continue;
			}
			if(option ==0) {
				return;
			}
			
			siff.init(option);
			siff.OpenFrame();
			songRegister(siff.getTagInfo());
			mp3Player( list.get(option-1), siff.getTagInfo(), 0);
			siff.dispose();
		}
	}
	void Play_Count(HashMap<String,String> map) {
		dao.Play_Count(map);
	}
	
	void myListDelete() {
		ArrayList<String> list = dao.myList(login);
		if (list.contains(null)) {
			System.out.println("즐겨찾기 된 노래가 없습니다");
			return;
		}
		
		while (true) {
			clear();
			int option = 0;
			System.out.println("====목록====");
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				int pos = s.lastIndexOf("\\");
				String ext = s.substring(pos + 1);

				if (new File(list.get(i)).exists())
					System.out.println("[" + (i + 1) + "]" + ext);
				else
					System.out.println("[" + (i + 1) + "]" + ext + " ◀◀◀ 경로에 파일이 존재하지 않습니다.");
			}
			System.out.println("[0] 종료");
			System.out.println();

			System.out.println("삭제할 MP3파일을 고르시오");

			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력하세요.");
				continue;
			} finally {
				sc.nextLine();
			}

			if (option > list.size() || option < 0) {
				System.out.println("인덱스 초과");
				continue;
			}
			if (option == 0) {
				return;
			}
			// 리스트에서 삭제
			String path = list.get(option-1);
			list.remove(option-1);
			dao.RemoveList(path, login);
		}
	}
	
	void myList() {
		ArrayList<String> list = dao.myList(login);
		if (list.contains(null)) {
			System.out.println("즐겨찾기 된 노래가 없습니다");
			return;
		}
		
		while(true) {
			clear();
			int option = 0;
			System.out.println("===목록====");
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				int pos = s.lastIndexOf( "\\" );
				String ext = s.substring(pos+1);
				
				if(new File(list.get(i)).exists())
					System.out.println("[" + (i+1) + "]" + ext);
				else
					System.out.println("[" + (i+1) + "]" + ext + " ◀◀◀ 경로에 파일이 존재하지 않습니다.");
			}
			System.out.println("[0] 종료");
			System.out.println();
			System.out.println("실행할 MP3파일을 고르시오");
			
			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력하세요.");
				continue;
			} finally {
				sc.nextLine();
			}
			
			if(option > list.size() || option < 0) {
				System.out.println("인덱스 초과");
				continue;
			}
			if(option ==0) {
				return;
			}
			
			if(!new File(list.get(option-1)).exists()){
				System.out.println("존재하지 않는 파일 입니다.");
				continue;
			}
			
			siff = new SongInfoFrame();
			siff.setPathList(list);
			siff.init(option);
			siff.OpenFrame();
			
				songRegister(siff.getTagInfo());
				mp3Player(list.get(option - 1), siff.getTagInfo(), 1);
				siff.dispose();

		}
	}
	void clear() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}


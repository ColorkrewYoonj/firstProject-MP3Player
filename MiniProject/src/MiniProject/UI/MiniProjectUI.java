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
				System.out.println("��ɾ �Է����ּ���");
				no = sc.nextInt();
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("�߸��� �Է��Դϴ�.");
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
					System.out.println("�α��� ���ּ���");
					//break;
				}else {
					myList();
					//break;
				}
				break;
			case 4: 
				if (login == null) {
					System.out.println("�α��� ���ּ���");
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
			System.out.println("[�ݢ�MP3 PLAYER�ݢ�] - �α��� ���ּ���");
			System.out.println("������������������������������������������");
			System.out.println("��0. ȸ�� ����                                    ");	
			System.out.println("��1. �α���                                        ");	
			System.out.println("��2. MP3 PLAYER                                ");	
			System.out.println("��3. My List                                       ");
			System.out.println("��4. My List ����                                 ");
			System.out.println("��5. ��õ �뷡                                      ");	
			System.out.println("��6. ����                                   ");
			System.out.println("������������������������������������������");
		}else {
			System.out.println("[�ݢ�MP3 PLAYER�ݢ�] - �ȳ��ϼ���." + login+"��");
			System.out.println("����������������������������������������������������");
			System.out.println("��0. ȸ������ ����                                            ");	
			System.out.println("��1. �α� �ƿ�                                                 ");	
			System.out.println("��2. ���� ���                                                 ");	
			System.out.println("��3. My List                                                    ");	
			System.out.println("��4. My List ����                                			   ");
			System.out.println("��5. ��õ �뷡                                      			   ");	
			System.out.println("��6. ����                                   ");
			System.out.println("������������������������������������������");
		}
	}			
	
	//ȸ������ ������ ����ϴ� enum 
	public enum Gender{  
		M , F
	}
	//ȸ������ ����(�߸��� �Է��� ���ð�� ���)
	void SignUp() {
		char pass[] = null;
		try {
			sc.nextLine();
			Private p = null;
			Gender gender  = null;
			System.out.println("[ȸ�� ����]");
			System.out.print("���̵� : ");
			String id  =  sc.nextLine();
			System.out.print("��й�ȣ : ");
			Console cons = System.console();
			pass = cons.readPassword();
			String enter = new String(pass);
			String password = ec.encrypton(enter);
			System.out.print("�̸� : ");
			String name = sc.nextLine();
			System.out.print("���� : ");
			enter = sc.nextLine();
			if (enter.equals("��")||enter.toUpperCase().equals("M")|| enter.toUpperCase().equals("MALE")|| enter.equals("����")) {
				gender = Gender.M; 
			}else if (enter.equals("��")||enter.toUpperCase().equals("F")||enter.toUpperCase().equals("FEMALE")||enter.equals("����")) {
				gender = Gender.F;
			}else {
				System.out.println("�߸��� �Է� �Դϴ�.");
				return;
			}
			System.out.print("���� : ");
			int age = sc.nextInt();
			p = new Private(id, password, name,gender, age);
			if (dao.SignUp(p)) {
				System.out.println("ȸ�� ���Կ� �����ϼ̽��ϴ�.");
			}else {
				System.out.println("ȸ�� ���Կ� �����ϼ̽��ϴ�.");
			}
		}catch(Exception e) {
			System.out.println("�߸��� �Է� �Դϴ�.");
		}
	}
	//�α��� ���� (���̵�� ��й�ȣ�� DB�� ������ ���� ���ÿ� ��ġ�ϴ� ROW�� �������)
	void LogIn() {
		char pass[] = null;
		sc.nextLine();
		System.out.println("[�α���]");
		System.out.print("���̵� : ");
		String id = sc.nextLine();
		System.out.print("��й�ȣ : ");
		Console cons = System.console();
		pass = cons.readPassword();
		String enter = new String(pass);
		String password = ec.encrypton(enter);
		if (dao.LogIn(id, password)) {
			login = id;
			System.out.println("�α��ο� �����ϼ̽��ϴ�.");
		}else {
			System.out.println("�α��ο� �����ϼ̽��ϴ�.");
		}
	}
	void LogOut() {
		System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
		login = null;
	}
	void Revise() {
		char pass[] = null;
		try {
		sc.nextLine();
		HashMap<String,Object> map = new HashMap<String,Object>();
		Gender gender  = null;
		System.out.print("��й�ȣ : ");
		Console cons = System.console();
		pass = cons.readPassword();
		String enter = new String(pass);
		String password = ec.encrypton(enter);
		System.out.print("�̸� : ");
		String name = sc.nextLine();
		System.out.print("���� : ");
		enter = sc.nextLine();
		if (enter.equals("��")||enter.toUpperCase().equals("M")|| enter.toUpperCase().equals("MALE")|| enter.equals("����")) {
			gender = Gender.M; 
		}else if (enter.equals("��")||enter.toUpperCase().equals("F")||enter.toUpperCase().equals("FEMALE")||enter.equals("����")) {
			gender = Gender.F;
		}else {
			System.out.println("�߸��� �Է� �Դϴ�.");
			return;
		}
		System.out.print("���� : ");
		int age = sc.nextInt();
		map.put("password", password);
		map.put("name",name);
		map.put("gender", gender);
		map.put("age", age);
		map.put("id", login);
		if (dao.Revise(map)) {
			System.out.println("�����Ǿ����ϴ�");
		}
		}catch(Exception e){
			System.out.println("�߸��� �Է� �Դϴ�");
		}
	}
	//�뷡 ��õ
	void recommendationManu() {
		System.out.println("1. ���� ��õ  2.��� ��õ ");
		int no = 0;
		try {
			no = sc.nextInt();
		} catch (Exception e) {
			sc.nextLine();
			System.out.println("�߸� �Է��Ͽ����ϴ�.");
		}
		switch (no) {
		case 1 :
			clear();
			if (login == null) {
				System.out.println("�α����� �ʿ��մϴ�.");
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
			System.out.println("MyList�� �����ϴ� �뷡�� ����� ������!");
		}else {
			System.out.println("[ȸ������ ������ <�帣>�� ��õ��]");
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
			System.out.println("[ȸ������ ������ <����>�� ��õ��]");
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
			System.out.println("[�ٸ� ȸ������ ��õ��<����>]");
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
			System.out.println("[�ٸ� ȸ������ ��õ��<����>]");
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
		System.out.println("[�ֽ� �α�� ����Ʈ3]");
		for (Song song : list) {
			System.out.print(song);
			System.out.println();
		}
		System.out.println();
	}
	void totalGenreRecommendation() {
		ArrayList<Song> list = null;
		System.out.println("[�帣�� �ֽ� �α�� ����Ʈ3]");
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
				System.out.println("��ϵǾ����ϴ�.");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				System.out.println("��Ͽ� �����߽��ϴ�.");
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
			System.out.println("" + (player.isPaused() ? "�Ͻ����� ��" : "���� �����"));
			
			System.out.println("" + (!player.isPaused() ? "1. �Ͻ�����" : "1. �ٽ� ���") + (mode == 0 ? ", 2. My List ���, 3. ����(�����޴�)" : ", 3. ����(�����޴�)"));
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
						System.out.println("�α��� ���ּ���");
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
				System.out.println("���ڸ� �Է������");
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
			System.out.println("===���====");
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				int pos = s.lastIndexOf( "\\" );
				String ext = s.substring(pos+1);
				System.out.println("[" + (i+1) + "]" + ext);
			}
			System.out.println("[0] ����");
			System.out.println();
			System.out.println("������ MP3������ ���ÿ�");
			
			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("���ڸ� �Է��ϼ���.");
				continue;
			} finally {
				sc.nextLine();
			}
			
			if(option > list.size() || option < 0) {
				System.out.println("�ε��� �ʰ�");
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
			System.out.println("���ã�� �� �뷡�� �����ϴ�");
			return;
		}
		
		while (true) {
			clear();
			int option = 0;
			System.out.println("====���====");
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				int pos = s.lastIndexOf("\\");
				String ext = s.substring(pos + 1);

				if (new File(list.get(i)).exists())
					System.out.println("[" + (i + 1) + "]" + ext);
				else
					System.out.println("[" + (i + 1) + "]" + ext + " ������ ��ο� ������ �������� �ʽ��ϴ�.");
			}
			System.out.println("[0] ����");
			System.out.println();

			System.out.println("������ MP3������ ���ÿ�");

			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("���ڸ� �Է��ϼ���.");
				continue;
			} finally {
				sc.nextLine();
			}

			if (option > list.size() || option < 0) {
				System.out.println("�ε��� �ʰ�");
				continue;
			}
			if (option == 0) {
				return;
			}
			// ����Ʈ���� ����
			String path = list.get(option-1);
			list.remove(option-1);
			dao.RemoveList(path, login);
		}
	}
	
	void myList() {
		ArrayList<String> list = dao.myList(login);
		if (list.contains(null)) {
			System.out.println("���ã�� �� �뷡�� �����ϴ�");
			return;
		}
		
		while(true) {
			clear();
			int option = 0;
			System.out.println("===���====");
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				int pos = s.lastIndexOf( "\\" );
				String ext = s.substring(pos+1);
				
				if(new File(list.get(i)).exists())
					System.out.println("[" + (i+1) + "]" + ext);
				else
					System.out.println("[" + (i+1) + "]" + ext + " ������ ��ο� ������ �������� �ʽ��ϴ�.");
			}
			System.out.println("[0] ����");
			System.out.println();
			System.out.println("������ MP3������ ���ÿ�");
			
			try {
				option = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("���ڸ� �Է��ϼ���.");
				continue;
			} finally {
				sc.nextLine();
			}
			
			if(option > list.size() || option < 0) {
				System.out.println("�ε��� �ʰ�");
				continue;
			}
			if(option ==0) {
				return;
			}
			
			if(!new File(list.get(option-1)).exists()){
				System.out.println("�������� �ʴ� ���� �Դϴ�.");
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


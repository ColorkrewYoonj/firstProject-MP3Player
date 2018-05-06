package MiniProject.MP3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

public class SongInfoFrame extends JFrame implements ActionListener{
	private String albumImagePath = ".\\mp3\\notAlbum.jpg";
	//private String DefaultImagePath = "D:\\user\\Desktop\\mp3\\Penguins.jpg";
	private String DefaultImagePath = ".\\MP3\\notAlbum.png";

	private AlbumImagePanel aip = null;
	private TagInfoPanel tifp = null;
	private LyricsPanel lp = null;
	
	private MP3Tag tag = null;
	
	private HashMap<String, String> map = null;
	
	private JPanel buttonPanel = null;
	private JButton editButton = null;
	
	private JButton refreshButton = null;
	private boolean isEditMode = false;
	
	private int indexNum = 0;
	
	private boolean isAddList = false;
	
	/** �ε��� 0���� �⺻ �̹��� */
	private ArrayList<PathData> pathList = null;
	
	
//	private String decoding = "EUC_KR";
//	private String encoding = "ISO-8859-1";
	
	private String filePath = null;
	
	public SongInfoFrame() {
		pathList = new ArrayList<>();
		initDefaultAlbumImage();
	}
	
	public void initDefaultAlbumImage() {
		PathData pd = new PathData("none", DefaultImagePath);
		aip = new AlbumImagePanel();
		pd.setImage(aip.getAlbumImage(DefaultImagePath));
		pathList.add(pd);
	}
	
	public HashMap<String, String> getTagInfo(){
		return map;
	}
	
	public void setPathList(ArrayList<String> mp3List) {
		if(isAddList) {
			pathList.clear();
			initDefaultAlbumImage();
		}
		
		isAddList = true;
		PathData pd = null;
		String imagePath = null;
		for (String path : mp3List) {
				if (getAlbumImage(path))
					imagePath = albumImagePath;
				else
					imagePath = path + "_albumImage.png";
				pd = new PathData(path, imagePath);
				pd.setImage(aip.getAlbumImage(imagePath));
				pathList.add(pd);
		}
	}
	
	public void init(int index) {
		this.filePath = pathList.get(index).getFilePath();
		this.albumImagePath = pathList.get(index).getAlbumPath();
		tag = new MP3Tag();
		tifp = new TagInfoPanel();
		lp = new LyricsPanel();

		map = tag.getTagInfo(filePath);
		setTagInfo(map);
		
		aip.setImage(pathList.get(index).getImage());
		
		indexNum = index;
	}
	
	/**
	 * ���ڿ��� ���ڵ� ���� Ȯ��(cmd ���)
	 * @param str
	 */
	public void findEncoding(String str) {
		String orininalStr = str;
		String[] charSet = { "utf-8", "euc-kr", "ksc5601", "iso-8859-1", "x-windows-949" };
		
		for(int i = 0; i < charSet.length; i++){
			for(int j = 0; j < charSet.length; j++) {
				try {
					System.out.println("[" + charSet[i] + "," + charSet[j] + "] = "
							+ new String(orininalStr.getBytes(charSet[i]), charSet[j]));
				} catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	public void fieldReset(int index) {
		if(!map.isEmpty())
			map.clear();
		map = tag.getTagInfo(pathList.get(index).getFilePath());
		setTagInfo(map);
		
		aip.setImage(pathList.get(index).getImage());
	}
	
	public void setIndexNum(int n) {
		indexNum = n;
	}
	
	/**
	 * ������ ����
	 */
	public void OpenFrame() {
		
		setTitle("�뷡 ����");
		setSize(500, 800);
				
		// ���� ��ư��
		buttonPanel = new JPanel();
		editButton = new JButton("����");
		buttonPanel.add(editButton);
		editButton.addActionListener(this);
		
		refreshButton = new JButton("���ΰ�ħ");
		buttonPanel.add(refreshButton);
		refreshButton.addActionListener(this);
				
		aip.setBounds(0, 0, aip.getWidth(), aip.getHeight());
		
		// ���� �г��� ���
		add(aip, BorderLayout.LINE_END);
		add(lp, BorderLayout.CENTER);
		add(tifp, BorderLayout.LINE_START);
		
		// ������ư�� �г��� �Ʒ�
		add(buttonPanel, "South");
		
		pack();
		
		// ������ ���̰� ��
		setVisible(true);
		
		
		// â ������ ���α׷� ����
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	// ��ư ���
//	private void addButton(String str, Container target) {
//		JButton button = new JButton(str);
//		button.addActionListener(this);
//		target.add(button);
//	}
	
	// �̺�Ʈ ó��
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == editButton) {
			if(!isEditMode) {
				tifp.setEditMode(isEditMode);
				lp.textArea.setEditable(true);
				isEditMode = true;
				editButton.setText("�Ϸ�");
			}
			else {
				tifp.setEditMode(isEditMode);
				lp.textArea.setEditable(false);
				editButton.setText("����");
				isEditMode = false;
				inputTagInfo(filePath);
			}
		}
		else if(e.getSource() == refreshButton) {
			refresh(indexNum);
		}
	}
	
	/*
	public void refresh() {
		fieldReset(filePath);
		repaint();
	}
	*/
	
	/*
	public void refresh(String filePath) {
		this.filePath = filePath;
		fieldReset(filePath);
		//aip.refresh();
		repaint();
	}
	*/
	
	public void refresh(int index) {
		fieldReset(index);
		repaint();
		indexNum = index;
		if(!isDisplayable())
			setVisible(true);
	}
	
	private void inputTagInfo(String filePath) {
		if(!map.isEmpty())
			map.clear();
		map = tifp.getTagInfo();
		map.put("lyrics", lp.getLyrics());
		tag.setTagInfo(filePath, map);
	}
	
	private boolean getAlbumImage(String filePath) {
		MP3File mp3 = null;
		
		//�̹� �ٹ������� �����ϸ� ����
		File checkFile = new File(filePath + "_albumImage.png");
		if(checkFile.exists()) {
			albumImagePath = checkFile.getAbsolutePath();
			//System.out.println("�̹� �ٹ��̹��������� ������");
			return false;
		}
		
		try {
			File f = new File(filePath);
			mp3 = (MP3File)AudioFileIO.read(f);
			AbstractID3v2Tag tag2 = mp3.getID3v2Tag();
			
			// Mp3 ���Ͽ��� �̹��� ����Ʈ�迭�� ��������
			if(tag2.getFirstArtwork() != null) {
				byte[] img = tag2.getFirstArtwork().getBinaryData();
				// ���Ϸ� ����
				albumImagePath = filePath + "_albumImage.png";
				FileImageOutputStream fios = new FileImageOutputStream(new File(albumImagePath));
				fios.write(img);
				fios.close();
			}
			else {
				//System.out.println("�̹��� ���� ����");
				albumImagePath = DefaultImagePath;
				indexNum = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * @param str ���ڿ�
	 * @param mode 1. ����, 2. ����, 3. �帣, 4.�ٹ�, 5. ����, 6. �۰
	 */
	public void setTagInfo(String str, int mode) {
		tifp.setTagInfo(str, mode);
	}
	
	/**
	 * �±������� ���� Map�� �Ѱܼ� ������
	 * @param map
	 */
	public void setTagInfo(HashMap<String, String> map) {
		tifp.setTagInfo(map);
		lp.setLyrics(map.get("lyrics"));
	}
	/**
	 * ���� ����
	 * @param str
	 */
	public void setLyrics(String str) {
		lp.setLyrics(str);
	}
	
	class LyricsPanel extends JPanel{
		private JTextArea textArea = null;
		private JScrollPane scrollPane = null;
		
		//private JButton editButton = null;
		//private ButtonListener listener = new ButtonListener();
		
		public void setLyrics(String str) {
			textArea.setText(str);
		}
		
		public String getLyrics() {
			return textArea.getText();
		}
		
		public LyricsPanel() {
			JPanel panel = new JPanel();
						
			textArea = new JTextArea(15, 20);
			textArea.setEditable(false);
			scrollPane = new JScrollPane(textArea, 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			panel.add(scrollPane);
			
			add(panel);
			
			pack();
			setVisible(true);
		}
}
	
	/**
	 * �±� �⺻ ���� ��¿� �г�
	 * @author user
	 *
	 */
	class TagInfoPanel extends JPanel{
		private JTextField title = null;
		private JTextField duration = null;
		private JTextField singer = null;
		private JTextField genre = null;
		private JTextField album = null;
		private JTextField year = null;
		private JTextField bitRate = null;
		private JTextField sampleRate = null;
		private JTextField songWriter = null;
		//private JTextArea textArea = null;
		private JTextField track = null;
		private JTextField encoder = null;
		private JTextField language = null;
		private JTextField url_lyrics_site = null;
		
//		private JTextField bitRate = null;
		private JTextField bitPerSample = null;
//		private JTextField sampleRate = null;
		private JTextField fileSize = null;
		private JTextField codec = null;
		private JTextField comment = null;
		
		
		//private JButton editButton = null;
		//private ButtonListener listener = new ButtonListener();
		
//		private String decoding = "EUC_KR";
//		private String encoding = "ISO-8859-1";
		
		
		public TagInfoPanel() {
			JPanel panel = new JPanel();
			
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			panel.add(new JLabel("����: "));
			title = new JTextField();
			title.setEditable(false);
			panel.add(title);
			
			panel.add(new JLabel("��Ƽ��Ʈ: "));
			singer = new JTextField(20);
			singer.setEditable(false);
			panel.add(singer);
			
			panel.add(new JLabel("�帣: "));
			genre = new JTextField();
			genre.setEditable(false);
			panel.add(genre);
			
			panel.add(new JLabel("�ٹ�: "));
			album = new JTextField();
			album.setEditable(false);
			panel.add(album);
			
			panel.add(new JLabel("����: "));
			year = new JTextField();
			year.setEditable(false);
			panel.add(year);
			
			panel.add(new JLabel("�۰: "));
			songWriter = new JTextField();
			songWriter.setEditable(false);
			panel.add(songWriter);
			
			panel.add(new JLabel("����: "));
			comment = new JTextField();
			comment.setEditable(false);
			panel.add(comment);
			
			panel.add(new JLabel("����ð�: "));
			duration = new JTextField();
			duration.setEditable(false);
			panel.add(duration);
			
			panel.add(new JLabel("Ʈ��: "));
			track = new JTextField();
			track.setEditable(false);
			panel.add(track);
			
			panel.add(new JLabel("Encoder: "));
			encoder = new JTextField();
			encoder.setEditable(false);
			panel.add(encoder);
			
			panel.add(new JLabel("���: "));
			language = new JTextField();
			language.setEditable(false);
			panel.add(language);
			
			panel.add(new JLabel("���� �ּ�: "));
			url_lyrics_site = new JTextField();
			url_lyrics_site.setEditable(false);
			panel.add(url_lyrics_site);
			
			panel.add(new JLabel("��Ʈ����Ʈ: "));
			bitRate = new JTextField();
			bitRate.setEditable(false);
			panel.add(bitRate);
			
			panel.add(new JLabel("���ø� ��Ʈ�� : "));
			bitPerSample = new JTextField();
			bitPerSample.setEditable(false);
			panel.add(bitPerSample);
			
			panel.add(new JLabel("���ļ�: "));
			sampleRate = new JTextField();
			sampleRate.setEditable(false);
			panel.add(sampleRate);
			
			panel.add(new JLabel("�ڵ�: "));
			codec = new JTextField();
			codec.setEditable(false);
			panel.add(codec);
			
			panel.add(new JLabel("���� ũ��: "));
			fileSize = new JTextField();
			fileSize.setEditable(false);
			panel.add(fileSize);
						
			JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setPreferredSize(new Dimension(250, 280));
			add(scrollPane);
			
			pack();
			setVisible(true);
		}
		
		/**
		 * �ؽ�Ʈ�ʵ忡 �±� ���� ����
		 * @param str ���ڿ�
		 * @param mode 1. ����, 2. ����, 3. �帣, 4.�ٹ�, 5. ����, 6. �۰
		 */
		public void setTagInfo(String str, int mode) {
			switch (mode) {
			case 1:
				title.setText(str);
				break;
			case 2:
				singer.setText(str);
				break;
			case 3:
				genre.setText(str);
				break;
			case 4:
				album.setText(str);
				break;
			case 5:
				year.setText(str);
				break;
			case 6:
				songWriter.setText(str);
				break;
			case 0:
				duration.setText(str);
				break;
			default:
				break;
			}
			
		}
		
		/**
		 * Hashmap ��ü�� �Ѱ� �޾� �����͸� ������
		 * @param map
		 */
		public void setTagInfo(HashMap<String, String> map) {
			title.setText(map.get("title"));
			singer.setText(map.get("singer"));
			genre.setText(map.get("genre_name"));
			album.setText(map.get("album"));
			year.setText(map.get("years"));
			songWriter.setText(map.get("composer"));
			duration.setText(map.get("duration"));
			track.setText(map.get("track"));
			encoder.setText(map.get("encoder"));
			language.setText(map.get("language"));
			url_lyrics_site.setText(map.get("url_lyrics_site"));
			
			bitRate.setText(map.get("bitRate"));
			bitPerSample.setText(map.get("bitPerSample"));
			sampleRate.setText(map.get("sampleRate"));
			fileSize.setText(map.get("fileSize"));
			codec.setText(map.get("codec"));
			comment.setText(map.get("comment"));
		}
		
		/**
		 * �±� ������ ���ڿ��� ��ȯ�ϴ� �Լ�
		 * @param mode 1. ����, 2. ����, 3. �帣, 4.�ٹ�, 5. ����, 6. �۰
		 * @return ���ڿ�
		 */
		public String getTagInfo(int mode) {
			String info = null;
			switch (mode) {
			case 1:
				info = title.getText();
				break;
			case 2:
				info = singer.getText();
				break;
			case 3:
				info = genre.getText();
				break;
			case 4:
				info = album.getText();
				break;
			case 5:
				info = year.getText();
				break;
			case 6:
				info = songWriter.getText();
				break;
			default:
				break;
			}
			return info;
		}
		
		/**
		 * �ؽ�Ʈ���� �ԷµǾ� �ִ� ������ HashMap ��ü�� ��ȯ��
		 * @return
		 */
		public HashMap<String, String> getTagInfo(){
			HashMap<String, String> map = new HashMap<>();
			/*try {
				map.put("title", new String(title.getText().getBytes(encoding), decoding));
				map.put("album_artist", new String(singer.getText().getBytes(encoding), decoding));
				map.put("genre", new String(genre.getText().getBytes(encoding), decoding));
				map.put("album", new String(album.getText().getBytes(encoding), decoding));
				map.put("year", new String(year.getText().getBytes(encoding), decoding));
				map.put("composer", new String(songWriter.getText().getBytes(encoding), decoding));
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			map.put("title", title.getText());
			map.put("singer", singer.getText());
			map.put("genre_name", genre.getText());
			map.put("album", album.getText());
			map.put("years", year.getText());
			map.put("composer", songWriter.getText());
			map.put("track", track.getText());
			map.put("encoder", encoder.getText());
			map.put("language", language.getText());
			map.put("url_lyrics_site", url_lyrics_site.getText());
			
			map.put("comment", comment.getText());
			return map;
		}
		
		/**
		 * ������忡 ���� �ؽ�Ʈ�ʵ带 Ȱ�� / ��Ȱ��ȭ
		 * @param isEditMode
		 */
		public void setEditMode(boolean isEditMode) {
			if(!isEditMode) {
				title.setEditable(true);
				singer.setEditable(true);
				genre.setEditable(true);
				album.setEditable(true);
				year.setEditable(true);
				songWriter.setEditable(true);
				comment.setEditable(true);
			}
			else {
				title.setEditable(false);
				singer.setEditable(false);
				genre.setEditable(false);
				album.setEditable(false);
				year.setEditable(false);
				songWriter.setEditable(false);
				comment.setEditable(false);
			}
		}
	}
	
	/*
	 * �ٹ� �̹��� ��¿� �г�
	 */
	class AlbumImagePanel extends JPanel{
		private BufferedImage img = null;
		private Image scaledImage = null;
		
		private final int IMAGE_WIDTH = 512;
		private final int IMAGE_HEIGHT = 512;
				
		public AlbumImagePanel() {
			
		}
		
		public void setImage(Image img) {
			scaledImage = img;
		}
		
		public void refresh() {
			this.repaint();
		}
				
		public Image getAlbumImage(String path) {
			BufferedImage bi = null;
			Image image = null;
			try {
				bi = ImageIO.read(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
				//System.out.println("�ٹ��̹����� �������� ����");
			}
			//������ ȯ�游 ������ �̹��� ����
			image = bi.getScaledInstance(IMAGE_WIDTH/2, IMAGE_HEIGHT/2, Image.SCALE_SMOOTH);
			return image;
		}
		
		
		public void paint(Graphics g) {		
			g.drawImage(scaledImage, -10, 10, null);
		}
		
		public Dimension getPreferredSize() {
			if(img == null) {
				return new Dimension(IMAGE_WIDTH/2, IMAGE_HEIGHT/2);
			}
			else
				return new Dimension(img.getWidth()/2, img.getHeight()/2);
		}
	}
}



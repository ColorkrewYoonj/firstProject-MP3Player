package MiniProject.MP3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SearchFile {
	private ArrayList<String> filePathList = new ArrayList<String>();
	private final String FILE_PATH = "c:\\"; 
	private String extensionName = null;
	
	File base = null;
	/**
	 * 기본 생성자
	 * C:\\ 경로 기준
	 */
	public SearchFile() {
		base = new File(FILE_PATH);
	}
	
	/**
	 * 파일경로를 넘겨주는 생성자
	 * @param filePath
	 */
	public SearchFile(String filePath) {
		base = new File(filePath);
	}
	public void Start() {
		File[] fileList = null;

		fileList = base.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if(fileList[i].isDirectory()) {
				subDirList(fileList[i].getAbsolutePath());
			}
			else if(fileList[i].isFile()) {
				if(compareToExtension(fileList[i]))
					filePathList.add(fileList[i].getAbsolutePath());
			}
			
		}
	}
	
	public void setExtensionName(String extension) {
		int pos = extension.lastIndexOf( "." );
		if(pos != -1)
			extensionName = extension.substring( pos + 1 );
		else extensionName = extension;
	}
	
	public ArrayList<String> getFilePathList(){
		return filePathList;
	}
	
	private void subDirList(String source) {
		File dir = new File(source);
		File[] fileList = dir.listFiles();
		if(fileList == null)
			return;
		try {
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				if (file.isFile()) {
					if(compareToExtension(file))
						filePathList.add(file.getAbsolutePath());
				} else if (file.isDirectory()) {
					subDirList(file.getCanonicalPath().toString());
				}
			}
		} catch (IOException e) {
		}
	}	
	
	private boolean compareToExtension(File f) {
		return f.getName().toLowerCase().endsWith("."+extensionName);
	}
	
//	private boolean compareToMP3(File f) {
//		return f.getName().toLowerCase().endsWith(".mp3");
//	}
//	
//	private void compareToTXT(File f) {
//		String strFileName = f.getName();
//		int pos = strFileName.lastIndexOf( "." );
//		String ext = strFileName.substring( pos + 1 );
//		if(ext.equalsIgnoreCase("txt"))
//			filePathList.add(f.getAbsolutePath());
//
//	}

}

package etc;

import java.security.*;
public class Encryption {
	public String encrypton(String str){
		String password = ""; 
	   	try{
	   		MessageDigest sha = MessageDigest.getInstance("SHA-256"); 
	   		sha.update(str.getBytes()); 
	   		byte byteData[] = sha.digest();
    		StringBuffer sb = new StringBuffer(); 
    		for(int i = 0 ; i < byteData.length ; i++){
	    		sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
    	}
	    	password = sb.toString();
	    	
	    }catch(NoSuchAlgorithmException e){
	   		System.out.println("Encrypt Error - NoSuchAlgorithmException");
	   		password = null; 
	   	}
	   	return password;
	}	
}

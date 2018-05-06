package MiniProject.VO;

import MiniProject.UI.MiniProjectUI.Gender;

public class Private {
	private String id;
	private String password;
	private String name;
	private int age;
	private Gender gender;

	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getName() {	return name;}
	public void setName(String name) {this.name = name;}
	public Gender getGender() {return gender;}
	public void setGender(Gender gender) {this.gender = gender;}
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public Private() {
	}
	public Private(String id,String password, String name, Gender gender, int age) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}
	public String toString() {
		return "아이디 : " + id +" 비밀번호 : " + password + "이름 : " + name + " 성별 : " + gender + " 나이 : " + age;
	}
}

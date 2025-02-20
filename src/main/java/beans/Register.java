package beans;

import java.util.Objects;

public class Register {

	private int regId;
	private String fname;
	private String lname;
	private String email;
	private String pass;
	private long mobile;
	private String address;
	
	// 2 constructors
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Register(int regId, String fname, String lname, String email, String pass, long mobile, String address) {
		super();
		this.regId = regId;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pass = pass;
		this.mobile = mobile;
		this.address = address;
	}

	// pair of setter and getter methods for each private field
	public int getRegId() {
		return regId;
	}

	public void setRegId(int regId) {
		this.regId = regId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Register [regId=" + regId + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", pass="+ pass + ", mobile=" + mobile + ", address=" + address + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, email, fname, lname, mobile, pass, regId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Register other = (Register) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(fname, other.fname) && Objects.equals(lname, other.lname) && mobile == other.mobile
				&& Objects.equals(pass, other.pass) && regId == other.regId;
	}	
}

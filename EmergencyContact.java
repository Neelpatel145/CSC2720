package Constructs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmergencyContact {
	private String name;
	private String address;
	private int phone;
	private int ID;
	private int EID;
	private String sqlCommand;
	public EmergencyContact(int ID) {
		this.ID=ID;
		Connection conn=null;
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/Manager?useSSL=false","student","student");
			Statement stmnt = conn.createStatement();
			ResultSet reslt = stmnt.executeQuery("SELECT * FROM Emergency_Contact WHERE Emergency_Contact_ID="+ID);
			if(reslt.next()) {
				this.name=reslt.getString("Name");
				this.address=reslt.getString("Address");
				this.phone=reslt.getInt("Phone_Number");
				this.EID=reslt.getInt("Emergency_Contact_ID");
			}
			conn.close();
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	public EmergencyContact(String name, String address, int num, int EID ) {
		this.name=name;
		this.address=address;
		this.phone=num;
		this.EID=EID;
		sqlCommand="insert into Emergency_Contact (Name,Address,Phone_Number,Emergency_Contact_ID) values('"+name+"','"+address+"',"+num+","+EID+");";
		Connection conn=null;
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/Manager?useSSL=false","student","student");
			Statement stmnt = conn.createStatement();
			stmnt.executeUpdate(sqlCommand);
			ResultSet reslt = stmnt.executeQuery("SELECT Emergency_Contact_ID FROM Employee_Contact WHERE Phone_Number="+phone);
			if(reslt.next()) {
				this.ID=reslt.getInt("Emergency_Contact_ID");
			}
			conn.close();
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	public String getContact() {
		return "Name: "+name+"\nADDRESS: "+address+"\nPHONE NUMBER: "+phone;
	}
	public String getPrimaryContact() {
		String name=null;
		String address=null;
		int phone = 0;
		Connection conn=null;
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/Manager?useSSL=false","student","student");
			Statement stmnt = conn.createStatement();
			stmnt.executeUpdate(sqlCommand);
			ResultSet reslt = stmnt.executeQuery("SELECT * FROM Employee_Contact WHERE Emergency_Contact_ID="+1+"and WHERE Emergency_Contact_ID="+EID);
			if(reslt.next()) {
				name=reslt.getString("Name");
				address=reslt.getString("Address");
				phone=reslt.getInt("Phone_Number");
			}
			conn.close();
		}catch (Exception exc) {
			exc.printStackTrace();
		}
		if(name==null||address==null||phone==0) {
			return"Emergency Contact Incomplete";
		}else {
		return "Name: "+name+"\nADDRESS: "+address+"\nPHONE NUMBER: "+phone;
		}
	}
	public String getALLContacts() {
		String all=null;
		Connection conn=null;
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/Manager?useSSL=false","student","student");
			Statement stmnt = conn.createStatement();
			ResultSet reslt = stmnt.executeQuery("SELECT * FROM Employee_Contact WHERE Emergency_Contact_ID="+EID);
			if(reslt.next()) {
				all+="\nName: "+reslt.getString("Name");
				all+="\nAddress: "+reslt.getString("Address");
				all+="\nPhone Number: "+reslt.getInt("Phone_Number");
			}
			conn.close();
		}catch (Exception exc) {
			exc.printStackTrace();
		}
		if(all!=null) {
		return all;
		}else {
			return "ERROR!!! Something Went Wrong";
		}
	}
	public void setName(String name) {
		this.name=name;
		this.sqlCommand="UPDATE Emergency_Contact SET Name="+name+"WHERE Emergency_Contact_ID="+ID;
		SQLInterface();
	}
	public void setAddress(String address) {
		this.address=address;
		this.sqlCommand="UPDATE Emergency_Contact SET Address="+address+"WHERE Emergency_Contact_ID="+ID;
		SQLInterface();
	}
	public void setPhone(int phone) {
		this.phone=phone;
		this.sqlCommand="UPDATE Emergency_Contact SET Phone_Number="+phone+"WHERE Emergency_Contact_ID="+ID;
		SQLInterface();
	}
	public void SQLInterface() {
		Connection conn=null;
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/Manager?useSSL=false","student","student");
			Statement stmnt = conn.createStatement();
			stmnt.executeUpdate(sqlCommand);
			conn.close();
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}

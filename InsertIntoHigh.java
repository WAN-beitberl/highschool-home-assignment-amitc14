package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertIntoHigh {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection(
	"jdbc:mysql://localhost:3306/simawork", "root", "amit2012");
	BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\carme\\Downloads\\highschool.csv"));
	boolean firstLine = true;
	String line;
	while ((line = br.readLine()) != null) {
	    if (firstLine) {
	        firstLine = false;
	        continue;
	    }
	    String[] values = line.split(",");
	
	    int id = Integer.parseInt(values[0]);
	    String first_name = values[1];
	    String last_name = values[2];
	    String email = values[3];
	    String gender = values[4];
	    String ip_address = values[5];
	    int cm_high = Integer.parseInt(values[6]);
	    int age = Integer.parseInt(values[7]);
	    boolean has_car = Boolean.parseBoolean(values[8]);
	    String car_color = values[9];
	    int grade = Integer.parseInt(values[10]);
	    double grade_avg = Double.parseDouble(values[11]);
	    int identification_card = Integer.parseInt(values[12]);
	
	    if (has_car == false && car_color != null) {
	        car_color = null;
	    }
	    
	    if(has_car == true && car_color == null) {
	    	car_color = "unknown";
	    	//if someone has a car but the car color is not mentioned,
	    	//instead of putting null we will put unknown
	    }
	
	    PreparedStatement stmt = con.prepareStatement("INSERT INTO simawork"
	    		+ ".highschool (id, first_name, last_name, "
	    		+ "email, gender, ip_address, cm_high, age,"
	    		+ " has_car, car_color, grade, grade_avg,"
	    		+ " identification_card) "
	    		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?");
	    stmt.setInt(1, id);
	    stmt.setString(2, first_name);
	    stmt.setString(3, last_name);
	    stmt.setString(4, email);
	    stmt.setString(5, gender);
	    stmt.setString(6, ip_address);
	    stmt.setInt(7, cm_high);
	    stmt.setInt(8, age);
	    stmt.setBoolean(9, has_car);
	    stmt.setString(10, car_color);
	    stmt.setInt(11, grade);
	    stmt.setDouble(12, grade_avg);
	    stmt.setInt(13, identification_card);
	    stmt.setInt(14, id);
	    stmt.executeUpdate();
	}
	con.close();
	br.close();
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	
	}
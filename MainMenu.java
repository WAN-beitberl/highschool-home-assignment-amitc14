package database;

import java.util.Scanner;
import java.sql.*;

public class MainMenu {

	
	public static void school_avg(Connection con) throws SQLException {
            // Retrieve the average grade of the entire school
            String query = "SELECT AVG(grade_avg) FROM highschool";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
         // Print the result
            if (rs.next()) {
                System.out.println("The average grade of the entire"
                		+ " school is: " + rs.getFloat(1));
            } else {
                System.out.println("No data found.");
            }
	}
	
	   public static void boys_avg(Connection con) throws SQLException {
	            // Retrieve the average grade of the entire school
	            String query = "SELECT AVG(grade_avg) FROM highschool"
	            		+ " WHERE gender = 'Male'";
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery(query);
	         // Print the result
	            if (rs.next()) {
	                System.out.println("The average grade of the entire"
	                		+ " school boys is: " + rs.getFloat(1));
	            } else {
	                System.out.println("No data found.");
	            }
       }

	   public static void girls_avg(Connection con) throws SQLException {
	            // Retrieve the average grade of the entire school
	            String query = "SELECT AVG(grade_avg) FROM highschool"
	            		+ " WHERE gender = 'Female'";
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery(query);
	         // Print the result
	            if (rs.next()) {
	                System.out.println("The average grade of the entire"
	                		+ " school girls is: " + rs.getFloat(1));
	            } else {
	                System.out.println("No data found.");
	            }
	   }
	
	   public static void avg_height_2m_purple(Connection con)
			   throws SQLException{
	            // Retrieve the average grade of the entire school
	            String query = "SELECT AVG(cm_high) FROM highschool"
	            		+ " WHERE cm_high >= 200 AND "
	            		+ "car_color = 'Purple'";
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery(query);
	         // Print the result
	            if (rs.next()) {
	                System.out.println("The average hright of students"
	                		+ " over 2m who own a purple car"
	                		+ " is: " + rs.getFloat(1) + "m");
	            } else {
	                System.out.println("No data found.");
	            }
	   }
	   
	   public static void findFriends(int friendId, Connection con)
			   throws SQLException {
		    // Find all friends of the given friendId
		    String sql = "SELECT DISTINCT friend_id, other_friend_id FROM highschool_friendships WHERE friend_id = ?";
		    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		        stmt.setInt(1, friendId);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            System.out.println("Friend: " + rs.getInt("friend_id") + ", Other friend: " + rs.getInt("other_friend_id"));
		        }
		    }

		    // Find all friends of the friends of the given friendId
		    sql = "SELECT DISTINCT friend_id, other_friend_id FROM highschool_friendships "
		            + "WHERE friend_id IN (SELECT DISTINCT other_friend_id "
		            + "FROM highschool_friendships WHERE friend_id = ?) AND other_friend_id != ?";
		    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		        stmt.setInt(1, friendId);
		        stmt.setInt(2, 26);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            System.out.println("Friend of friend: " + rs.getInt("friend_id") + ", Other friend of friend: " + rs.getInt("other_friend_id"));
		        }
		    }

	   }
	   
	   public static void printFriendshipStatistics(Connection con)
			   throws SQLException {
		    String query1 = "SELECT COUNT(DISTINCT highschool.id) FROM highschool";
		    String query2 = "SELECT COUNT(DISTINCT highschool.id)"
		    		+ " FROM highschool INNER JOIN highschool_friendships"
		    		+ " ON highschool.id=highschool_friendships.friend_id"
		    		+ " GROUP BY highschool.id HAVING COUNT(highschool.id) >= 2";
		    String query3 = "SELECT COUNT(DISTINCT highschool.id)"
		    		+ " FROM highschool INNER JOIN highschool_friendships"
		    		+ " ON highschool.id=highschool_friendships.friend_id"
		    		+ " GROUP BY highschool.id HAVING COUNT(highschool.id) = 1";
		    String query4 = "SELECT COUNT(DISTINCT highschool.id) FROM"
		    		+ " highschool LEFT JOIN highschool_friendships ON"
		    		+ " highschool.id=highschool_friendships.friend_id "
		    		+ " WHERE highschool_friendships.friend_id IS NULL";
		    Statement stmt1 = con.createStatement();
		    ResultSet rs1 = stmt1.executeQuery(query1);
		    int total = 0;
		    if (rs1.next()) {
		        total = rs1.getInt(1);
		    }
		    Statement stmt2 = con.createStatement();
		    ResultSet rs2 = stmt2.executeQuery(query2);
		    int popular = 0;
		    if (rs2.next()) {
		        popular = rs2.getInt(1);
		    }
		    Statement stmt3 = con.createStatement();
		    ResultSet rs3 = stmt3.executeQuery(query3);
		    int regular = 0;
		    if (rs3.next()) {
		        regular = rs3.getInt(1);
		    }
		    Statement stmt4 = con.createStatement();
		    ResultSet rs4 = stmt4.executeQuery(query4);
		    int lonely = 0;
		    if (rs4.next()) {
		        lonely = rs4.getInt(1);
		    }
		    double popularPercent = (double) popular / total * 100;
		    double regularPercent = (double) regular / total * 100;
		    double lonelyPercent = (double) lonely / total * 100;
		    System.out.println("Popular students: " + popularPercent + "%");
		    System.out.println("Regular students: " + regularPercent + "%");
		    System.out.println("Lonely students: " + lonelyPercent + "%");
		}

	   public static void printAvgGrade(int studentId, Connection con){
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            statement = con.prepareStatement("SELECT grade_avg"
	            		+ " FROM studentgrades WHERE id = ?");
	            statement.setInt(1, studentId);
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                double avgGrade = resultSet.getDouble("grade_avg");
	                System.out.println("The average grade of "
	                		+ "student " + studentId + " is: "
	                				+ "" + avgGrade);
	            } else {
	                System.out.println("No student found wit"
	                		+ "h id: " + studentId);
	            }
	        } catch (SQLException e) {
	            System.err.println("Error while queryin"
	            		+ "g the database: " + e.getMessage());
	        } finally {
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (statement != null) {
	                    statement.close();
	                }
	            } catch (SQLException e) {
	                System.err.println("Error while c"
	                		+ "losing the connection: " + e.getMessage());
	            }
	        }
	    }
	   
	public static Scanner reader = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Connect to the database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/simawork",
                "root", "amit2012");

		System.out.println("Hello Sima! how are you doing?"
				+ " I'm here to help you"
				+ " go over your students information.");
		int n = 0;
		do {
			System.out.println("Enter 1 to get "
					+ "the average grade of the entire school.");
			System.out.println("Enter 2 to get average grade of "
					+ "the boys in the school. ");
			System.out.println("Enter 3 to get average grade of "
					+ "the girls in the school. ");
			System.out.println("Enter 4 to get the average height of"
					+ "students over 2 meters who own a purple car.");
			System.out.println("Enter 5 to check a specific students friends.");
			System.out.println("Enter 6 to check the social status of "
					+ "the school");
		    System.out.println("Enter 7 to check a specific student grades");
		    System.out.println("Enter 8 to exit");
		    n = reader.nextInt();
		    if(n < 1 || n > 8)
		    	System.out.println("Please enter numbers between 1 - 8");
			if(n == 1) school_avg(con);
			if (n == 2) boys_avg(con);
			if(n == 3) girls_avg(con);
			if(n == 4) avg_height_2m_purple(con);
			if(n == 5) {
				int id_to_search;
				System.out.println("Please enter id to check:");
				id_to_search = reader.nextInt();
				findFriends(id_to_search, con);
			}
			if(n == 6)  printFriendshipStatistics(con);
			if(n == 7) {
				int id;
				System.out.println("Enter id that you would like to check:");
				id = reader.nextInt();
				printAvgGrade(id, con);
			}
			if(n == 8) System.out.println("Goodbye Sima! Have a nice day!");
		}while(n != 8);
	    // Close the connection
        con.close();
    } catch (Exception e) {
        System.out.println(e);
    }
	}

}

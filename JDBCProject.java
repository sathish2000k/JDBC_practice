import java.sql.*;
import java.util.*;

public class JDBCProject {
	enum Operation{
		INSERT,DELETE,UPDATE,READ
	}  
	static final String DB_URL = "jdbc:mysql://localhost:3306/student"; 	 	
	static final String USER = "root";
	static final String PASSWORD = "sathish";
	public static void main(String[] args) throws SQLException{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter INSERT,DELETE,UPDATE,READ operation to be done:");
		String function = input.nextLine();
		Operation operation = Operation.valueOf(function.toUpperCase());
		switch(operation){
		case INSERT:
			System.out.println("Enter student name:");
			String student_name = input.nextLine();
			System.out.println("Enter student mark:");
			int student_mark = input.nextInt();
			insert(student_name,student_mark);
			System.out.println("Inserted Successfully");
			break;
		case DELETE:
			System.out.println("Enter student roll no:");
			int roll_no = input.nextInt();
			delete(roll_no);
			System.out.println("Deleted Successfully");
			break;
		case UPDATE:
			System.out.println("Enter student roll_no::");
			int studentRollNo = input.nextInt();
			System.out.println("Enter new student mark:");
			int updatedStudentMark = input.nextInt();
			update(studentRollNo , updatedStudentMark);
			System.out.println("Updated Successfully");
			break;
		case READ:
			System.out.println("Read Successfully");
			list();
			break;
		default:
			break;
			
		}
	}

	static void update(int studentRollNo, int updatedStudentMark) {
		
		try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement statement = connection.prepareStatement("UPDATE marklist SET student_totalmarks = ? WHERE student_rollno = ?")){
			statement.setInt(1, updatedStudentMark);
			statement.setInt(2, studentRollNo);
			statement.executeUpdate();
			statement.close();
			connection.close();
			
		}
		catch(Exception exception) {
			System.err.println(exception);
		}
		
	}

	static void insert(String student_name, int student_mark) {
		
		try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement statement = connection.prepareStatement("INSERT INTO marklist(student_name,student_totalmarks) VALUES(?,?)"); ){
			statement.setString(1, student_name);
			statement.setInt(2, student_mark);
			statement.executeUpdate();
			statement.close();
			connection.close();	
		}
		catch(Exception exception) {
			System.err.println(exception);
		}	
	}
	
	static void delete(int roll_no) {
		
		try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement statement = connection.prepareStatement("DELETE FROM marklist WHERE student_rollno=?");){
			statement.setInt(1,roll_no);
			statement.executeUpdate();
			statement.close();
			connection.close();			
		}
		catch(Exception exception) {
			System.err.println(exception);
		}
	}

	static void list() {
		
		try(Connection connection =  DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement statement=connection.createStatement()){
			ResultSet resultSet = statement.executeQuery("SELECT * FROM marklist");
			System.out.println("Rollno:\tName:\tMarks:");
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2)+"\t"+resultSet.getInt(3));
			}
			resultSet.close();
			statement.close();
			connection.close();
			
		}
		catch(Exception exception) {
			System.err.println(exception);
		}
		
	}
	
	
	
}

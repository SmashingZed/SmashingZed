import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;

public class Login {
    static ArrayList<User> users = new ArrayList<User>();
    public static void main(String[] args) {

        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\Users.db");
            Statement statement = conn.createStatement();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users order by username desc");
            ResultSet r1 = st.executeQuery();
            //conn.setAutoCommit(false);
            statement.execute("CREATE TABLE IF NOT EXISTS users (username TEXT, password TEXT)");

           statement.execute("INSERT INTO users(username, password) VALUES('Salma', 'Salma1083')");

          Scanner sc = new Scanner(System.in);
          String usernameCounter;
          String passwordCounter;
          System.out.print("Username: ");
          String username = sc.next();
          System.out.print("Password: ");
          String password = sc.next();

          if (r1.next()) {
              usernameCounter = r1.getString("username");
              passwordCounter = r1.getString("password");
              if (usernameCounter != username) {
                  System.out.println("You've entered a wrong username / You aren't registered in our repertory");
                  System.out.print("Do you want to create your own account? (Y/n) ");
                  String answer = sc.next();
                  if (answer.equals("Y")) {
                      System.out.print("Choose a username: ");
                      String newUserName = sc.next();
                      System.out.print("Choose a password: ");
                      String newPassword = sc.next();
                      String insertStr = "INSERT INTO users(username, password) VALUES(?, ?)";
                      st = conn.prepareStatement(insertStr);
                      st.setString(1, newUserName);
                      st.setString(2, newPassword);
                      st.executeUpdate();
                      System.out.println("You have been registrated, congrats!!");
                  }
              } else if (usernameCounter == username && passwordCounter != password) {
                  System.out.println("You've entered a wrong password");
              } else {
                  System.out.println("All good");
              }
          } 
        } catch(SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        
       // System.out.println(users.get(1).getUserName());
        
    }

  /* public static  boolean searchUser(String username) {
        for(User i : users) {
            if(i.getUserName().equals(username)) {
                return true;
            } 
        }

        return false;
    }

    public static boolean checkPassword(String password) {
        for(User i : users) {
            if(i.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    } */

}

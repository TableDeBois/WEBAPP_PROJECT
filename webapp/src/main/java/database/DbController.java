package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DbController {

  private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bank";
  private static final String DB_USER = "test";
  private static final String DB_PASSWORD = "test_0011";

  
  public DbController() {}
  
  // La méthode suivante exécute une requête SQL et retourne les résultats
  public static ResultSet executeQuery(String query, Object... params) throws Exception {
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      PreparedStatement stmt = conn.prepareStatement(query);
      System.out.println("QUERY:\n" + query);
      System.out.println("PARAMS:");
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
        System.out.println(String.valueOf(i) +" : " + params[i]);
      }
      
      ResultSet rs =  stmt.executeQuery();
      if(rs.isClosed()) {
    	  System.err.println("DbConroller : rs closed");
      }else {
    	 System.err.println("DbConroller : rs open");
      }
      return rs;
    }
  }
  
  
  
  // La méthode suivante exécute une requête SQL et retourne les résultats
  public static  List<Map<Integer, Object>> executeQuery2(String query, Object... params) throws Exception {
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      PreparedStatement stmt = conn.prepareStatement(query);
      System.out.println("QUERY:\n" + query);
      System.out.println("PARAMS:");
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
        System.out.println(String.valueOf(i) +" : " + params[i]);
      }
      
      ResultSet rs =  stmt.executeQuery();
      if(rs.isClosed()) {
    	  System.err.println("DbConroller : rs closed");
      }else {
    	 System.err.println("DbConroller : rs open");
      }
      
      List<Map<Integer, Object>> resultList = new ArrayList<Map<Integer, Object>>();
      Map<Integer, Object> row = null;

      ResultSetMetaData metaData = rs.getMetaData();
      Integer columnCount = metaData.getColumnCount();

      while (rs.next()) {
          row = new HashMap<Integer, Object>();
          for (int i = 1; i <= columnCount; i++) {
              row.put(i, rs.getObject(i));
          }
          resultList.add(row);
      }
      return resultList;
    }
  }
  
  public static  Boolean executeUpdate2(String query, Object... params) throws Exception {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	      PreparedStatement stmt = conn.prepareStatement(query);
	      System.out.println("QUERY:\n" + query);
	      System.out.println("PARAMS:");
	      for (int i = 0; i < params.length; i++) {
	        stmt.setObject(i + 1, params[i]);
	        System.out.println(String.valueOf(i) +" : " + params[i]);
	      }
	      
	      int rs =  stmt.executeUpdate();
	      if(rs == 0) {
	    	  System.err.println("DbConroller : 0 columns affected");
	      }else {
	    	 System.err.println("DbConroller : "+ String.valueOf(rs)+" row(s) affected");
	      }
	      
	      return true;
	    }
	  }
  
  public static String getDbUrl() {
	  return DB_URL;
  }
  
  public static String getDbUser() {
	  return DB_USER;
  }
  
  public static String getDbPass() {
	  return DB_PASSWORD;
  }
  
}  

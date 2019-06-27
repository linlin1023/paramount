package test;

import java.sql.*;

public class DatabaseTest {

    public static void main(String[] args) throws SQLException {

        try{
            //加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver") ;
        }catch(ClassNotFoundException e){
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace() ;
        }

        String url = "jdbc:mysql://localhost:3306/paramount" ;
        String username = "root" ;
        String password = "gentoo666" ;
        Connection con = null;
        try{
             con =
                    DriverManager.getConnection(url , username , password ) ;
        }catch(SQLException se){
            System.out.println("database connection is not proper configured！");
            se.printStackTrace() ;
        }


        Statement stmt = con.createStatement() ;
        PreparedStatement pstmt = con.prepareStatement("select * from tb_user where id = ?", 2) ;

        boolean resultSet = stmt.execute("select * from tb_item");

        if(resultSet != true){
            System.out.println("table structure is abnormal");
        }
        boolean resultSet1 = pstmt.execute();
        if(resultSet != true){
            System.out.println("the table data is not initailized properly");
        }

    }
}

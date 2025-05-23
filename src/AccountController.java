import java.sql.*;


public class AccountController {
    static public void createAccount(String acc){
        String sql="insert into account(name) values (?);";
        try{
            Connection con=Dbconnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,acc);
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            System.out.println("Account created Successfully,Your id :"+rs.getInt(1)+"\n Thank You!");
            con.close();
            ps.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }
    static public boolean updateAmount(int user_id,double amount){
        String sql="update account set balance=? where id= ?";
        try{
            Connection con=Dbconnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(2,user_id);
            ps.setDouble(1,amount);
            ps.execute();
            ps.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    static double getBalance(int id){
        String sql="select balance from account where id = ?";
        double balance=0.0;
        try{
            Connection con = Dbconnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id );
            ResultSet rs=ps.executeQuery();
            rs.next();
            balance=rs.getDouble(1);
            ps.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return balance;
    }
    static boolean transfer(int from,int to,double amount) throws Exception {

        Connection con = null;
        try {
            con = Dbconnection.getConnection();
            con.setAutoCommit(false);
            double from_balance = AccountController.getBalance(from);
            if (from_balance < amount) {
                throw new Exception("low balance");

            }


            String f_sql = "update account set balance=balance-? where id=?";
            PreparedStatement ps = con.prepareStatement(f_sql);


            ps.setInt(2, from);
            ps.setDouble(1, amount);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new Exception("Debit failed");
            }
            String t_sql = "update account set balance=balance+? where id=?";
            ps = con.prepareStatement(t_sql);
            ps.setInt(2, to);
            ps.setDouble(1, amount);
            rows = ps.executeUpdate();
            if (rows != 1) {
                throw new Exception("Credit failed");
            }
            con.commit();

        } catch (Exception e) {
            System.out.print("\n"+e);
            System.out.print("\n Transaction failed.");
            con.rollback();
            con.close();
            return false;
        }
        con.close();
        return true;

    }
}

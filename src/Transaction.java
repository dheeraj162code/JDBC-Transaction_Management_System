import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Transaction {
    static void transact(int id,String type,double amount){
        String sql="insert into transaction_history(acc_id,type,amount) values(?,?,?)";
        try {
            Connection con=Dbconnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2,type);
            ps.setDouble(3,amount);
            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void transact_history(int id){
        String sql="select * from transaction_history where acc_id=?";
        try{
            Connection con=Dbconnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                System.out.print("\nTransaction id:"+rs.getInt(1)+"|description:"+rs.getString(3)+"|Amount:"+rs.getDouble(4)+"|Time:"+rs.getTimestamp(5));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

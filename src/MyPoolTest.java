import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bixin on 2018/4/12.
 */
public class MyPoolTest {
    public static IMyPool pool = MyPoolFactory.getInstance();

    public static void main(String[] args) {
        MyPooledConnection myPooledConnection = pool.getMyPooledConnection();
        ResultSet set = myPooledConnection.query("select * from t_test where statis_day=180412");
        try {
            while (set.next()) {
                System.out.println(set.getString("username") + "use connection :" + myPooledConnection.getConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

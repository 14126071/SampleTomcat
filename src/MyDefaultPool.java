import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by bixin on 2018/4/12.
 */
public class MyDefaultPool implements IMyPool {

    private Vector<MyPooledConnection> myPooledConnections = new Vector<MyPooledConnection>();
    private static String jdbcURL;
    private static String jdbcUsername;
    private static String jdbcPassword;
    private static int initcount;
    private static int step;
    private static int maxcount;

    public MyDefaultPool() {
        init();
        try {
            Class.forName(DbConfigXML.jdbcDriver);//加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createMyPooledConnection(initcount);
    }

    public void init() {
        jdbcURL = DbConfigXML.jdbcURL;
        jdbcUsername = DbConfigXML.jdbcUsername;
        jdbcPassword = DbConfigXML.jdbcPassword;
        initcount = DbConfigXML.initCount;
        step = DbConfigXML.step;
        maxcount = DbConfigXML.maxCount;
    }

    @Override
    public MyPooledConnection getMyPooledConnection() {
        if (myPooledConnections.size() < 1) {
            throw new RuntimeException("pool init error");
        }
        MyPooledConnection myPooledConnection = null;
        try {
            myPooledConnection = getRealConnectionFromPool();
            while (myPooledConnection == null) {
                createMyPooledConnection(step);
                myPooledConnection = getRealConnectionFromPool();
                return myPooledConnection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myPooledConnection;
    }

    @Override
    public void createMyPooledConnection(int count) {
        if (myPooledConnections.size() + count > maxcount) {
            throw new RuntimeException("connection has full");
        }
        for (int i = 0; i < count; i++) {
            try {
                Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                MyPooledConnection myPooledConnection = new MyPooledConnection(connection, false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized MyPooledConnection getRealConnectionFromPool() throws SQLException{
        for(MyPooledConnection myPooledConnection : myPooledConnections){
            if(!myPooledConnection.isBusy()){
                if(myPooledConnection.getConnection().isValid(3000)){
                    myPooledConnection.setBusy(true);
                    return myPooledConnection;
                }else{
                    Connection connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
                    myPooledConnection.setConnection(connection);
                    myPooledConnection.setBusy(true);
                    return myPooledConnection;
                }
            }
        }
        return null;
    }
}

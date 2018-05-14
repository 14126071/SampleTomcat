package JDBCPOOL;

/**
 * Created by bixin on 2018/4/12.
 */
public interface IMyPool {
    public MyPooledConnection getMyPooledConnection();

    public void createMyPooledConnection(int count);
}

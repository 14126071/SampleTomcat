package JDBCPOOL;

/**
 * Created by bixin on 2018/4/12.
 */
public class MyPoolFactory {

    private static class Singleton {
        public static IMyPool myPool = new MyDefaultPool();
    }

    public static IMyPool getInstance() {
        return Singleton.myPool;
    }

}

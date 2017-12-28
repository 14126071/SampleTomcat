import java.util.ArrayList;
import java.util.List;

/**
 * Created by bixin on 2017/10/31.
 */
public class ServletMappingConfig {
    public static List<ServletMapping> servletMappingList = new ArrayList<ServletMapping>();

    static {
        servletMappingList.add(new ServletMapping("testServlet","/test","TestServlet"));
    }
}

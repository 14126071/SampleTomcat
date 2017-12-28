import java.io.IOException;

/**
 * Created by bixin on 2017/10/31.
 */
public class TestServlet extends MyServlet {
    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try{
            myResponse.write("get Hello World!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try{
            myResponse.write("post Hello World!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

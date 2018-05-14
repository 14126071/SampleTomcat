import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by bixin on 2018/4/17.
 */
// 表示一个ChannelHandler 可以被多个Channel 安全地共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf  in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println("Server received: "+System.currentTimeMillis()+in.toString(CharsetUtil.UTF_8));
        // 将接收到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //将未决消息冲刷到远程节点，并且关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace(); // 打印异常栈跟踪
        ctx.close(); // 关闭该Channel
    }
}

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by bixin on 2018/4/17.
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() + "<port>");
//        }
//        int port = Integer.parseInt(args[0]); // 设置端口值
        new EchoServer(8088).start(); // 调用服务器start方法
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup(); // 创建EventLoop
        try {
            ServerBootstrap b = new ServerBootstrap(); // 创建Server-Bootstrap
            // 指定所使用的NIO传输Channel
            b.group(group).channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //添加一个EchoServerHandler 到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // EchoServerHandler被标注为@Shareable,所以我们总是使用同样的实例
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync(); // 异步的绑定服务器，调用sync()方法阻塞直到绑定完成
            f.channel().closeFuture().sync(); // 获取Channel的CloseFuture,并且阻止当前线程直到完成
        } finally {
            group.shutdownGracefully().sync(); // 关闭EventLoopGroup,释放所有的资源
        }
    }
}

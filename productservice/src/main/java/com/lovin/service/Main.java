package com.lovin.service;

import com.lovin.IProductService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bixin on 2018/4/13.
 */
public class Main {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true){
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                // 读取网络协议
                String apiClassName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                Class[] parameterTypes = (Class[])objectInputStream.readObject();
                Object[] args4Method = (Object[])objectInputStream.readObject();
                Class clazz = null;
                if(apiClassName.equals(IProductService.class.getName())){
                    clazz = ProductService.class;
                }
                Method method = clazz.getMethod(methodName,parameterTypes);
                Object invoke = method.invoke(clazz.newInstance(),args4Method);
                ObjectOutputStream objectOutputStream=  new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(invoke);
                objectOutputStream.flush();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

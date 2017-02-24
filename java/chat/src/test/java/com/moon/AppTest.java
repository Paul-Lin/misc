package com.moon;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7575);
        OutputStream out = socket.getOutputStream();


        Date now = new Date();
        DataOutputStream dataOut = new DataOutputStream(out);
        String str = "qwertyu111";
        //byte[] buf = str.getBytes();
        //System.out.println(new String(buf));
        out.write(str.getBytes());
        //dataOut.writeBytes("qwertyu111");
        //dataOut
        dataOut.writeInt(40);
        dataOut.writeLong(now.getTime());

        dataOut.writeBytes("00");
        dataOut.writeInt(10);
        dataOut.writeInt(20);
        dataOut.writeInt(30);
        dataOut.writeBytes("pear");

        dataOut.flush();

        // 得到返回数据
        System.out.println("waiting");
        InputStream in = socket.getInputStream();
        byte[] buf = new byte[1024];
        int len = in.read(buf);
        System.out.println("len = " + len);
        // 打印
        for (int ix = 0 ; ix < len ; ++ix) {
            System.out.println( byte2Hex(Byte.toUnsignedInt(buf[ix])));
            System.out.println( Integer.toHexString(Byte.toUnsignedInt(buf[ix])) );
        }

        socket.close();
    }

    public static String byte2Hex(int b){
        StringBuilder buf=new StringBuilder();
        int heightDigit=b>>4;
        char hex=hex(heightDigit);
        buf.append(hex);
        int lowDigit=(b<<28)>>>28;
        hex=hex(lowDigit);
        buf.append(hex);
        return buf.toString();
    }

    public static char hex(int num){
        if(num<10)
            return (char)('0'+num);
        else
            return (char)('A'+(num-10));
    }
}

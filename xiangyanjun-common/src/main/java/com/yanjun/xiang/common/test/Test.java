package com.yanjun.xiang.common.test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private static Pattern pattern = null;
    // 从words.properties初始化正则表达式字符串
    private static void initPattern()
    {
        StringBuffer patternBuf = new StringBuffer("");
        try
        {
            InputStream in = Test.class.getClassLoader().getResourceAsStream("sensitive.properties");
            Properties pro = new Properties();
            pro.load(in);
            Enumeration enu = pro.propertyNames();
            patternBuf.append("(");
            while(enu.hasMoreElements())
            {
                patternBuf.append((String)enu.nextElement()+"|");
            }
            patternBuf.deleteCharAt(patternBuf.length()-1);
            patternBuf.append(")");

//unix换成UTF-8
            pattern = Pattern.compile(new String(patternBuf.toString().getBytes("ISO-8859-1"), "UTF-8"));
//win下换成gb2312
//            pattern = Pattern.compile(new String(patternBuf.toString().getBytes("ISO-8859-1"), "gb2312"));
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
    private static String doFilter(String str)
    {
        Matcher m = pattern.matcher(str);
        boolean b = m.find();
        System.out.println(b);
        str = m.replaceAll("");
        return str;
    }

    public static void main(String[] args)
    {
        String str = "111赌博机17个军区11押题";
        System.out.println("str:"+str);
        initPattern();
        Date d1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss:SSS Z");
        System.out.println("start:"+formatter.format(d1));
        System.out.println("共"+str.length()+"个字符，查到" + Test.doFilter(str));
        Date d2 = new Date();
        System.out.println("end:"+formatter.format(d2));
    }

}

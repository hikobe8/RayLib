package com.x.javautil;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Author : Ray
 * Time : 1/8/21 11:05 AM
 * Description :
 */

/**
 * 获取apk的包名、版本名、版本号、图标等信息
 */
public class ApkUtil {

    //  文件名称
    //  apk的绝对地址
    private static String apk = "apk/163_apk_newsapp.apk";
    //  拷贝图标的存放位置
    private static String fileName =  apk + ".png";


    public static void main(String[] args) {

        try {
            File file = new File(apk);
            if (file.exists() && file.isFile()) {
                ApkFile apkFile = new ApkFile(file);
                ApkMeta apkMeta = apkFile.getApkMeta();

                //  拷贝出的icon文件名 根据需要可以随便改
                System.out.println("应用名称   :" + apkMeta.getLabel());
                System.out.println("包名       :" + apkMeta.getPackageName());
                System.out.println("版本号     :" + apkMeta.getVersionName());
                System.out.println("图标       :" + apkMeta.getIcon());
                System.out.println("大小       :" + (double) (file.length() * 100 / 1024 / 1024) / 100 + " MB");
                //  System.out.println("全部       :===============================");
                //  System.out.println(apkMeta.toString());

                //  拷贝图标
                saveBit(apkMeta.getIcon());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  拷贝图标
    public static void saveBit(String Icon) throws IOException {
        ZipInputStream zin = null;

        try {
            //  访问apk 里面的文件
            ZipFile zf = new ZipFile(apk);
            InputStream in = new BufferedInputStream(new FileInputStream(apk));
            zin = new ZipInputStream(in);
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.getName().equals(Icon)) {
                    //  拷贝出图标
                    System.out.println("拷贝开始");
                    InputStream inStream = zf.getInputStream(ze);

                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    //创建一个Buffer字符串
                    byte[] buffer = new byte[1024];
                    //每次读取的字符串长度，如果为-1，代表全部读取完毕
                    int len = 0;
                    //使用一个输入流从buffer里把数据读取出来
                    while ((len = inStream.read(buffer)) != -1) {
                        //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                        outStream.write(buffer, 0, len);
                    }
                    //关闭输入流
                    inStream.close();
                    //把outStream里的数据写入内存

                    //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                    byte[] data = outStream.toByteArray();
                    //new一个文件对象用来保存图片，默认保存当前工程根目录
                    File imageFile = new File(fileName);
                    //创建输出流
                    FileOutputStream fileOutStream = new FileOutputStream(imageFile);
                    //写入数据
                    fileOutStream.write(data);
                    System.out.println("拷贝结束");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zin.closeEntry();
        }
    }
}



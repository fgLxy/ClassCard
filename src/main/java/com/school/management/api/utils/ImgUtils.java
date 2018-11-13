package com.school.management.api.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ImgUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImgUtils.class);

    private static File RESOURCE;

    private static final String ROOTPATH = "E:\\class card\\";

    public static void base64esToImg(String[] img, String[] fileName) throws Exception {
        OutputStream out = null;
        for (int i = 0; i < img.length; i++) {
            byte[] bytes = Base64.decodeBase64(img[i]);
            try {
                String name = fileName[i];
                name = i + "_" + name.substring(0, name.lastIndexOf("."));
                out = new FileOutputStream(RESOURCE + name + ".jpg");
                out.write(bytes);
            } catch (Exception e) {
                break;
            }
        }
        if (out != null) {
            out.flush();
            out.close();
        }
    }

    public static List imgToBase64(String[] fileName) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < fileName.length; i++) {
                String name = fileName[i];
                name = i + "_" + name.substring(0, name.lastIndexOf("."));
                InputStream in = new FileInputStream(RESOURCE + name + ".jpg");
                byte[] b = new byte[in.available()];
                in.read(b);
                in.close();
                list.add("data:image/jpeg;base64," + new BASE64Encoder().encode(b));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertBlobToBase64String(Blob blob) {
        String result = "";
        if (null != blob) {
            try {
                InputStream msgContent = blob.getBinaryStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[100];
                int n = 0;
                while (-1 != (n = msgContent.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                result = new BASE64Encoder().encode(output.toByteArray());
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            return null;
        }
    }

    public static String base64ToImg(String img, String fileName, String imgPath) throws IOException {
        OutputStream out = null;
        byte[] bytes = Base64.decodeBase64(img);
        File dir = new File(ROOTPATH + "\\images\\" + imgPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File path = new File(dir.getPath() + "\\" + fileName);
        if (!path.exists()) {
            path.getParentFile().mkdir();
        }
        path.createNewFile();
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        out = new FileOutputStream(path);
        out.write(bytes);
        out.flush();
        out.close();
        return "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/images/" + imgPath + "/" + fileName;
    }

    public static String base64ToFile(String file, String fileName) throws IOException {
        OutputStream out = null;
        byte[] bytes = Base64.decodeBase64(file);
        File dir = new File(ROOTPATH + "\\file\\");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File path = new File(dir.getPath() + "\\" + fileName);
        if (!path.exists()) {
            path.getParentFile().mkdir();
        }
        path.createNewFile();
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        out = new FileOutputStream(path);
        out.write(bytes);
        out.flush();
        out.close();
        return "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/files/" + fileName;
    }

    public static void filesToImg(String photos) throws Exception {
        InputStream in = new ByteArrayInputStream(photos.getBytes());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//创建一个Buffer字符串
        byte[] buffer = new byte[1024];
//每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
//使用一个输入流从buffer里把数据读取出来
        while ((len = in.read(buffer)) != -1) {
//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
//关闭输入流
        in.close();
//把outStream里的数据写入内存
        outStream.flush();
//得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = outStream.toByteArray();
        System.out.println(new BASE64Encoder().encode(data));
//new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(ResourceUtils.getFile("classpath:").getPath() + "\\static\\" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date(System.currentTimeMillis())) + ".jpg");
//创建输出流
        FileOutputStream fileOutStream = new FileOutputStream(imageFile);
//写入数据

        fileOutStream.write(data);
        fileOutStream.flush();

        fileOutStream.close();
        outStream.close();
    }

    public static List<String> filesToImg(MultipartHttpServletRequest request, String typePath) throws Exception {
        String rootPath = ROOTPATH + typePath;

        Iterator<String> it = request.getFileNames();

        List<String> fileNameList = new ArrayList<>();
        File createFile = new File(rootPath);
        if (!createFile.exists()) {
            createFile.mkdir();
        }
        while (it.hasNext()) {
            String fileName = it.next();
            MultipartFile file = request.getFile(fileName);
            BufferedOutputStream stream = null;
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(createFile, file.getOriginalFilename())));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    throw e;
                }
                fileNameList.add("http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/" + typePath + "\\" + file.getOriginalFilename());
            } else {
                throw new Exception("图片内容为空");
            }
        }
        return fileNameList;
    }

    public static Object androidUploadImage(String image, HttpServletRequest request) {

//        Image image1 = new Image();
        String str = image.substring(image.indexOf(",") + 1);
        String imageName = java.util.UUID.randomUUID().toString();
        String rootPath = "D:\\temp\\";

        File dir = new File(rootPath + File.separator);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        Base64Utils.GenerateImage(str, rootPath + imageName + ".jpg");
//        image1.setImageUrl(rootPath+imageName+".jpg");

        return null;
    }

}

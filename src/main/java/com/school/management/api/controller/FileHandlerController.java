package com.school.management.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/school/base64")
public class FileHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandlerController.class);

//    @PostMapping("/uploadMore")
//    public String handleFileUpload(HttpServletRequest request) {
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//        MultipartFile file = null;
//        BufferedOutputStream stream = null;
//        for (int i = 0; i < files.size(); ++i) {
//            file = files.get(i);
//            String filePath = "D://aim//";
//            if (!file.isEmpty()) {
//                try {
//                    byte[] bytes = file.getBytes();
//                    stream = new BufferedOutputStream(new FileOutputStream(
//                            new File(filePath + file.getOriginalFilename())));//设置文件路径及名字
//                    stream.write(bytes);// 写入
//                    stream.close();
//                } catch (Exception e) {
//                    stream = null;
//                    return "第 " + i + " 个文件上传失败  ==> " + e.getMessage();
//                }
//            } else {
//                return "第 " + i + " 个文件上传失败因为文件为空";
//            }
//        }
//        return "上传成功";
//    }
//
//    @PostMapping("/upload")
//    public String singleFileUpload(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println();
//        System.out.println(request.getContentType());
//        System.out.println();
////        MultipartFile pFile = null;
////        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//
////        if(multipartResolver.isMultipart(request)){
////            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
////            pFile = multiRequest.getFileMap().get("pFile");
////
////            //判断文件是否为空
////            if(pFile.isEmpty()){
////                return "文件为空，上传失败!";
////            }
////            try{
////                //获得文件的字节流
////                byte[] bytes=pFile.getBytes();
////                //获得path对象，也即是文件保存的路径对象
////                Path path= Paths.get("D:\\abc\\"+pFile.getOriginalFilename());
////                //调用静态方法完成将文件写入到目标路径
////                Files.write(path,bytes);
////                return "恭喜上传成功!";
////            }catch (IOException e){
////                e.printStackTrace();
////                LOGGER.warn(e.getMessage(), e);
////            }
////        }
//        return "未知异常";
//    }
//
//    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
//    public Object imageUpload(@RequestParam(value = "photo") String photo, @RequestParam(value = "phoneNumber") String phoneNumber, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
//        System.out.println();
//        System.out.println("imageUpload Request was coming. The photo of string is "+ photo);
//        System.out.println();
//        Map<String, Object> map = new HashMap<>();
//        try {
//            request.setCharacterEncoding("utf-8");
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("text/html");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
////            return new JsonObjectResult(REsu)
//        }
//        try {
//
//            // 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密
//            byte[] photoimg = new BASE64Decoder().decodeBuffer(photo);
//            for (int i = 0; i < photoimg.length; ++i) {
//                if (photoimg[i] < 0) {
//                    // 调整异常数据
//                    photoimg[i] += 256;
//                }
//            }
//            // 获取项目运行路径
//            String pathRoot = request.getSession().getServletContext().getRealPath("");
//            // 生成uuid作为文件名称
//            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//
//            String path = "/images/" + uuid + "head.png";
//            // byte[] photoimg =
//            // Base64.decode(photo);//此处不能用Base64.decode（）方法解密，我调试时用此方法每次解密出的数据都比原数据大
//            // 所以用上面的函数进行解密，在网上直接拷贝的，花了好几个小时才找到这个错误（菜鸟不容易啊）
//            System.out.println("图片的大小：" + photoimg.length);
//            System.out.println();
//            System.out.println(request.getContextPath());
//            System.out.println();
//            File file = new File(pathRoot + path);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            FileOutputStream out = new FileOutputStream(file);
//            out.write(photoimg);
//            out.flush();
//            out.close();
//            map.put("updateImage", "filed");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return new JsonObjectResult(ResultCode.SUCCESS, "", map);
//    }

}

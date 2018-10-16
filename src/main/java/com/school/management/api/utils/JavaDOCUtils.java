package com.school.management.api.utils;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaDOCUtils {

    private static RootDoc root;

    private String classPath;

    // 一个简单Doclet,收到 RootDoc对象保存起来供后续使用
    // 参见参考资料6
    public static class Doclet {

        public Doclet() {
        }

        public static boolean start(RootDoc root) {
            JavaDOCUtils.root = root;
            return true;
        }
    }

    public static Map<String, Object> show() {
        ClassDoc[] classes = root.classes();
        Map<String, Object> field_comment = new HashMap<>();
        for (int i = 0; i < classes.length; ++i) {
            for (FieldDoc fieldDoc : classes[i].serializableFields()) {
                if (fieldDoc.type().typeName().equals("List")) {
                    continue;
                }
                field_comment.put(fieldDoc.name(), fieldDoc.commentText());
            }
        }
        return field_comment;
    }

    public static RootDoc getRoot() {
        return root;
    }

    private JavaDOCUtils(String classPath) {
        this.classPath = classPath;
    }

    public static Map<String, Object> init(String classPath) {
        new JavaDOCUtils(classPath);
        com.sun.tools.javadoc.Main.execute(new String[]{"-doclet", Doclet.class.getName(),
                "-encoding", "utf-8",
                "-classpath", classPath});
        return show();
    }

    /*
      public static void main(final String... args) throws Exception {
          调用com.sun.tools.javadoc.Main执行javadoc,参见 参考资料3
          javadoc的调用参数，参见 参考资料1
         -doclet 指定自己的docLet类名
         -classpath 参数指定 源码文件及依赖库的class位置，不提供也可以执行，但无法获取到完整的注释信息(比如annotation)
         -encoding 指定源码文件的编码格式
        com.sun.tools.javadoc.Main.execute(new String[]{"-doclet",Doclet.class.getName(),
                "-encoding", "utf-8",
                "-classpath",
                "E:\\ClassManagement\\ClassManagementSystemProject\\src\\main\\java\\com\\school\\management\\api\\entity\\Student.java"});
                 因为自定义的Doclet类并不在外部jar中，就在当前类中，所以这里不需要指定-docletpath 参数，
                "E:\\ClassManagement\\ClassManagementSystemProject\\out\\production\\classes;",
              "-docletpath",
              Doclet.class.getResource("/").getPath(),
      获取单个代码文件FaceLogDefinition.java的javadoc
        show();
      }
     */
}

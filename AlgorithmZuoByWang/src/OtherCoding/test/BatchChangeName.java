package OtherCoding.test;

import java.io.File;

public class BatchChangeName {
    public static void main(String[] args) {
        test3();
    }

    public static void test1() {
        File directory = new File("D:\\Notes\\国学");

        // 获取目录中的所有文件和目录的数组
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                // 只修改文件，不修改目录
                if (file.isFile()) {
                    // 获取原文件名
                    String oldName = file.getName();

                    String newName =
                            oldName.replace("-获取更多易学资料加微信3458344044", "");

                    // 创建一个新的文件名对象
                    File newFile = new File(directory, newName);

                    // 重命名文件
                    if (file.renameTo(newFile)) {
                        System.out.println(oldName + " has been renamed to " + newName);
                    } else {
                        System.out.println("Error renaming " + oldName);
                    }
                }
            }
        } else {
            System.out.println("The directory is empty or does not exist.");
        }
    }

    public static void test3() {
        File directory = new File("D:\\Notes\\前端\\资料\\第一阶段-html+css\\day11-day17");
        // 获取目录中的所有文件和目录的数组
        File[] fs1 = directory.listFiles();
        if (fs1 == null) {
            System.out.println("空");
            return;
        }
        for (File file : fs1) {//day01,day02 .... 文件夹
            File[] fs2 = file.listFiles();
            if (fs2 == null) {
                System.out.println("fs2 空");
                continue;
            }
            String name = gn(file);
            File newName = new File(directory, file.getName() + name);
            file.renameTo(newName);
        }
    }

    public static String gn(File f) {//提取文件夹里面关键词
        if (f == null) return "";
        String name = f.getName();
        if (f.isFile()) {//是文件且不是临时文件
            if (name.contains(".pdf")
                    || name.contains(".ppt")
                    || name.contains(".pptx")
//                    || name.contains(".md")
            ) {
                return "&" + name.replaceAll("^\\d+_", "")
                        .replaceAll("\\.\\w+$", "");
            } else return "";
        }
        StringBuilder sb = new StringBuilder();
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files == null) return "";
            for (File f2 : files) {
                sb.append(gn(f2));
            }
        }
        return sb.toString();
    }

}

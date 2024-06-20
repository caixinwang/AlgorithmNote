```java
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TyporaGetImagesFromRemoteToLocal {
    private static String content = "";
    private static Pattern URL_PT = Pattern.compile("(?<protocol>\\w+)://(?<host>[\\w._-]+)/(?<path>\\S*)");
    private static Pattern TYPORA_URL_PT = Pattern.compile("\\(((?<protocol>\\w+)://(?<host>[\\w._-]+)/(?<path>\\S*)/(?<filename>\\S*))\\)");
    private static Pattern PRE_SUF_FIX_PT = Pattern.compile("(?<prefix>\\w+)\\.(?<suffix>jpg|png)");

    public static void getAllTyporaImagesFromWeb(String dir) {
        getAllTyporaImagesFromWeb(Paths.get(dir));
    }

    private static void getAllTyporaImagesFromWeb(Path dir) {
        if (!Files.isDirectory(dir)) return;
        try {
            Files.walk(dir).forEach(child -> {
                if (!Files.isDirectory(child) && child.getFileName().toString().contains(".md")) {
                    try {
                        getTyporaImagesFromWeb(child);
                    } catch (Exception e) {
                        System.out.println(child + " 下载图片失败");
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(dir+"中的typora文件下载图片成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void getTyporaImagesFromWeb(Path tp) throws Exception{
        if (Files.isDirectory(tp)) return;
        try {
            Path image_dir = tp.getParent().resolve("image");
            if (!Files.exists(image_dir)) Files.createDirectories(image_dir);
            List<String> urlsFromTypora = getUrlsFromTypora(tp);
            AtomicBoolean flag = new AtomicBoolean(true);
            urlsFromTypora.forEach(url -> {
                try {
                    Path filesName = Paths.get(new URL(url).getFile()).getFileName();
                    downloadImage(url, image_dir.resolve(filesName));
                    System.out.println("图片"+url+"下载成功");
                } catch (Exception e) {
                    flag.set(false);
                    System.out.println("图片"+url+"下载失败");
                    throw new RuntimeException(e);
                }
            });
            if (flag.get()) changeHttpImageToLocal(tp, content);
        } catch (Exception e) {
            throw e;
        }
    }

    private static void downloadImage(String imageUrl, Path des) throws Exception {

        // 创建一个指向网络图片的URL对象
        URL url = new URL(imageUrl);
        // 使用ImageIO读取图片
        BufferedImage image = ImageIO.read(url);
        //如果目录不存在就创建
        Path parent = des.getParent();
        if (!Files.exists(parent)) Files.createDirectories(parent);
        //拿到图片的格式
        Matcher url_pt_macher = URL_PT.matcher(imageUrl);
        String path = "";
        String suffix = "";
        if (url_pt_macher.find()) {
            path = url_pt_macher.group("path");
        }
        if (path.equals("")) throw new IOException("path 为空");
        Matcher pre_suf_matcher = PRE_SUF_FIX_PT.matcher(path);
        if (pre_suf_matcher.find()) {
            suffix = pre_suf_matcher.group("suffix");
        }
        // 将图片写入本地文件
        ImageIO.write(image, suffix, new File(des.toString()));

    }


    private static void changeHttpImageToLocal(Path typoraPath, String content) throws IOException {
        Matcher matcher = TYPORA_URL_PT.matcher(content);
        String changed = matcher.replaceAll("(./image/${filename})");
        Files.write(typoraPath, changed.getBytes(StandardCharsets.UTF_8));
    }


    private static List<String> getUrlsFromTypora(Path tp) throws IOException {
        String s = Files.readString(tp, StandardCharsets.UTF_8);
        content = s;
        List<String> collect = TYPORA_URL_PT.matcher(s).results().map(r -> r.group(1)).collect(Collectors.toList());
        return collect;
    }


    public static void main(String[] args) throws Exception {
//        String url = "https://img-blog.csdnimg.cn/img_convert/25ff06202998a7eec828e81f978e338a.png";
//        getTyporaImagesFromWeb("D:\\Projects\\javaProjects\\ToolKits\\src\\main\\java\\FastDFS分布式文件存储系.md");
        getAllTyporaImagesFromWeb("D:\\桌面\\test");
//        }
    }

}

```

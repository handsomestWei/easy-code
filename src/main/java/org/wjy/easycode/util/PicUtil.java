package org.wjy.easycode.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 图片画框，最好保证图片后缀格式一致
 * 
 * @author weijiayu
 * @date 2023/5/15 11:59
 */
public class PicUtil {

    // 图片画框：原图覆盖
    public static boolean drawBorderWithCover(String fileFullPath, List<List<String>> regionXYList) {
        try {
            BufferedImage bufferedImage = drawBorder(fileFullPath, regionXYList);
            if (bufferedImage == null) {
                return false;
            }
            return ImageIO.write(bufferedImage, getFormatName(fileFullPath), new File(fileFullPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 图片画框：输出到新图片
    public static boolean drawBorderWithNewFile(String fileFullPath, String newFileFullPath,
        List<List<String>> regionXYList) {
        try {
            BufferedImage bufferedImage = drawBorder(fileFullPath, regionXYList);
            if (bufferedImage == null) {
                return false;
            }
            return ImageIO.write(bufferedImage, getFormatName(fileFullPath), new File(newFileFullPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 图片画框：返回修改后的byte数组
    public static byte[] drawBorderWithByte(String fileFullPath, List<List<String>> regionXYList) {
        try {
            BufferedImage bufferedImage = drawBorder(fileFullPath, regionXYList);
            if (bufferedImage == null) {
                return null;
            }
            // 转存
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, getFormatName(fileFullPath), stream);
            return stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 图片画框。传入归一化xy坐标
    private static BufferedImage drawBorder(String fileFullPath, List<List<String>> regionXYList) {
        try {
            BufferedImage image = ImageIO.read(new File(fileFullPath));
            int h = image.getHeight();
            int w = image.getWidth();
            Graphics2D g2d = image.createGraphics();
            // 初始化画布
            g2d.drawImage(image, 0, 0, null);
            // 定义线条信息
            Stroke dash =
                new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, new float[] {15, 0,}, 0f);
            g2d.setStroke(dash);
            // 设置线条颜色
            g2d.setColor(Color.RED);
            // 遍历坐标点
            for (int i = 0; i < regionXYList.size(); i++) {
                if (i == regionXYList.size() - 1) {
                    // 最后首尾相连
                    g2d.drawLine((int)(w * Float.parseFloat(regionXYList.get(i).get(0))),
                        (int)(h * Float.parseFloat(regionXYList.get(i).get(1))),
                        (int)(w * Float.parseFloat(regionXYList.get(0).get(0))),
                        (int)(h * Float.parseFloat(regionXYList.get(0).get(1))));
                } else {
                    // 每次画一条线
                    g2d.drawLine((int)(w * Float.parseFloat(regionXYList.get(i).get(0))),
                        (int)(h * Float.parseFloat(regionXYList.get(i).get(1))),
                        (int)(w * Float.parseFloat(regionXYList.get(i + 1).get(0))),
                        (int)(h * Float.parseFloat(regionXYList.get(i + 1).get(1))));
                }
            }
            // 释放资源
            g2d.dispose();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取图片格式后缀
    private static String getFormatName(String fileFullPath) {
        String[] pathArray = fileFullPath.split(".");
        return pathArray[pathArray.length - 1];
    }
}

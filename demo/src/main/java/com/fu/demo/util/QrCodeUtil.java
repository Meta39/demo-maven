package com.fu.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;

//二维码工具类
public class QrCodeUtil {

    /**
     * 生成二维码
     *
     * @param args
     * @throws IOException
     * @throws WriterException
     */
    public static void main(String[] args) throws IOException, WriterException {
        File file = new File("D:/1.png");
        generateQRCodeImage("13676217917", file.getCanonicalPath(), 200, 200, true);
    }

    /**
     * 生产二维码图片
     *
     * @param text        文本内容
     * @param filePath    生成文件路径
     * @param width       生成文件宽度
     * @param height      生成文件高度
     * @param deleteWhite 是否删除白边
     */
    public static void generateQRCodeImage(String text, String filePath, int width, int height, boolean deleteWhite) throws WriterException, IOException {

        //解决中文乱码
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        //是否删除白边
        if (deleteWhite) {
            BufferedImage image = deleteWhite(bitMatrix); //去白边的话加这一行
            File outputfile = new File(filePath);
            ImageIO.write(image, "png", outputfile);
            return;
        }
        //生成文件路径
        Path path = FileSystems.getDefault().getPath(filePath);
        //输出图像
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    //去白边
    private static BufferedImage deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }

        int width = resMatrix.getWidth();
        int height = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, resMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}


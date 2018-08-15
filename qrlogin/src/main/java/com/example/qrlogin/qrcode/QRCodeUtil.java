package com.example.qrlogin.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * @title: QRCodeUtil
 * @Description: 生成二维码
 * @author: roverxiafan
 * @date: 2017/4/17 18:22
 */
public class QRCodeUtil {
    private static final String CHARSET = "UTF-8";
    private static final String FORMAT_NAME = "JPG";
    private static final int QRCODE_SIZE = 300;
    private static final int LOGO_SIZE = 60;

    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param logo logo图片
     * @param needCompress 是否压缩logo
     * @return 二维码图片
     * @throws WriterException WriterException
     * @throws IOException IOException
     */
    private static BufferedImage createImage(String content, File logo, boolean needCompress) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logo == null || !logo.exists()) {
            return image;
        }

        QRCodeUtil.insertImage(image, logo, needCompress);
        return image;
    }

    private static void insertImage(BufferedImage source, File logo, boolean needCompress) throws IOException {
        if (!logo.exists()) {
            return;
        }

        Image src = ImageIO.read(logo);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) {
            if (width > LOGO_SIZE) {
                width = LOGO_SIZE;
            }
            if (height > LOGO_SIZE) {
                height = LOGO_SIZE;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }

        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成带logo的二维码
     * @param content 二维码内容
     * @param logo logo文件
     * @param destPath 二维码输出路径
     * @param needCompress 是否压缩logo
     * @throws IOException IOException
     * @throws WriterException WriterException
     */
    public static void encode(String content, File logo, String destPath, boolean needCompress) throws IOException, WriterException {
        BufferedImage image = QRCodeUtil.createImage(content, logo, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    /**
     * 生成不带logo的二维码
     * @param content 二维码内容
     * @param destPath 二维码输出路径
     * @throws IOException IOException
     * @throws WriterException WriterException
     */
    public static void encode(String content, String destPath) throws IOException, WriterException {
        QRCodeUtil.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码
     * @param content 二维码内容
     * @param logo logo
     * @param output 输出流
     * @param needCompress 是否压缩logo
     * @throws IOException IOException
     * @throws WriterException WriterException
     */
    public static void encode(String content, File logo, OutputStream output, boolean needCompress) throws IOException, WriterException {
        BufferedImage image = QRCodeUtil.createImage(content, logo, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 解析二维码
     * @param file 二维码图片
     * @return 解析内容
     * @throws IOException IOException
     * @throws NotFoundException NotFoundException
     */
    public static String decode(File file) throws IOException, NotFoundException {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 解析二维码
     * @param path 二维码图片路径
     * @return 解析内容
     * @throws IOException IOException
     * @throws NotFoundException NotFoundException
     */
    public static String decode(String path) throws IOException, NotFoundException {
        return QRCodeUtil.decode(new File(path));
    }

    public static void mkdirs(String dir) {
        if(StringUtils.isEmpty(dir)){
            return;
        }

        File file = new File(dir);
        if(file.isDirectory()){
            return;
        } else {
            file.mkdirs();
        }
    }

    public static void main(String[] args) throws Exception {
        String dir = "G:\\zz1zz.jpg";
        String content = "http://localhost/qrlogin/l/qdaEqa17";
        String logoImgPath = "G:\\logo.png";
        File file = new File(dir);
        QRCodeUtil.encode(content, new File(logoImgPath), new FileOutputStream(file), true);

        System.out.println(QRCodeUtil.decode("G:\\zz1zz.jpg"));
        System.out.println(QRCodeUtil.decode("G:\\zz1zz.jpg"));
    }
}
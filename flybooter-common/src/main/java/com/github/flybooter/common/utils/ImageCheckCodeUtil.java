package com.github.flybooter.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class ImageCheckCodeUtil {

    private static String[] wordPool = {"2", "3", "4", "5", "6", "7", "8", "9"};

    public static ImageCheckCode genImage(int imgWidth, int imgHeight, int codeCount) throws IOException {
        int fontHeight = imgHeight - 2;
        int codeY = imgHeight - 12;

        StringBuilder code = new StringBuilder();
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imgWidth, imgHeight);
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontHeight));
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);
        g.setColor(new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)));
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(imgWidth);
            int y = random.nextInt(imgHeight);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        for (int i = 0; i < codeCount; i++) {
            String word = wordPool[random.nextInt(wordPool.length)];
            code.append(word);
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            g.drawString(String.valueOf(word), (i) * imgWidth / (codeCount + 1), codeY);

        }
        g.dispose();
        ImageCheckCode imageCheckCode = new ImageCheckCode();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", output);
        imageCheckCode.setCode(code.toString());
        imageCheckCode.setImage(output.toByteArray());
        return imageCheckCode;
    }

    public static class ImageCheckCode {
        private String code;
        private byte[] image;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }
    }
}

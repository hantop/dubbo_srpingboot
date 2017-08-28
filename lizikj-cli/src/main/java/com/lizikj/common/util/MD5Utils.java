package com.lizikj.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    private static final int RANDOM_SALT_LENGTH = 5;

    private static char[] CH = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 使用md5的算法进行加密
     *
     * @param src
     * @return
     * @date 2017年1月3日
     */
    public static byte[] md5(byte[] src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            return messageDigest.digest(src);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }
        return new byte[0];
    }

    public static String encryptMD5(byte[] src) {
        StringBuilder sb = new StringBuilder();
        byte[] bb = md5(src);
        for (int i = 0; i < bb.length; i++) {
            // 将高4位转换成字符串
            int x = (bb[i] >>> 4 & 0x0f);
            sb.append(CH[x]);
            // 将低4位转换成字符串
            x = (bb[i] & 0x0f);
            sb.append(CH[x]);
        }
        return sb.toString();
    }

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String src, String salt) {
        if (isBlank(src)) {
            return null;
        }
        try {
            if (!StringUtils.isEmpty(salt)) {
                src = src + "{" + salt + "}";
            }
            StringBuilder buffer = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F'};
            byte[] b = messageDigest.digest(src.getBytes());

            for (int i = 0; i < b.length; i++) {
                // 将高4位转换成字符串
                int x = (b[i] >>> 4 & 0x0f);
                buffer.append(ch[x]);
                // 将低4位转换成字符串
                x = (b[i] & 0x0f);
                buffer.append(ch[x]);
            }
            return buffer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static String md5(String src) {
        return md5(src, null);
    }

    /**
     * 管理员密码md5
     * 先将密码md5，toLowerCase()
     * 再将md5后的密码加上salt再次md5一次
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String pwdMd5(String pwd, String salt) {
        return md5(md5(pwd).toLowerCase(), salt);
    }


    /**
     * 获取随机salt
     *
     * @return
     */
    public static String randomSalt() {
        StringBuilder salt = new StringBuilder();

        Random random = new Random();
        // 随机获取ASCII码上33~126
        for (int i = 0; i < RANDOM_SALT_LENGTH; i++) {
            int a = random.nextInt(94) + 33;
            salt.append((char) a);
        }

        return salt.toString();
    }
}

package com.lizikj.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class SHA1 {
    public static final String key = "Jt0M1ls6HXDV54zyKvq7OFmEbicNjIpWwUPnQgGLe9kC3oSBrdTaR2AuZhxf8Y";

    public static String getSHA1(String decript)
            throws NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest
                .getInstance("SHA-1");
        digest.update(decript.getBytes());
        byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }
}

package com.github.flybooter.common.utils;

import com.github.flybooter.common.constants.AuthoritiesConstants;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HmacSHAUtil {

    public static String hmacSHA256(String message, String appSecretKey) {
        String signature = null;
        try {
            Mac hasher = Mac.getInstance("HmacSHA256");
            hasher.init(new SecretKeySpec(appSecretKey.getBytes(), "HmacSHA256"));

            byte[] hash = hasher.doFinal(message.getBytes());

            // 获得十六进制形式的签名
            signature = DatatypeConverter.printHexBinary(hash).toLowerCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
        }

        return signature;
    }

    public static void main(String[] args) {
        String authKey = "662335513980608210";
        String authSecretKey = "25B790B5E8B3464FU114F5E9B0895EA9";

        String body = "{\"phone\":\"18684275630\",\"idCard\":\"220182199112195714\",\"name\":\"大大大\",\"mobile\":\"18684275630\"}";


        String message1  = String.format("%s%s%s%s%s", "GET", AuthoritiesConstants.SEPARATOR, authKey, AuthoritiesConstants.SEPARATOR, authSecretKey);

        String message2 = String.format("%s%s%s%s%s%s%s", "POST", AuthoritiesConstants.SEPARATOR, body, AuthoritiesConstants.SEPARATOR, authKey, AuthoritiesConstants.SEPARATOR, authSecretKey);


        System.out.println(message1+"GET ---> "+HmacSHAUtil.hmacSHA256(message1, authSecretKey));
        System.out.println(message2+"POST ---> "+HmacSHAUtil.hmacSHA256(message2, authSecretKey));
    }
}

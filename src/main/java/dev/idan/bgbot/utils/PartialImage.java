package dev.idan.bgbot.utils;

import dev.idan.bgbot.entities.Token;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.URL;
import java.security.MessageDigest;

public class PartialImage {

    @SneakyThrows
    public static String getDomain(String urlAsStr) {
        URL url = new URL(urlAsStr);
        String domain = url.getHost();

        if (domain.startsWith("www.")) domain = domain.substring(4);
        return domain;
    }

    @SneakyThrows
    public static String getEmail(String userAvatar, String userMail, Token token) {
        if (userAvatar == null || token.isUseGravatar() || isPrivateNetwork(getDomain(userAvatar))) {
            MessageDigest md = MessageDigest.getInstance("MD5");

            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest(userMail.getBytes())) sb.append(String.format("%02x", b));

            userAvatar = "https://www.gravatar.com/avatar/" + sb;
        }

        return userAvatar;
    }

    @SneakyThrows
    public static boolean isPrivateNetwork(String domain) {
        InetAddress ipAddress = InetAddress.getByName(domain);
        return ipAddress.isSiteLocalAddress() || ipAddress.isLoopbackAddress();
    }

}

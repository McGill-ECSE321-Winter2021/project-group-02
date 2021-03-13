package ca.mcgill.ecse321.scrs.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper
{
    //helper functions

    public static String hash(String string){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    string.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }catch (NoSuchAlgorithmException e){
            System.out.println("bad algorithm");
        }
        return "error";
    }

    // from https://www.baeldung.com/sha-256-hashing-java
    private static String bytesToHex(byte[] hash)
    {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

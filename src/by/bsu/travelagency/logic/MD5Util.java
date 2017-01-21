package by.bsu.travelagency.logic;

import by.bsu.travelagency.logic.exception.BusinessLogicException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Михаил on 5/22/2016.
 */
public class MD5Util {

    /**
     * Md 5 encode.
     *
     * @param st the st
     * @return the string
     * @throws BusinessLogicException the business logic exception
     */
    public static String md5Encode(String st) throws BusinessLogicException {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessLogicException("Failed to MD5 encode.", e);
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

}

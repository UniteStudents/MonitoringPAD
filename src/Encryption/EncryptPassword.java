//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Encryption;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptPassword {
    public EncryptPassword() {
    }

    public static String decrypt(String strToDecrypt) {
        try {
            Key var5 = getKey();
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(2, var5);
            return new String(desCipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Key var5 = getKey();
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(1, var5);
            return Base64.getEncoder().encodeToString(desCipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private static Key getKey() {
        try {
            byte[] var4 = getBytes("2-9-0-9-1-9-8-0");
            DESKeySpec pass = new DESKeySpec(var4);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            SecretKey s = skf.generateSecret(pass);
            return s;
        } catch (Exception var41) {
            var41.printStackTrace();
            return null;
        }
    }

    private static byte[] getBytes(String str) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            StringTokenizer st = new StringTokenizer(str, "-", false);

            while(st.hasMoreTokens()) {
                int var5 = Integer.parseInt(st.nextToken());
                bos.write((byte)var5);
            }

            byte[] var52 = bos.toByteArray();
            byte[] var51 = var52;
            return var51;
        } finally {
            closeResource(bos);
        }
    }

    private static void closeResource(Closeable closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {
            ;
        }

    }
}

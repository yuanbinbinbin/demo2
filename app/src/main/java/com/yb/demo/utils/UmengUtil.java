package com.yb.demo.utils;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.umeng.commonsdk.framework.UMEnvelopeBuild;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class UmengUtil {

    public static void decode(String msg){

    }

    public static String createUMID(Context context) {
        String id = UMEnvelopeBuild.imprintProperty(context, "umid", null);
       // Log.e("test",com.umeng.commonsdk.stateless.f.a(context).getAbsolutePath());
        Log.e("test", new String(Base64.decode("dW1weF9pbnRlcm5hbA==", 0)));
        Log.e("test", "umid: " + id);
        Log.e("test", "encrypt Id : " + Encrypt.encryptBySHA1(id));
        return id;
    }

    private static class Encrypt {

        private static final byte[] iv = new byte[]{10, 1, 11, 5, 4, 15, 7, 9, 23, 3, 1, 6, 8, 12, 13, 91};

        public static byte[] encrypt(byte[] var0, byte[] var1) throws Exception {
            Cipher var2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec var3 = new SecretKeySpec(var1, "AES");
            IvParameterSpec var4 = new IvParameterSpec(iv);
            var2.init(1, var3, var4);
            return var2.doFinal(var0);
        }

        public static byte[] decrypt(byte[] var0, byte[] var1) throws Exception {
            Cipher var2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec var3 = new SecretKeySpec(var1, "AES");
            IvParameterSpec var4 = new IvParameterSpec(iv);
            var2.init(2, var3, var4);
            return var2.doFinal(var0);
        }

        public static String encryptBySHA1(String var0) {
            MessageDigest var1 = null;
            String var2 = null;
            byte[] var3 = var0.getBytes();

            try {
                var1 = MessageDigest.getInstance("SHA1");
                var1.update(var3);
                var2 = bytes2Hex(var1.digest());
                return var2;
            } catch (Exception var5) {
                return null;
            }
        }

        static String bytes2Hex(byte[] var0) {
            String var1 = "";
            String var2 = null;

            for (int var3 = 0; var3 < var0.length; ++var3) {
                var2 = Integer.toHexString(var0[var3] & 255);
                if (var2.length() == 1) {
                    var1 = var1 + "0";
                }

                var1 = var1 + var2;
            }

            return var1;
        }
    }
}

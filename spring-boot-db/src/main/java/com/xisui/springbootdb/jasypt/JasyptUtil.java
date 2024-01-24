package com.xisui.springbootdb.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

public class JasyptUtil {

    public static void main(String[] args) {
        testPwdEncrypt();
//        testPwdDecrypt();
    }

    public static void testPwdEncrypt() {

        // 实例化加密器
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        // 配置加密算法和秘钥
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setPassword("chengxuyuanjidian");      // 用于加密的秘钥(盐)，可以是随机字符串，或者固定，一定要存储好，不要被其他人知道
//        config.setAlgorithm("PBEWithMD5AndDES");    // 设置加密算法，默认
        config.setPoolSize(2);
        encryptor.setConfig(config);

        // 对密码进行加密
        String myPwd = "imooc";
        String encryptedPwd = encryptor.encrypt(myPwd);
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("+ 原密码为：" + myPwd);
        System.out.println("+ 加密后的密码为：" + encryptedPwd);
        System.out.println("++++++++++++++++++++++++++++++");
        String decMyPwd = encryptor.decrypt(encryptedPwd);
        System.out.println("+ 解密后的密码为：" + decMyPwd);
    }

    public static void testPwdDecrypt() {

        // 实例化加密器
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        // 配置加密算法和秘钥
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setPassword("chengxuyuanjidian");      // 用于加密的秘钥(盐)，可以是随机字符串，或者固定，一定要存储好，不要被其他人知道
//        config.setAlgorithm("PBEWithMD5AndDES");    // 设置加密算法，默认
        encryptor.setConfig(config);

        // 对密码进行解密
        String pendingPwd = "XD8gz6KU4d/n3DKgeNASBw==";
        String myPwd = encryptor.decrypt(pendingPwd);
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("+ 解密后的密码为：" + myPwd);
        System.out.println("++++++++++++++++++++++++++++++");
    }
}

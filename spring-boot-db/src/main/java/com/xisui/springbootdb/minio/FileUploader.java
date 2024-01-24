package com.xisui.springbootdb.minio;

import io.minio.*;
import io.minio.errors.MinioException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

public class FileUploader {
  public static void main(String[] args) {
      // Create a minioClient with the MinIO server playground, its access key and secret key.
      MinioClient minioClient =
          MinioClient.builder()
              .endpoint("http://localhost:9000")
              .credentials("eftA5FDCWJm0EqoI83DN", "4sElhuaVl7eJZbIjkZikeOvYa8XuoDWZWjZJS70Y")
              .build();

    //uploadObjectArgs(minioClient, "storage", "ja-netfilter.zip", "C:\\Users\\xisui\\Downloads\\ja-netfilter.zip");
//    putObjectArgs(minioClient, "storage", "resource/ja-netfilter.zip", "C:\\Users\\xisui\\Downloads\\ja-netfilter.zip");

    //putObjectArgs(minioClient, "storage", "composite/ja-netfilter.zip", "C:\\Users\\xisui\\Downloads\\ja-netfilter.zip");
   // copyObjectArgs(minioClient, "storage", "apps/system_info.txt", "storage", "resource/system_info.txt");
    composeObjectArgs(minioClient, "storage", "系统提醒信息.xls");
  }

  public static void uploadObjectArgs(MinioClient minioClient, String bucketName, String objectName, String filePath){
    https://github.com/minio/minio-java/blob/release/examples/UploadObject.java
      try {
          // check bucket if not exist.
          boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
          if (!found) {
            // Make a new bucket
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
          } else {
            System.out.println("Bucket already exists. bucketName: " + bucketName);
          }

          // Upload 'C:\Users\xisui\Downloads\ja-netfilter.zip' as object name 'asiaphotos-2015.zip' to bucket 'storage'.
          minioClient.uploadObject(
                  UploadObjectArgs.builder()
                          .bucket(bucketName)
                          .object(objectName)
                          .filename(filePath)
                          .build());
          System.out.println("successfully uploaded");
      } catch (MinioException e) {
          System.out.println("MinioException occurred: " + e);
          System.out.println("HTTP trace: " + e.httpTrace());
      } catch (IOException e) {
        System.out.println("IOException occurred: " + e);
      } catch (NoSuchAlgorithmException e) {
        System.out.println("NoSuchAlgorithmException occurred: " + e);
      } catch (InvalidKeyException e) {
        System.out.println("InvalidKeyException occurred: " + e);
      }
  }

  public static void putObjectArgs(MinioClient minioClient, String bucketName, String objectName, String filePath){
    // https://github.com/minio/minio-java/blob/release/examples/PutObject.java
    try {
      // check bucket if not exist.
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!found) {
        // Make a new bucket
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
      } else {
        System.out.println("Bucket already exists. bucketName: " + bucketName);
      }

      File file = new File(filePath);
      try(var fileInputStream = Files.newInputStream(Path.of(file.toURI()))) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(fileInputStream, file.length(), -1)
                        .build());
      }

      System.out.println("successfully uploaded");
    } catch (MinioException e) {
      System.out.println("MinioException occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
    } catch (IOException e) {
      System.out.println("IOException occurred: " + e);
    } catch (NoSuchAlgorithmException e) {
      System.out.println("NoSuchAlgorithmException occurred: " + e);
    } catch (InvalidKeyException e) {
      System.out.println("InvalidKeyException occurred: " + e);
    }
  }

  public static void copyObjectArgs(MinioClient minioClient, String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName){
    //https://github.com/minio/minio-java/blob/release/examples/CopyObject.java
    try {
      // check bucket if not exist.
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(targetBucketName).build());
      if (!found) {
        // Make a new bucket
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(targetBucketName).build());
      } else {
        System.out.println("Bucket already exists. bucketName: " + targetBucketName);
      }

      minioClient.copyObject(
              CopyObjectArgs.builder()
                      .bucket(targetBucketName)
                      .object(targetObjectName)
                      .source(CopySource.builder().bucket(sourceBucketName).object(sourceObjectName).build())
                      .build());

      System.out.println("successfully uploaded");
    } catch (MinioException e) {
      System.out.println("MinioException occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
    } catch (IOException e) {
      System.out.println("IOException occurred: " + e);
    } catch (NoSuchAlgorithmException e) {
      System.out.println("NoSuchAlgorithmException occurred: " + e);
    } catch (InvalidKeyException e) {
      System.out.println("InvalidKeyException occurred: " + e);
    }
  }

  public static void composeObjectArgs(MinioClient minioClient, String bucketName, String objectName){
    // https://github.com/minio/minio-java/blob/release/examples/ComposeObject.java
    try {
      // check bucket if not exist.
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!found) {
        // Make a new bucket
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
      } else {
        System.out.println("Bucket already exists. bucketName: " + bucketName);
      }

      ComposeSource s =
              ComposeSource.builder().bucket(bucketName).object(objectName).build();

      minioClient.composeObject(
              ComposeObjectArgs.builder()
                      .bucket(bucketName)
                      .object("compose_"+objectName)
                      .sources(Collections.singletonList(s))
                      .build());

      System.out.println("successfully uploaded");
    } catch (MinioException e) {
      System.out.println("MinioException occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
    } catch (IOException e) {
      System.out.println("IOException occurred: " + e);
    } catch (NoSuchAlgorithmException e) {
      System.out.println("NoSuchAlgorithmException occurred: " + e);
    } catch (InvalidKeyException e) {
      System.out.println("InvalidKeyException occurred: " + e);
    }
  }
}

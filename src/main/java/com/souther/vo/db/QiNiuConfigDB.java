package com.souther.vo.db;

import lombok.Getter;
import lombok.Setter;

/**
 * 七牛上传配置
 */
@Setter
@Getter
public class QiNiuConfigDB {

  /**
   * AccessKey
   */
  private String ak;
  /**
   * SecretKey
   */
  private String sk;
  /**
   * 存储空间
   */
  private String bucket;
  /**
   * 七牛访问前缀
   */
  private String visitPrefix;
  /**
   * 存储文件夹
   */
  private String uploadFolder;
}

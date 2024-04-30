package com.xisui.springbootai.domain;

import com.alibaba.cloud.ai.tongyi.image.TongYiImagesOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageOptionsDTO extends TongYiImagesOptions {
  /**图片描述*/
  private String instructions ;
  // getter, setter
}

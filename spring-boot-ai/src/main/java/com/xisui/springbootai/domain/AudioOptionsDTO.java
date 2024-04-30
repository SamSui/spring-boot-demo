package com.xisui.springbootai.domain;

import com.alibaba.cloud.ai.tongyi.audio.TongYiAudioSpeechOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AudioOptionsDTO extends TongYiAudioSpeechOptions {
  /**文本描述*/
  private String instructions ;
  // getter, setter
}

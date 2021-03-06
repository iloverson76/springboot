package com.chp.modules.mybatis.service.dto;

import java.util.List;

import com.chp.modules.mybatis.dao.entity.AudioClip;

/**
 * @Class Name AudioClipPageVo
 * @Author huzhenjia
 * @Create In 2017年12月11日
 */
public class AudioClipPageDto {
   
  
  private int pageCount;
  
  
  private List<AudioClip> audioClip;

  private int total;

  /**
   * @Return the int pageCount
   */
  public int getPageCount() {
    return pageCount;
  }

  /**
   * @Param int pageCount to set
   */
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  /**
   * @Return the List<AudioClip> audioClip
   */
  public List<AudioClip> getAudioClip() {
    return audioClip;
  }

  /**
   * @Param List<AudioClip> audioClip to set
   */
  public void setAudioClip(List<AudioClip> audioClip) {
    this.audioClip = audioClip;
  }

  /**
   * @Return the int total
   */
  public int getTotal() {
    return total;
  }

  /**
   * @Param int total to set
   */
  public void setTotal(int total) {
    this.total = total;
  }
  
  
}

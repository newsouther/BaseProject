package com.souther.vo.po;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 * @Description
 * @Author souther
 * @Date 2020/5/20 9:12
 * @Version 1.0
 **/
@Data
public class PageParam<T> implements Serializable {

  private static final long serialVersionUID = 7646589067857794970L;

  private int pageNum = 1;

  private int pageSize = 10;

  private T param;

  private Map<String,Object> like;

  private Map<String,Boolean> order;

}

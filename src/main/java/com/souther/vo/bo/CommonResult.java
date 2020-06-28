package com.souther.vo.bo;

import com.souther.common.constant.ResultEnum;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description
 * @Author souther
 * @Date 2020/5/20 9:51
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
public class CommonResult<T> implements Serializable {

  private static final long serialVersionUID = 5588593047569528242L;
  private int code;
  private String msg;
  private T data;

  public CommonResult() {
    this.code = ResultEnum.SUCCESS.getCode();
    this.msg = ResultEnum.SUCCESS.getMsg();
  }

  public CommonResult(String msg) {
    this.code = ResultEnum.SUCCESS.getCode();
    this.msg = msg;
  }

  public CommonResult(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public CommonResult(T data) {
    this.code = ResultEnum.SUCCESS.getCode();
    this.msg = ResultEnum.SUCCESS.getMsg();
    this.data = data;
  }

  public static <T> CommonResult<T> error(String msg) {
    return new CommonResult<T>(400, msg);
  }

  public static <T> CommonResult<T> error(int code, String msg) {
    return new CommonResult<T>(code, msg);
  }
}

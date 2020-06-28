package com.souther.common.exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

  private Integer code;

  public MyException(Integer code, String message) {
    super(message);
    this.code = code;
  }

}

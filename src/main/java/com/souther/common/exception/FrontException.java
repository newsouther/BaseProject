package com.souther.common.exception;

import lombok.Getter;

@Getter
public class FrontException extends RuntimeException {

  private Integer code;

  public FrontException(Integer code, String message) {
    super(message);
    this.code = code;
  }

}

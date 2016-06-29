package com.song.exception;

/**
 * Created by song on 16/6/18.
 */
public class GlobalException extends Exception {


  private String failingSqlStatement;

  public GlobalException(String failingSqlStatement) {
    this.failingSqlStatement = failingSqlStatement;
  }

  public GlobalException(String message, String failingSqlStatement) {
    super(message);
    this.failingSqlStatement = failingSqlStatement;
  }

  public GlobalException(String message, Throwable cause, String failingSqlStatement) {
    super(message, cause);
    this.failingSqlStatement = failingSqlStatement;
  }

  public GlobalException(Throwable cause, String failingSqlStatement) {
    super(cause);
    this.failingSqlStatement = failingSqlStatement;
  }

  public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String failingSqlStatement) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.failingSqlStatement = failingSqlStatement;
  }


  public String getFailingSqlStatement() {
    return failingSqlStatement;
  }

  public void setFailingSqlStatement(String failingSqlStatement) {
    this.failingSqlStatement = failingSqlStatement;
  }
}

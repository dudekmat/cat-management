package com.github.dudekmat.catmanagement.auth.event;

import org.springframework.context.ApplicationEvent;

public class LoginFailureEvent extends ApplicationEvent {

  public LoginFailureEvent(Object source) {
    super(source);
  }
}

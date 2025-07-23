package com.vovgoo.demo.aop.captcha;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyCaptcha {
}

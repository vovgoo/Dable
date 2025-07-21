package com.vovgoo.demo.aop.captcha;

import com.vovgoo.demo.dtos.CaptchaAware;
import com.vovgoo.demo.exceptions.CaptchaVerificationException;
import com.vovgoo.demo.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CaptchaAspect {

    private final CaptchaService captchaService;

    @Before("@annotation(com.vovgoo.demo.aop.captcha.VerifyCaptcha)")
    public void verifyCaptcha(JoinPoint joinPoint) {
        for(Object arg : joinPoint.getArgs()) {
            if(arg instanceof CaptchaAware captchaAware) {
                String captcha = captchaAware.getCaptcha();
                if(!captchaService.verifyCaptcha(captcha)) {
                    throw new CaptchaVerificationException("Captcha verification failed");
                }
            }
        }
    }
}

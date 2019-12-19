/*
 * package com.zdl.aspect;
 * 
 * import javax.servlet.http.Cookie; import
 * javax.servlet.http.HttpServletRequest;
 * 
 * import org.aspectj.lang.annotation.Aspect; import
 * org.aspectj.lang.annotation.Before; import
 * org.aspectj.lang.annotation.Pointcut; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.redis.core.StringRedisTemplate; import
 * org.springframework.stereotype.Component; import
 * org.springframework.util.StringUtils; import
 * org.springframework.web.context.request.RequestContextHolder; import
 * org.springframework.web.context.request.ServletRequestAttributes;
 * 
 * 
 * import com.zdl.constant.CookieConstant; import
 * com.zdl.constant.RedisConstant; import
 * com.zdl.exception.SellerAuthorizeException; import com.zdl.util.CookieUtil;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Aspect
 * 
 * @Component
 * 
 * @Slf4j public class SellerAuthorizeAspect {
 * 
 * @Autowired private StringRedisTemplate redisTemplate;
 * 
 * @Pointcut("execution(public * com.zdl.controller.Seller*.*(..))"+
 * "&&!execution(public * com.zdl.controler.SellerUserController.*(..))") public
 * void verify() {}
 * 
 * @Before("verify()") public void doVerify() { ServletRequestAttributes
 * attributes = (ServletRequestAttributes)
 * RequestContextHolder.getRequestAttributes(); HttpServletRequest request =
 * attributes.getRequest(); //查询cookie Cookie cookie = CookieUtil.get(request,
 * CookieConstant.TOKEN); if (cookie == null) {
 * log.warn("【登录校验】Cookie中查不到token"); throw new SellerAuthorizeException(); }
 * 
 * //去redis里查询 String tokenValue =
 * redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,
 * cookie.getValue())); if (StringUtils.isEmpty(tokenValue)) {
 * log.warn("【登录校验】Redis中查不到token"); throw new SellerAuthorizeException(); } } }
 */
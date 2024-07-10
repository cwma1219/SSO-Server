package org.example.cas_example1.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.cas_example1.service.RedisService;
import org.example.cas_example1.utils.RequestUtil;
import org.example.cas_example1.utils.TokenUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //檢查有沒有本地的jwt => 有且驗證後就放行
        Cookie cookieExample2 = RequestUtil.getCookie(request, "example2");
        if (cookieExample2 != null) {
            String jwt = cookieExample2.getValue();
            if (jwt == null) {
                return redirect(response);
            }
            log.info("jwt: {}", jwt);
            String login = redisService.get(jwt);
            if (login == null) {
                return redirect(response);
            }
            log.info("login: {}", login);
            Claims claims = TokenUtil.parsePayload(jwt);
            if (!login.equals(claims.get("login", String.class))) {
                return redirect(response);
            }
            return true;
        }

        //檢查有沒有ST
        String st = request.getParameter("ST");
        RestTemplate restTemplate = new RestTemplate();
        if (st == null) {
            //沒有ST 向cas請求ST
            Cookie cookie = RequestUtil.getCookie(request, "ST");
            if (cookie == null) {
                return redirect(response);
            } else {
                st = cookie.getValue();
            }

        }

        //有ST
        //1.向SSO詢問ST是否有效
        String url = "http://35.247.95.83:9090/sso/verify?ST=" + st + "&serverId=2";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = new JSONObject(result.getBody());
        boolean verify = jsonObject.getBoolean("result");
        if (!verify) {
            //ST無效，重新登入
            return redirect(response);
        } else {
            //建立本服務的通行證/憑證
            String login = request.getParameter("login");
            String jwt = TokenUtil.genToken(login);
            setCookie(response, "example2", jwt);
            log.info("jwt: {}", jwt);
            redisService.save(jwt, login);
        }

        return true;
    }

    public void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }

    public boolean redirect( HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("location", "http://35.247.95.83:80/login?service=http://35.234.61.195:80/home&serverId=2");
//        map.put("location", "http://localhost:3000/login?service=http://localhost:3002/home&serverId=2");
        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(new JSONObject(map).toString());
        return false;
    }
}

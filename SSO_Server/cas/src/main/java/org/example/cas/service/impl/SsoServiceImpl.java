package org.example.cas.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.cas.entity.Account;
import org.example.cas.repository.AccountRepository;
import org.example.cas.service.RedisService;
import org.example.cas.service.SsoService;
import org.example.cas.utils.Argon2SpringUtils;
import org.example.cas.utils.TokenUtil;
import org.example.cas.vo.SsoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class SsoServiceImpl implements SsoService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RedisService redisService;

    @Override
    public Map<String, String> login(SsoVo vo, String serverId, HttpServletResponse response, HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();
        Map<String, String> save = new HashMap<>();
        //驗證帳號密碼
        Cookie tgcCookie = getCookie(request, "TGC");
        Account account = accountRepository.findAccountByUsername(vo.getLogin());
        if (account == null) {
            throw new RuntimeException("帳號不存在");
        } else {
            if (!Argon2SpringUtils.decoder(account.getPassword(), vo.getPw())) {
                throw new RuntimeException("密碼錯誤");
            }
            if (tgcCookie == null) {
                // 產生TGC
                String TGC = TokenUtil.genToken(vo.getLogin());
                setCookie(response, "TGC", TGC);

                // 產生ST
                String ST = TokenUtil.genToken(vo.getLogin());
                map.put("ST", ST);
                save.put(serverId, ST);

                // 寫入Redis
                log.debug("map: {}", map);
                redisService.save(vo.getLogin(), save);
            }
        }
        return map;
    }

    @Override
    public void register(SsoVo vo) {
        System.out.println(Argon2SpringUtils.encoder(vo.getPw()));
        Account account = new Account();
        account.setUsername(vo.getLogin());
        account.setPassword(Argon2SpringUtils.encoder(vo.getPw()));
        account.setCreateDate(new Date());
        account.setModifyDate(new Date());
        accountRepository.save(account);

    }

    @Override
    public boolean verify(String st, String id, HttpServletRequest request) {

        Claims claims = TokenUtil.parsePayload(st);
        String login = claims.get("login", String.class);

        Map<String, String> map = redisService.get(login);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(id) && entry.getValue().equals(st)) {
                redisService.deleteHash(login, id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, String> get(String serverId, HttpServletResponse response, HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();
        Map<String, String> save = new HashMap<>();

        //驗證TGC
        Cookie tgcCookie = getCookie(request, "TGC");
        if (tgcCookie == null) {
            log.info("TGC不存在");
            throw new RuntimeException("TGC不存在");
        }

        String tgc = tgcCookie.getValue();
        log.info("TGC: {}", tgcCookie.getValue());
        Claims claims = TokenUtil.parsePayload(tgc);
        String login = claims.get("login", String.class);
        log.info("login: {}", login);

        //產生該服務的ST
        String ST = TokenUtil.genToken(login);
        log.info("new ST: {}", ST);
        map.put("ST", ST);
        map.put("login", login);
        save.put(serverId, ST);
        //寫入Redis
        redisService.save(login, save);

        return map;
    }

    public void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }

    public Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

}

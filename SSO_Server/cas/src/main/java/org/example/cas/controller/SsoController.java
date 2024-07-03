package org.example.cas.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.cas.service.SsoService;
import org.example.cas.vo.SsoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sso")
@Slf4j
public class SsoController {

    @Autowired
    SsoService ssoService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("service") String url, @RequestParam("serverId") String serverId, @RequestBody SsoVo vo, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = ssoService.login(vo, serverId, response, request);
        map.put("location", url);
        map.put("login", vo.getLogin());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody SsoVo vo) {
        Map<String, Object> map = new HashMap<>();
        ssoService.register(vo);
        map.put("result", "success");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestParam("ST") String st, @RequestParam("serverId") String id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", ssoService.verify(st, id, request));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/st")
    public ResponseEntity<Map<String, String>> get(@RequestParam("service") String url, @RequestParam("serverId") String serverId, HttpServletResponse response, HttpServletRequest request) {
        log.info("service: {}, serverId: {}", url, serverId);
        Map<String, String> map = ssoService.get(serverId, response, request);
        map.put("location", url);
        return ResponseEntity.ok(map);
    }
}

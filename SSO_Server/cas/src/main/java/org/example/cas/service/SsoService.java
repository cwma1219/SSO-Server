package org.example.cas.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cas.vo.SsoVo;

import java.util.Map;

public interface SsoService {

    Map<String, String> login(SsoVo vo, String serverId, HttpServletResponse response, HttpServletRequest request);

    void register(SsoVo vo);

    boolean verify(String st, String id, HttpServletRequest request);

    Map<String, String> get(String serverId, HttpServletResponse response, HttpServletRequest request);
}

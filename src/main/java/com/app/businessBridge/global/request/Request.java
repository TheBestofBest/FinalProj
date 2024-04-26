package com.app.businessBridge.global.request;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class Request {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final HttpSession session;
    private User user;
//    @Setter
//    private Member member = null;

    public Request(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {

        this.req = req;
        this.resp = resp;
        this.session = session;

        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        } else {
            this.user = null;
        }
    }

    public boolean isLogin() {
        return user != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }

//    Member Entity Merge하고나서 주석 풀고 사용하세요
    // 현재 로그인한 member 가져오는 method
//    public Member getMember() {
//        if (isLogout()) {
//            return null;
//        }
//
//        if (member == null) {
//            member = memberService.findByUsername(getLoginedSiteUserUsername());
//        }
//
//        return member;
//    }


    private String getLoginedSiteUserUsername() {
        if (isLogout()) return null;

        return user.getUsername();
    }

}

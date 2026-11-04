package edu.uow.csit314.idp.config;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
/** Same-origin session-bound CSRF protection for all state-changing API requests, including login. */
@Component
public class CsrfProtectionFilter extends OncePerRequestFilter {
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException {
  if(req.getRequestURI().startsWith("/api/")&&!Set.of("GET","HEAD","OPTIONS").contains(req.getMethod())&&!SessionSecurity.valid(req.getSession(false),req.getHeader("X-CSRF-Token"))){res.setStatus(403);res.setContentType("application/json");res.getWriter().write("{\"message\":\"CSRF token is missing or invalid. Refresh and try again.\"}");return;}chain.doFilter(req,res);
 }
}

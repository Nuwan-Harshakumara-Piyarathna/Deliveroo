
package com.pdn.eng.Security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AdminFilter extends OncePerRequestFilter {
	
	@Autowired
	MyUserDetailsService userDetails;
	
	@Autowired
	AdminFilterUtil AdminFilterUtil;
	
	


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader=request.getHeader("Authorization");
		System.out.println("Filtering Admin...");
		
		String mobileNumber=null;
		String jwt=null;
			
			if(authorizationHeader==null) {
				
				 throw new AccessDeniedException("Access Denied: Failed");
				
			}
			

			
			if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ") ) {
				jwt= authorizationHeader.substring(7);
				mobileNumber=AdminFilterUtil.getUsernameFromToken(jwt);
				
				
			}
			
			if (mobileNumber!= null && SecurityContextHolder.getContext().getAuthentication() == null) {
	
				@SuppressWarnings("unused")
				UserDetails userDetail = userDetails.loadUserByUsername(mobileNumber);
			}
			
			if (!AdminFilterUtil.validateToken(jwt, userDetails.loadUserByUsername(mobileNumber))) {
				
				throw new AccessDeniedException("Access Denied: Failed");
				
			}
	
			if (AdminFilterUtil.validateToken(jwt, userDetails.loadUserByUsername(mobileNumber))) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));	
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	
			}
			
			filterChain.doFilter(request, response);
	}
	
	//add filter only for /admin/ paths
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		final String urlPath=request.getServletPath();
		AntPathMatcher path=new AntPathMatcher();
		if(path.match("/admin/**", urlPath)) {
			return false;
		}
		
		return true;
	}
	
	
}


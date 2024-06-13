package com.maple.maple_boss_now.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

        private final Object principal;
        private String credentials;

        public JwtAuthenticationToken(Object principal, String credentials) {
            super(null);
            this.principal = principal;
            this.credentials = credentials;
            setAuthenticated(false);
        }

        public JwtAuthenticationToken(Object principal, String credentials, Object authorities) {
            super(null);
            this.principal = principal;
            this.credentials = credentials;
            setAuthenticated(true);
        }

        @Override
        public Object getCredentials() {
            return credentials;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            if (isAuthenticated) {
                throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
            }

            super.setAuthenticated(false);
        }

        @Override
        public void eraseCredentials() {
            super.eraseCredentials();
            credentials = null;
        }
}

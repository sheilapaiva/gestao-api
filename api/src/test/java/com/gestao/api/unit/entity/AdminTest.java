package com.gestao.api.unit.entity;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gestao.api.entity.Admin;

import org.junit.jupiter.api.Assertions;

import java.util.Collection;

public class AdminTest {

    @Test
    void testGettersAndSetters() {
        Admin admin = new Admin();
        admin.setUsername("adminUser");
        admin.setPassword("adminPass");

        Assertions.assertEquals("adminUser", admin.getUsername());
        Assertions.assertEquals("adminPass", admin.getPassword());
    }

    @Test
    void testConstructor() {
        Admin admin = new Admin("adminUser", "adminPass");

        Assertions.assertEquals("adminUser", admin.getUsername());
        Assertions.assertEquals("adminPass", admin.getPassword());
    }

    @Test
    void testGetAuthorities() {
        Admin admin = new Admin("adminUser", "adminPass");

        Collection<? extends GrantedAuthority> authorities = admin.getAuthorities();
        
        Assertions.assertNotNull(authorities);
        Assertions.assertEquals(1, authorities.size());
        Assertions.assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}

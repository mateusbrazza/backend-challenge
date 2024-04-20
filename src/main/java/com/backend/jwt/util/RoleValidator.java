package com.backend.jwt.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleValidator {
    private static Set<String> validRoles = new HashSet<>();

    static {
        loadValidRoles();
    }

    private static void loadValidRoles() {
        Resource resource = new ClassPathResource("roles.txt");
        try (InputStream is = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                validRoles.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidRole(String role) {
        return validRoles.contains(role);
    }
}
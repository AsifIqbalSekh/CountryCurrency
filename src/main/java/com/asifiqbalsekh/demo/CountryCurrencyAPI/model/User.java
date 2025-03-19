package com.asifiqbalsekh.demo.CountryCurrencyAPI.model;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@Document(value = "user-details")
public class User implements UserDetails {

    @Id
    String email;
    String password;
    String fullName;
    Set<Roles> roles;
    boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(item->new SimpleGrantedAuthority(item.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

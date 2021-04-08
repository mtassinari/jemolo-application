package it.laziocrea.jemoloapp.service.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import it.laziocrea.jemoloapp.domain.Authority;

@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper {

    default Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if(authoritiesAsString != null){
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }
    
    default Set<String> stringFromAuthority(Set<Authority> authoritiesAsAuthority) {
        Set<String> authorities = new HashSet<>();
        
        if(authoritiesAsAuthority != null) {
        	authorities = authoritiesAsAuthority.stream().map(Authority::getName).collect(Collectors.toSet());
        	return authorities;
        }
        
        return authorities;
    }
}

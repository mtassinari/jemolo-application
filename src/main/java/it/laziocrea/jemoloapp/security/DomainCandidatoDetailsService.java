package it.laziocrea.jemoloapp.security;

import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.repository.CandidatoRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import it.laziocrea.jemoloapp.service.util.AttributeEncryptor;

/**
 * Authenticate a candidato from the database.
 */
@Component("candidatoDetailsService")
public class DomainCandidatoDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainCandidatoDetailsService.class);

    private final CandidatoRepository userRepository;
    private final AttributeEncryptor attributeEncryptor;
    public DomainCandidatoDetailsService(CandidatoRepository userRepository, AttributeEncryptor attributeEncryptor) {
        this.userRepository = userRepository;
        this.attributeEncryptor = attributeEncryptor;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating candidato {}", login);
        log.debug("Authenticating {}", attributeEncryptor.convertToDatabaseColumn(login));
        if (new EmailValidator().isValid(login, null)) {
        	String login2 = attributeEncryptor.convertToDatabaseColumn(login);
            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login2)
                .map(candidato -> createSpringSecurityUser(login2, candidato))
                .orElseThrow(() -> new UsernameNotFoundException("Iscritto with email " + login + " was not found in the database"));
        }

        return userRepository.findOneWithAuthoritiesByLogin(login)
            .map(candidato -> createSpringSecurityUser(login, candidato))
            .orElseThrow(() -> new UsernameNotFoundException("Iscritto " + login + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, Candidato user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("Iscritto " + login + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
            user.getPassword(),
            grantedAuthorities);
    }
}

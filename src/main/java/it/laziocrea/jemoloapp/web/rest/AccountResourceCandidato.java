package it.laziocrea.jemoloapp.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import it.laziocrea.jemoloapp.config.Constants;
import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.PersistentCandidatoToken;
import it.laziocrea.jemoloapp.domain.PersistentToken;
import it.laziocrea.jemoloapp.domain.User;
import it.laziocrea.jemoloapp.repository.CandidatoRepository;
import it.laziocrea.jemoloapp.repository.PersistentCandidatoTokenRepository;
import it.laziocrea.jemoloapp.repository.PersistentTokenRepository;
import it.laziocrea.jemoloapp.security.AuthoritiesConstants;
import it.laziocrea.jemoloapp.security.SecurityUtils;
import it.laziocrea.jemoloapp.service.CandidatoService;
import it.laziocrea.jemoloapp.service.MailService2;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.PasswordChangeDTO;
import it.laziocrea.jemoloapp.web.rest.errors.CodiceFiscaleNotFoundException;
import it.laziocrea.jemoloapp.web.rest.errors.EmailAlreadyUsedException;
import it.laziocrea.jemoloapp.web.rest.errors.EmailNotFoundException;
import it.laziocrea.jemoloapp.web.rest.errors.InvalidPasswordException;
import it.laziocrea.jemoloapp.web.rest.vm.KeyAndPasswordVM;
import it.laziocrea.jemoloapp.web.rest.vm.ManagedCandidatoVM;

/**
 * REST controller for managing the current candidato's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResourceCandidato {
	
    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private final Logger log = LoggerFactory.getLogger(AccountResourceCandidato.class);

    private final CandidatoRepository candidatoRepository;
    
    private final CandidatoService candidatoService;

    private final MailService2 mailService;

    private final PersistentCandidatoTokenRepository persistentTokenRepository;

    public AccountResourceCandidato(CandidatoRepository candidatoRepository, CandidatoService userService, MailService2 mailService, PersistentCandidatoTokenRepository persistentTokenRepository) {

        this.candidatoRepository = candidatoRepository;
        this.candidatoService = userService;
        this.mailService = mailService;
        this.persistentTokenRepository = persistentTokenRepository;
    }
    
    /**
     * {@code POST  /candidato/register} : register the andidato.
     *
     * @param managedCandidatoVM the managed candidato View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws CodiceFiscaleAlreadyUsedException {@code 400 (Bad Request)} if the codiceFiscale is already used.
     */
    @PostMapping("/candidato/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCandidato(@Valid @RequestBody ManagedCandidatoVM managedCandidatoVM) {
        if (!checkPassword(managedCandidatoVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        log.debug("CodiceFiscale {}", managedCandidatoVM.getCodiceFiscale());
        Candidato candidato = candidatoService.registerCandidato(managedCandidatoVM, managedCandidatoVM.getPassword());
        mailService.sendActivationEmail(candidato);
    }
       
    /**
     * {@code GET  /candidati/activate} : activate the registered user.
     *
     * @param activationkey the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the candidato couldn't be activated.
     */
    @GetMapping("/candidati/activate")
    public void activateAccount(@RequestParam(value = "activationkey") String key) {
        Optional<Candidato> candidato = candidatoService.activateCandidatoRegistration(key);
        if (!candidato.isPresent()) {
            throw new AccountResourceException("No candidato was found for this activation key");
        }
    }  
    
    /**
     * {@code GET  /candidato/authenticate} : check if the candidato is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the candidato is authenticated.
     */
    @GetMapping("/candidato/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current candidato is authenticated");
        return request.getRemoteUser();
    }
   
    /**
     * {@code POST  /candidato/account} : update the current candidato information.
     *
     * @param candidatoDTO the current candidato information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */    
    @PostMapping("/candidato/account")
    public void saveAccount(@Valid @RequestBody CandidatoDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<Candidato> existingUser = candidatoRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<Candidato> user = candidatoRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        candidatoService.updateUser(userDTO.getNome(), userDTO.getCognome(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }
    
    /**
     * {@code GET  /candidato/account} : get the current candidato.
     *
     * @return the current candidato.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the candidato couldn't be returned.
     */
    @GetMapping("/candidato/account")
    public CandidatoDTO getAccount() {
        return candidatoService.getCandidatoWithAuthorities()
            .map(CandidatoDTO::new)
            .orElseThrow(() -> new AccountResourceException("Candidato could not be found"));
    }
    
    /**
     * {@code POST  /candidato/account/change-password} : changes the current candidato's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/candidato/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPassword(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        /*log.debug("passwordChangeDto.getNewPassword(): {}",passwordChangeDto.getNewPassword());
        log.debug("passwordChangeDto.getCurrentPassword(): {}",passwordChangeDto.getCurrentPassword());*/
        candidatoService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }
    
    /**
     * {@code GET  /candidato/account/sessions} : get the current open sessions.
     *
     * @return the current open sessions.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the current open sessions couldn't be retrieved.
     */
    @GetMapping("/candidato/account/sessions")
    public List<PersistentCandidatoToken> getCurrentSessions() {
        return persistentTokenRepository.findByCandidato(
            candidatoRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AccountResourceException("Current candidato login not found")))
                    .orElseThrow(() -> new AccountResourceException("Candidato could not be found"))
        );
    }
    
    /**
     * {@code DELETE  /candidato/account/sessions?series={series}} : invalidate an existing session.
     *
     * - You can only delete your own sessions, not any other user's session
     * - If you delete one of your existing sessions, and that you are currently logged in on that session, you will
     *   still be able to use that session, until you quit your browser: it does not work in real time (there is
     *   no API for that), it only removes the "remember me" cookie
     * - This is also true if you invalidate your current session: you will still be able to use it until you close
     *   your browser or that the session times out. But automatic login (the "remember me" cookie) will not work
     *   anymore.
     *   There is an API to invalidate the current session, but there is no API to check which session uses which
     *   cookie.
     *
     * @param series the series of an existing session.
     * @throws UnsupportedEncodingException if the series couldn't be URL decoded.
     */
    @DeleteMapping("/candidato/account/sessions/{series}")
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        SecurityUtils.getCurrentUserLogin()
            .flatMap(candidatoRepository::findOneByLogin)
            .ifPresent(u ->
            persistentTokenRepository.findByCandidato(u).stream()
                    .filter(persistentToken -> StringUtils.equals(persistentToken.getSeries(), decodedSeries))
                    .findAny().ifPresent(t -> persistentTokenRepository.deleteById(decodedSeries)));
    }
    
    /**
     * {@code POST   /candidato/account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address is not registered.
     */
    @PostMapping(path = "/candidato/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           candidatoService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
     * {@code POST   /candidato/account/reset-password/init2} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address is not registered.
     */
    @PostMapping(path = "/candidato/account/reset-password/init2")
    public void requestPasswordReset2(@RequestBody String codiceFiscale) {
       mailService.sendPasswordResetMail(
           candidatoService.requestPasswordReset2(codiceFiscale)
               .orElseThrow(CodiceFiscaleNotFoundException::new)
       );
    }

    /**
     * {@code POST   /candidato/account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/candidato/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPassword(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<Candidato> candidato = candidatoService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!candidato.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }
    
    /**
     * {@code DELETE /candidato/account/:codiceFiscale} : delete the "codiceFiscale" Candidato.
     *
     * @param codiceFiscale the codice fiscale of the Candidato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // @DeleteMapping("/candidato/account/{codiceFiscale:" + Constants.CODICE_FISCALE_REGEX + "}")
    @DeleteMapping("/candidato/account/{codiceFiscale}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.CANDIDATO + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String codiceFiscale) {
        log.debug("REST request to delete User: {}", codiceFiscale);
        candidatoService.deleteAccount(codiceFiscale);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,  "userManagement.deleted", codiceFiscale)).build();
    }

    private static boolean checkPassword(String password) {
    	Pattern p = Pattern.compile(Constants.PASSWORD_REGEX);
    	Matcher m = p.matcher(password);
    	return m.matches();
    }
    
}

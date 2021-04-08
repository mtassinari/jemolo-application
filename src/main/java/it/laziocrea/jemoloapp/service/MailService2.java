package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService2 {

    private final Logger log = LoggerFactory.getLogger(MailService2.class);

    private static final String CANDIDATO = "candidato";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;
    
    private final CandidatoService candidatoService;
    
    public MailService2(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine, CandidatoService candidatoService) {
    	this.candidatoService = candidatoService;
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to Candidato '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to candidato '{}'", to, e);
            } else {
                log.warn("Email could not be sent to candidato '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(Candidato candidato, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(candidato.getLangKey());
        Context context = new Context(locale);
        context.setVariable(CANDIDATO, candidato);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(candidato.getEmail(), subject, content, false, true);
    }
    
    @Async
    public void sendEmailFromTemplate(CandidatoDTO candidato, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(candidato.getLangKey());
        Context context = new Context(locale);
        context.setVariable(CANDIDATO, candidato);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(candidato.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendSchedaCreationEmail(AnagraficaCandidatoDTO scheda) {
    	Optional<CandidatoDTO> candidatoDTO = candidatoService.findOne(scheda.getId());
    	if (candidatoDTO.isPresent()) {   		
	        log.debug("Sending activation email to '{}'", candidatoDTO.get().getEmail());
	        sendEmailFromTemplate(candidatoDTO.get(), "mail/schedaCreationEmail", "email.schedacreation.title");
    	}
    }
    
    @Async
    public void sendSchedaUpdateEmail(AnagraficaCandidatoDTO scheda) {
    	Optional<CandidatoDTO> candidatoDTO = candidatoService.findOne(scheda.getId());
    	if (candidatoDTO.isPresent()) {   		
	        log.debug("Sending activation email to '{}'", candidatoDTO.get().getEmail());
	        sendEmailFromTemplate(candidatoDTO.get(), "mail/schedaUpdateEmail", "email.schedaupdate.title");
    	}
    }
    
    @Async
    public void sendActivationEmail(Candidato candidato) {
        log.debug("Sending activation email to '{}'", candidato.getEmail());
        sendEmailFromTemplate(candidato, "mail/candidatoActivationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(Candidato candidato) {
        log.debug("Sending creation email to '{}'", candidato.getEmail());
        sendEmailFromTemplate(candidato, "mail/candidatoCreationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(Candidato candidato) {
        log.debug("Sending password reset email to '{}'", candidato.getEmail());
        sendEmailFromTemplate(candidato, "mail/candidatoPasswordResetEmail", "email.reset.title");
    }

	public void sendHalfMonthEmail(Candidato candidato) {
		log.debug("Sending half month alert '{}'", candidato.getEmail());
		sendEmailFromTemplate(candidato, "mail/halfMonthAlert", "email.halfmonth.title");
	}

	public void sendOneMonthEmail(Candidato candidato) {
		log.debug("Sending one month alert '{}'", candidato.getEmail());
		sendEmailFromTemplate(candidato, "mail/oneMonthAlert", "email.onemonth.title");
	}
}

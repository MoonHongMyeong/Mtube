package me.moon.Mtube.util;

import lombok.NoArgsConstructor;
import me.moon.Mtube.dto.mail.MailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@NoArgsConstructor
@PropertySource("classpath:application-mail.yml")
public class MailSender {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(MailDto mailDto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getAddress());
        mailMessage.setFrom(from);
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());

        mailSender.send(mailMessage);
    }
}

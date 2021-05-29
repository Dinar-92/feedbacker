package tech.itpark.configuration;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.itpark.controller.FeedbackController;
import tech.itpark.controller.UserController;
import tech.itpark.bodyconverter.BodyConverter;
import tech.itpark.bodyconverter.GsonBodyConverter;
import tech.itpark.crypto.PasswordHasher;
import tech.itpark.crypto.PasswordHasherDefaultImpl;
import tech.itpark.crypto.TokenGenerator;
import tech.itpark.crypto.TokenGeneratorDefaultImpl;
import tech.itpark.http.Handler;
import tech.itpark.security.Roles;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class AppConfiguration {
  @Bean
  public DataSource dataSource() throws NamingException {
    final var cxt = new InitialContext();
    return (DataSource) cxt.lookup("java:/comp/env/jdbc/db");
  }

  @Bean
  public PasswordHasher passwordHasher(MessageDigest digest) {
    return new PasswordHasherDefaultImpl(digest);
  }

  @Bean
  public MessageDigest messageDigest() throws NoSuchAlgorithmException {
    return MessageDigest.getInstance("SHA-256");
  }

  @Bean
  public TokenGenerator tokenGenerator() {
    return new TokenGeneratorDefaultImpl();
  }

  @Bean
  public List<BodyConverter> bodyConverters() {
    return List.of(
        new GsonBodyConverter(new Gson())
    );
  }

  @Bean
  public Map<String, Handler> linkTrace(UserController ctrl) {
    return Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/register", ctrl::register),
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/delete", ctrl::delete),
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/restore", ctrl::restore),
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/login", ctrl::login),
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/logout", ctrl::logout),
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/password", ctrl::updatePassword),
            new AbstractMap.SimpleEntry<String, Handler>("/api/auth/secret", ctrl::updateSecret)
    );
  }
  @Bean
  public Map<String, Handler> linkTrace(FeedbackController ctrl) {
    return Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Handler>("/api/wiki", ctrl::getAll),
            new AbstractMap.SimpleEntry<String, Handler>("/api/wiki/create", ctrl::create),
            new AbstractMap.SimpleEntry<String, Handler>("/api/wiki/update", ctrl::update),
            new AbstractMap.SimpleEntry<String, Handler>("/api/wiki/remove", ctrl::remove)

    );
  }
  @Bean
  public Map<String, Set<String>> accessRoles() {
    return Map.ofEntries(
            new AbstractMap.SimpleEntry<>("/api/auth/delete", Set.of(Roles.ROLE_ADMIN, Roles.ROLE_MODERATOR, Roles.ROLE_USER)),

            new AbstractMap.SimpleEntry<>("/api/feedback", Set.of(Roles.ROLE_ANONYMOUS, Roles.ROLE_USER, Roles.ROLE_MODERATOR, Roles.ROLE_ADMIN))
    );
  }
}

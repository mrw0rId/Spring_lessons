package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ru.geekbrains.controller.UserController;
import ru.geekbrains.persist.RepoFactory;
import ru.geekbrains.persist.RepositoryInterface;
import ru.geekbrains.util.DBConnection;
import ru.geekbrains.util.RepoType;

import java.sql.Connection;
import java.sql.SQLException;

@EnableWebMvc
@ComponentScan("ru.geekbrains")
@Configuration
public class AppConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private ApplicationContext applicationContext;

    private RepoFactory repoFactory;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext, RepoFactory repoFactory) {
        this.applicationContext = applicationContext;
        this.repoFactory = repoFactory;
    }

    @Bean
    @Autowired
    public RepositoryInterface userRepository(DBConnection dbConnection) {
        Connection connection = dbConnection.getConnection();
        try {
            logger.info("Requesting RemoteUserRepository");
            return repoFactory.createRepo(RepoType.USER_REMOTE, connection);
        } catch (Exception n) {
            logger.info("No connection: " + n.getMessage());
            logger.info("Switching to LocalUserRepository");
            return repoFactory.createRepo(RepoType.USER_LOCAL, connection);
        }
    }


    @Bean
    @Autowired
    public RepositoryInterface productRepository(DBConnection dbConnection) {
        Connection connection = dbConnection.getConnection();
        try {
            logger.info("Requesting RemoteProductRepository");
            return repoFactory.createRepo(RepoType.PRODUCT_REMOTE, connection);
        } catch (Exception n) {
            logger.info("No connection: " + n.getMessage());
            logger.info("Switching to LocalProductRepository");
            return repoFactory.createRepo(RepoType.PRODUCT_LOCAL, connection);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public ViewResolver htmlViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
        resolver.setContentType("text/html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setViewNames(new String[]{"*"});
        return resolver;
    }

    private ITemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setCacheable(false);
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
}

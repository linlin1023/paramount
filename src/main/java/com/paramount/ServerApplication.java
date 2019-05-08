package com.paramount;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.http.HttpStatus;

/**
 * Created by szz on 2018/3/23 23:08.
 * Email szhz186@gmail.com
 */
@SpringBootApplication
@ComponentScan
//@MapperScan(basePackages = "com.paramount", markerInterface =Mapper.class)
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {

		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/pages/error/401.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/pages/error/404.html");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/pages/error/500.html");

			container.addErrorPages(error401Page, error404Page, error500Page);
		});
	}
	@Autowired
    private SolrClient solrClient;


	@Value("${spring.data.solr.core}")
	private String coreName;

	@Bean
    public SolrTemplate getSolrTemplate(){
	    return new SolrTemplate(solrClient );
    }

    @Bean
	public ReloadableResourceBundleMessageSource getMessageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}



}

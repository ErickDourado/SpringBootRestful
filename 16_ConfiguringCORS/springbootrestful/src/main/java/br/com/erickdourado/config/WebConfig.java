package br.com.erickdourado.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erickdourado.serialization.converter.YamlJackson2HttpMessageConverter;

@Configuration //Colocamos essa anotação para o Spring ler essa classe enquanto o app ainda está compilando.
public class WebConfig implements WebMvcConfigurer {
	
	//Essa constante vem do pacote serialization.converter
	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");
	
	@Value("${cors.originPatterns}")
	private String corsOriginPatterns = "";
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		String[] allowedOrigins = corsOriginPatterns.split(",");
		
		registry.addMapping("/**")
		//.allowedMethods("GET", "POST", "PUT") Aceitar métodos específicos.
		.allowedMethods("*") // Aceitar todos os métodos.
		.allowedOrigins(allowedOrigins)
		.allowCredentials(true); // Para possibilitar autenticação.
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		//VIA PARÂMETRO DA REQUISIÇÃO: http://localhost:8080/api/person/v1?mediaType=xml

		//configurer.favorParameter(true) //Aqui diz que o ContentNegotiation pode ser passado por parâmetro.
				//.parameterName("mediaType") //O nome do parâmetro para quando quisermos passar ele.
				//.ignoreAcceptHeader(true) //Mostrando que o ContentNegotiation não pode ser passado via Header.
				//.useRegisteredExtensionsOnly(false)//Não foi explicado pra que serve exatamente.
				//.defaultContentType(MediaType.APPLICATION_JSON)//Tipo de serialização padrão é JSON.
				//.mediaType("json", MediaType.APPLICATION_JSON)//A seguir, a lista de valores para passar no parâmetro.
				//.mediaType("xml", MediaType.APPLICATION_XML);
		
		//VIA HEADER DA REQUISIÇÃO: http://localhost:8080/api/person/v1
		
		configurer.favorParameter(false) //Aqui diz que o ContentNegotiation não pode ser passado por parâmetro.
				.ignoreAcceptHeader(false) //Mostrando que o ContentNegotiation vai ser passado via Header.
				.useRegisteredExtensionsOnly(false)//Não foi explicado pra que serve exatamente.
				.defaultContentType(MediaType.APPLICATION_JSON)//Tipo de serialização padrão é JSON.
				.mediaType("json", MediaType.APPLICATION_JSON)//A seguir, a lista de valores para passar no parâmetro.
				.mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
	}

}

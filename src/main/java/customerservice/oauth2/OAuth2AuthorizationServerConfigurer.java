package customerservice.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@Configuration
public class OAuth2AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

	@Value("${oauth2.clientId}")
	private String clientId;

	@Value("${oauth2.secret}")
	private String secret;

	@Value("${oauth2.scopes}")
	private String[] scopes;

	/* OAuth2 in memory credentials */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(secret).scopes(scopes).and().build();
	}

}

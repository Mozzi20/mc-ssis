package io.github.mozzi20;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Component
public class NameApi {

	private RestTemplate restTemplate;
	private static URI NAME_URL;

	public NameApi() throws URISyntaxException {
		restTemplate = new RestTemplate();
		NAME_URL = new URI("https://18mosu.ssis.nu/mc_name.php?email=");
	}

	public Optional<NameDto> getName(String email) {
		NameDto result = restTemplate.getForObject(NAME_URL + email, NameDto.class);
		return Optional.ofNullable(result);
	}

	@Data
	public static class NameDto {
		private String firstname;
		private String lastname;
		private String klass;
	}
}

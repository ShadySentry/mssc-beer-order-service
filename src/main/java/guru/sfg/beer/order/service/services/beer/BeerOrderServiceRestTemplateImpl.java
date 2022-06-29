package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreInvalidFields = false)
@Component
public class BeerOrderServiceRestTemplateImpl implements BeerService {
    private final String BEER_PATH = "/api/v1/beerUpc/{upc}";
    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    public BeerOrderServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public BeerDto getBeer(String upc) {
        String url = beerServiceHost + BEER_PATH;
        log.debug("Calling beer service with URL " + url);
        log.debug("UPC");

        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<BeerDto>() {
                        }, (Object) upc);

        return responseEntity.getBody();
    }
}

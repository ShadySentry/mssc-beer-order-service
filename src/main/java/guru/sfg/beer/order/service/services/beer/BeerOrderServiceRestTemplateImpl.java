package guru.sfg.beer.order.service.services.beer;

import guru.sfg.brewery.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreInvalidFields = false)
@Component
public class BeerOrderServiceRestTemplateImpl implements BeerService {
    public static final String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";

    public static final String BEER_PATH_V1 = "/api/v1/beer/";
    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    public BeerOrderServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeer(String upc) {
        String url = beerServiceHost + BEER_UPC_PATH_V1;
        log.debug("Calling beer service with URL " + url);
        log.debug("UPC");


        return Optional.ofNullable(restTemplate.getForObject(url+upc,BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID uuid) {
        String url = beerServiceHost + BEER_PATH_V1;
        log.debug("Calling beer service with URL " + url);
        log.debug("beerId "+uuid.toString());

        return Optional.ofNullable(restTemplate.getForObject(url+uuid.toString(),BeerDto.class));
    }
}

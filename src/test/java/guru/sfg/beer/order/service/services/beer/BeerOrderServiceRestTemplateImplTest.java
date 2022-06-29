package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.bootstrap.BeerOrderBootStrap;
import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderServiceRestTemplateImplTest {
    @Autowired
    BeerService beerService;

    @Test
    void getBeerTest() {
        BeerDto beerDto = beerService.getBeer(BeerOrderBootStrap.BEER_1_UPC);

        assertTrue(beerDto != null);
        assertTrue(beerDto.getUpc().compareToIgnoreCase(beerDto.getUpc()) == 0);
    }

    @Test
    void getBeerWrongUpc() {

        assertThrows(HttpServerErrorException.InternalServerError.class, () -> {
            beerService.getBeer("0083783375214");
        });
    }
}
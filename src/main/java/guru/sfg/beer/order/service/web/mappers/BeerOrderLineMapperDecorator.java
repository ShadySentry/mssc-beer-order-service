package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;

    private BeerOrderLineMapper beerOrderMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    public void setBeerOrderMapper(BeerOrderLineMapper beerOrderMapper) {
        this.beerOrderMapper = beerOrderMapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        Optional<BeerDto> beerDtoOptional = beerService.getBeer(line.getUpc());

        BeerOrderLineDto orderLineDto = beerOrderMapper.beerOrderLineToDto(line);
        beerDtoOptional.ifPresent(beerDto -> {
            orderLineDto.setBeerName(beerDto.getBeerName());
            orderLineDto.setBeerStyle(beerDto.getBeerStyle());
            orderLineDto.setPrice(beerDto.getPrice());
            orderLineDto.setBeerId(beerDto.getId());
        });

        return orderLineDto;
    }

//    @Override
//    public BeerOrderLineDto beerOrderLineToDtoNoBeerInfo(BeerOrderLine line) {
//        return beerOrderMapper.beerOrderLineToDto(line);
//    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {
        return beerOrderMapper.dtoToBeerOrderLine(dto);
    }
}

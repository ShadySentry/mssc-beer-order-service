package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

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
        BeerDto beer = beerService.getBeer(line.getUpc());

        BeerOrderLineDto lineDto = beerOrderMapper.beerOrderLineToDto(line);
        lineDto.setBeerName(beer.getBeerName());
        lineDto.setBeerStyle(beer.getBeerStyle());

        return lineDto;
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

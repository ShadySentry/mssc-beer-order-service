package guru.sfg.beer.order.service.sm.actions;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.services.BeerOrderManagerImpl;
import guru.sfg.beer.order.service.web.mappers.BeerOrderMapper;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;

    private final BeerOrderMapper beerOrderMapper;


    public AllocateOrderAction(JmsTemplate jmsTemplate, BeerOrderRepository beerOrderRepository, BeerOrderMapper beerOrderMapper) {
        this.jmsTemplate = jmsTemplate;
        this.beerOrderRepository = beerOrderRepository;
        this.beerOrderMapper = beerOrderMapper;
    }


    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {

        String beerOrderId = (String) context.getMessageHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findById(UUID.fromString(beerOrderId)).get();
                                                                    //have to be AllocateOrderRequest  but not beerOrderDto ????
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE, beerOrderMapper.beerOrderToDto(beerOrder));

        log.debug("Sent allocation Request for Order Id: " + beerOrderId);

    }
}

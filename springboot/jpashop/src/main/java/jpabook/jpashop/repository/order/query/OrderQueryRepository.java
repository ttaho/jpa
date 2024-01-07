package jpabook.jpashop.repository.order.query;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();

        result.forEach(o ->{
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId){
        String jpql = "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) "
            + "from OrderItem oi "
            + "join oi.item i "
            + "where oi.order.id = :orderId";
        List<OrderItemQueryDto> orders = em.createQuery(jpql, OrderItemQueryDto.class)
            .setParameter("orderId", orderId)
            .getResultList();
        return orders;
    }
    public List<OrderQueryDto> findOrders(){
        String jpql = "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) "
            + "from Order o "
            + "join o.member m join o.delivery d";
        List<OrderQueryDto> resultList = em.createQuery(jpql, OrderQueryDto.class)
            .getResultList();
        return resultList;
    }


    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(
            orderIds);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;

    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        String jpql = "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) "
            + "from OrderItem oi "
            + "join oi.item i "
            + "where oi.order.id in :orderIds";
        List<OrderItemQueryDto> orderItems = em.createQuery(jpql, OrderItemQueryDto.class)
            .setParameter("orderIds", orderIds)
            .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
            .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
            .map(orderQueryDto -> orderQueryDto.getOrderId())
            .collect(Collectors.toList());
        return orderIds;
    }

    public List<OrderFlatDto> findAllByDto_flat() {

        String jpql = "select new "
            + "jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) "
            + "from Order o "
            + "join o.member m "
            + "join o.delivery d "
            + "join o.orderItems oi "
            + "join oi.item i ";
        return em.createQuery(jpql, OrderFlatDto.class)
            .getResultList();
    }
}

package org.gamein.marketservergamein2022.infrastructure.repository.market;

import org.gamein.marketservergamein2022.core.sharedkernel.entity.Order;
import org.gamein.marketservergamein2022.core.sharedkernel.enums.OrderType;
import org.gamein.marketservergamein2022.infrastructure.repository.market.OrderRepoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;


@Repository
public class OrderRepoCustomImpl implements OrderRepoCustom {
    @Autowired
    private EntityManager em;
    @Override
    public List<Order> allOrders(OrderType type, Long productId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> orderRoot = cq.from(Order.class);

        Predicate totalPredicate = cb.and(
                cb.isFalse(orderRoot.get("cancelled")),
                cb.isFalse(orderRoot.get("archived")),
                cb.isNull(orderRoot.get("acceptDate"))
        );

        if (productId != null) {
            Predicate productPredicate = cb.equal(orderRoot.get("product").get("id"), productId);
            totalPredicate = cb.and(totalPredicate, productPredicate);
        }
        if (type != null) {
            Predicate typePredicate = cb.equal(orderRoot.get("type"), type);
            totalPredicate = cb.and(totalPredicate, typePredicate);
        }

        cq.where(totalPredicate);

//        if (type != null) {
//            if (type == OrderType.SELL) {
//                cq.orderBy(cb.asc(orderRoot.get("unitPrice")));
//            } else {
//                cq.orderBy(cb.desc(orderRoot.get("unitPrice")));
//            }
//        }
        cq.orderBy(cb.desc(orderRoot.get("submitDate")));

        TypedQuery<Order> query = em.createQuery(cq);
        return query.getResultList();
    }
}

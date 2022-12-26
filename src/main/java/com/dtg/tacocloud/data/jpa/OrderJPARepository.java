package com.dtg.tacocloud.data.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dtg.tacocloud.model.TacoOrder;

public interface OrderJPARepository extends CrudRepository<TacoOrder, Long>{

	List<TacoOrder> findByDeliveryZip(String deliveryZip);
}

package ae.dt.deliveryorder.repository;

import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.BolDetail;


public interface BolDetailsRepository extends CrudRepository<BolDetail,String> {

	BolDetail findByBolBolId(Long bolId);



}

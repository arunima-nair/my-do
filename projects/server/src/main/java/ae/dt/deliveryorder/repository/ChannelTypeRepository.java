package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.ChannelType;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface ChannelTypeRepository extends CrudRepository<ChannelType,String> {
	
	  @Query("select c from ChannelType c where c.value = :value") 
	  ChannelType findByValue(@Param("value")String value);
	 

	 	
}

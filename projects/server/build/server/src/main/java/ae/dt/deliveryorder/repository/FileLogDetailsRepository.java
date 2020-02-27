package ae.dt.deliveryorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.FileLogDetail;

/**
 * Created by Joseph Vibik on 06/23/2019.
 */
public interface FileLogDetailsRepository extends CrudRepository<FileLogDetail, Long>, JpaSpecificationExecutor<FileLogDetail>{
	
	List<FileLogDetail> findByFileLogFileLogId(Long valueOf);
	
}

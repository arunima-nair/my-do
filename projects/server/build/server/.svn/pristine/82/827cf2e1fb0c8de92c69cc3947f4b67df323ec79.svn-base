package ae.dt.deliveryorder.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.FileLogDetail;

/**
 * Created by Joseph Vibik on 06/23/2019.
 */
public interface FileLogRepository extends CrudRepository<FileLog, Long>, JpaSpecificationExecutor<FileLog> {
	
	@Query("select sum(case when d.isProcessed = 'Y' then 1 else 0 end),sum(case when d.isProcessed = 'N' then 1 else 0 end), count(d)  from FileLogDetail d where d.fileLog.fileLogId = :fileLogId")
	Object[] getCountDetails(@Param("fileLogId") Long fileLogId);

	FileLog findByFileLogId(@Param("fileLogId") Long fileLogId);

	@Query(value = "SELECT SEQ_FILE_REF.nextval FROM dual", nativeQuery = true)
	Long getNextFileRefNo();
}


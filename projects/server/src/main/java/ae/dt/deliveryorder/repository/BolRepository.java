package ae.dt.deliveryorder.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.dto.BoLDTO;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */
public interface BolRepository extends JpaRepository<Bol, String>, JpaSpecificationExecutor<Bol>  {

	@Query("select b from Bol b where b.bolNumber = :bolNumber")
	List<Bol> findByBolnumber(@Param("bolNumber") String bolNumber);
	@Query("select b from Bol b where b.bolNumber = :bolNumber")
	Page<Bol> findByBolnumber(@Param("bolNumber") String bolNumber,Pageable pageable);
	@Query("select b from Bol b where b.bolNumber = :bolNumber")
	Bol findBolObjByBolnumber(@Param("bolNumber") String bolNumber);
	
	@Query("select b from Bol b where b.bolId = :bolId")
	List<Bol> findById(@Param("bolId")Long bolId);
	
	
	List<Bol> findByCreatedDateBetween(Date fromDate,Date toDate);
	List<Bol> findByCreatedDateAfterAndCreatedDateBefore(Date fromDate, Date toDate);
	//List<Bol> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(Date parse, Date parse2);
	
	@Query("select bol from Bol bol where trunc(bol.createdDate)>= :fromDate and trunc(bol.createdDate)<= :toDate and bol.createdBy = :createdBy")
	List<Bol> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("createdBy") String createdBy);
	//@Query("select bol from Bol bol where trunc(bol.createdDate)>= :fromDate and trunc(bol.createdDate)<= :toDate and ")
	@Query("select bol from Bol bol where trunc(bol.createdDate)>= :fromDate and trunc(bol.createdDate)<= :toDate and bol.bolId in (select doAuthRequest.bol.bolId from DoAuthRequest doAuthRequest where doAuthRequest.reqPartyName like CONCAT(:createdBy,'-%'))")
	List<Bol> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualByImporterCode(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("createdBy") String createdBy);
	
	@Query("select bol from Bol bol where trunc(bol.createdDate)>= :fromDate and trunc(bol.createdDate)<= :toDate and bol.createdBy = :createdBy and bol.status = :status")
	List<Bol> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndStatus(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate,@Param("status")String status, @Param("createdBy") String createdBy);
	
	@Query("select bol from Bol bol where trunc(bol.createdDate)>= :fromDate and bol.status = :status and trunc(bol.createdDate)<= :toDate and bol.bolId in (select doAuthRequest.bol.bolId from DoAuthRequest doAuthRequest where doAuthRequest.reqPartyName like CONCAT(:createdBy,'-%'))")
	List<Bol> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualByImporterCodeAndStatus(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate,@Param("status")String status, @Param("createdBy") String createdBy);
	
	
	List<Bol> findByBolNumberAndBolInvoicesInvoiceTypeInvoiceTypeId(String bolNo, Long invType);
	Bol findBolByBolId(Long bolId);
	Bol findBolByBolNumber(String bolNumber);

	

}

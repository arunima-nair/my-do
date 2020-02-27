package ae.dt.deliveryorder.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.CodeMaster;

public interface CodeMasterRepository extends CrudRepository<CodeMaster, Long>, JpaSpecificationExecutor<CodeMaster>{

	CodeMaster findByCodeMasterValue(String code);
}

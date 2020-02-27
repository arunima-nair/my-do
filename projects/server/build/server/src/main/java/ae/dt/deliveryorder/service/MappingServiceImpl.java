package ae.dt.deliveryorder.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.Credit;
import ae.dt.deliveryorder.domain.DeliveryOrder;
import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.Document;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.domain.Rosoom;
import ae.dt.deliveryorder.dto.ApprovalLogDTO;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.CollectionDTO;
import ae.dt.deliveryorder.dto.CreditDTO;
import ae.dt.deliveryorder.dto.DeliveryOrderDTO;
import ae.dt.deliveryorder.dto.DoAuthDocDTO;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.DocumentDTO;
import ae.dt.deliveryorder.dto.InvoiceTypeDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
import ae.dt.deliveryorder.dto.PaymentOfProofDTO;
import ae.dt.deliveryorder.dto.RosoomDTO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service("mappingService")
public class MappingServiceImpl implements MappingService{

	@Override
	public List<BoLDTO> bolDomaintoDTO(List<Bol> listBol) {
		
		List<BoLDTO> bolDtoList = new ArrayList<BoLDTO>();
		List<DoAuthRequestDTO> authRequestDTOsList = new ArrayList<DoAuthRequestDTO>();
		Set<DoAuthDocDTO> authDocDTOList = new HashSet<DoAuthDocDTO>();
		Set<BolInvoiceDTO> bolInvoiceDTOList = new HashSet<BolInvoiceDTO>();
		Set<ApprovalLogDTO> approveLogDTOList = new HashSet<ApprovalLogDTO>();
		Set<PaymentLogDTO> paymentLogDTOList = new HashSet<PaymentLogDTO>();
		Set<Credit> creditsList = new HashSet<Credit>();
		Set<PaymentOfProofDTO> paymentOfProofDTOList = new HashSet<PaymentOfProofDTO>();
		Set<Rosoom> rosoomList = new HashSet<Rosoom>();
		
		//SET BOL DTO
		if(listBol != null) {
			BoLDTO boLDTO = new BoLDTO();
			Bol bol = listBol.get(0);
			boLDTO.setBolNumber(bol.getBolNumber());
			boLDTO.setEncBolNumber(EncryptionUtil.encrypt(bol.getBolNumber()));
			boLDTO.setBolType(bol.getBolType());
			boLDTO.setStatus(bol.getStatus());
			boLDTO.setCreatedDate(bol.getCreatedDate());
			InvoiceTypeDTO invoiceTypeDTO = new InvoiceTypeDTO();
			Set<CollectionDTO> collectionDTOList = new HashSet<CollectionDTO>();
			
			//Set the Static Mapping 
			if(bol.getDoAuthRequests() != null) {
				for(DoAuthRequest doAuthRequestEntity :bol.getDoAuthRequests()) {
					DoAuthRequestDTO doAuthRequestDTO = new DoAuthRequestDTO();
					doAuthRequestDTO.setDoAuthRequestIdEncr(EncryptionUtil.encrypt(String.valueOf(doAuthRequestEntity.getDoAuthRequestId())));
					doAuthRequestDTO.setReqContactPerson(doAuthRequestEntity.getReqContactPerson());
					doAuthRequestDTO.setReqEmail(doAuthRequestEntity.getReqEmail());
					doAuthRequestDTO.setReqContactNumber(doAuthRequestEntity.getReqContactNumber());
					doAuthRequestDTO.setReqPartyName(doAuthRequestEntity.getReqPartyName());
					
					doAuthRequestDTO.setBolContactPerson(doAuthRequestEntity.getBolContactPerson());
					doAuthRequestDTO.setBolEmail(doAuthRequestEntity.getBolEmail());
					doAuthRequestDTO.setBolContactNumber(doAuthRequestEntity.getBolContactNumber());
					doAuthRequestDTO.setBolPartyName(doAuthRequestEntity.getBolPartyName());
					
					doAuthRequestDTO.setDoContactPerson(doAuthRequestEntity.getDoContactPerson());
					doAuthRequestDTO.setDoEmail(doAuthRequestEntity.getDoEmail());
					doAuthRequestDTO.setDoContactNumber(doAuthRequestEntity.getDoContactNumber());
					doAuthRequestDTO.setDoPartyName(doAuthRequestEntity.getDoPartyName());
					doAuthRequestDTO.setCreatedDate(doAuthRequestEntity.getCreatedDate());
					doAuthRequestDTO.setStatus(doAuthRequestEntity.getStatus());
					
					//SET DO_AUTH_Documents DTO
					if(doAuthRequestEntity.getDoAuthDocs() != null) {
						for (DoAuthDoc doauAuthDoc : doAuthRequestEntity.getDoAuthDocs()) {
							DoAuthDocDTO authDocoumentDto = new DoAuthDocDTO();
							DocumentDTO documentDTO = new DocumentDTO();
							Document document = doauAuthDoc.getDocumentBean();
							authDocoumentDto.setEncrDocumentPath(EncryptionUtil.encrypt(doauAuthDoc.getDocumentPath()));
							//authDocoumentDto.setDoAuthDocsId(doauAuthDoc.getDoAuthDocsId());
							authDocoumentDto.setEncrDoAuthDocsId(EncryptionUtil.encrypt(String.valueOf(doauAuthDoc.getDoAuthDocsId())));
							//documentDTO.setDocumentId(document.getDocumentId());
							documentDTO.setDocumentValue(document.getDocumentValue());
							authDocoumentDto.setDocumentBean(documentDTO);
							//authDocoumentDto.setDocumentBean(document);
							authDocDTOList.add(authDocoumentDto);	
						}
					doAuthRequestDTO.setDoAuthDocs(authDocDTOList);	
					}
					authRequestDTOsList.add(doAuthRequestDTO);
				}

			}
			
			//SET BOL_DETAILS DTO
			if(bol.getBolDetails() != null) {
				Set<BoLDetailDTO> boLDetailDTOList = new HashSet<BoLDetailDTO>();
				for (BolDetail bolDet : bol.getBolDetails()) {
					BoLDetailDTO boLDetailDTO = new BoLDetailDTO();
					boLDetailDTO.setConsigneeName(bolDet.getConsigneeName());
					boLDetailDTO.setVesselName(bolDet.getVesselName());
					boLDetailDTO.setVesselEta(bolDet.getVesselEta());
					boLDetailDTO.setVesselAta(bolDet.getVesselAta());
					boLDetailDTO.setImporterCode(bolDet.getImporterCode());
					boLDetailDTO.setShippingAgentCode(bolDet.getShippingAgentCode());
					boLDetailDTO.setShippingAgentName(bolDet.getShippingAgentName());
					boLDetailDTO.setVoyageNumber(bolDet.getVoyageNumber());
					boLDetailDTO.setContainerCount(bolDet.getContainerCount());
					boLDetailDTOList.add(boLDetailDTO);
				}
				boLDTO.setBolDetails(boLDetailDTOList);
			}				
			//SET BOL_INVOICE DTO
			if(bol.getBolInvoices() != null) {
				 Set<BolInvoice> bolInvoiceList = bol.getBolInvoices();
				 for (BolInvoice bolInvoice : bolInvoiceList) {
					 BolInvoiceDTO bolInvoiceDTO = new BolInvoiceDTO();
					 bolInvoiceDTO.setInvoiceNumber(bolInvoice.getInvoiceNumber());
					 bolInvoiceDTO.setEncrInvoiceNumber(EncryptionUtil.encrypt(bolInvoice.getInvoiceNumber()));
					 bolInvoiceDTO.setInvoiceValue(bolInvoice.getInvoiceValue());
					 bolInvoiceDTO.setCurrency(bolInvoice.getCurrency());
					 bolInvoiceDTO.setStatus(bolInvoice.getStatus());
					 bolInvoiceDTO.setCreatedBy(bolInvoice.getCreatedBy());
					 bolInvoiceDTO.setCreatedDate(bolInvoice.getCreatedDate());
					 bolInvoiceDTO.setModifiedBy(bolInvoice.getModifiedBy());
					 bolInvoiceDTO.setModifiedDate(bolInvoice.getModifiedDate());
					 
					 if(bolInvoice.getInvoiceType() != null) {
						 InvoiceType invoiceType = bolInvoice.getInvoiceType();
						 invoiceTypeDTO.setInvoiceTypeId(invoiceType.getInvoiceTypeId());
						 invoiceTypeDTO.setInvoiceTypeName(invoiceType.getInvoiceTypeName());
						 bolInvoiceDTO.setInvoiceType(invoiceTypeDTO);
					 }
					 if(bolInvoice.getCollections() != null) {
						 Set<CollectionDTO> collectionDTOSet = new HashSet<CollectionDTO>();
						 for (Collection collection : bolInvoice.getCollections()) {
							 CollectionDTO collec = new CollectionDTO();
							 collec.setCollectionType(collection.getCollectionType());
							 collec.setAmount(collection.getAmount());
							 collec.setInvoiceNumber(collection.getInvoiceNumber());
							 collec.setStatus(collection.getStatus());		
							 
							 //Credit
							 if(collection.getCredits()!= null) {
								 for (Credit credit : collection.getCredits()) {
									// CreditDTO creditDTO = new CreditDTO();
									 
								 } 
							 }
							 //PaymentOfProof
							 if(collection.getPaymentOfProofs()!= null) {
								 for (PaymentOfProof poProof : collection.getPaymentOfProofs()) {
									 PaymentOfProofDTO paymentOfProofDTO = new PaymentOfProofDTO();
									 paymentOfProofDTO.setPaymentOfProofId(poProof.getPaymentOfProofId());
									 paymentOfProofDTOList.add(paymentOfProofDTO);
								 }
								 collec.setPaymentOfProofs(paymentOfProofDTOList);
							 }
							 //Rosoom
							 if(collection.getRosooms()!= null) {
								 RosoomDTO rosoomDTO = new RosoomDTO();
								 
							 }
							 collectionDTOSet.add(collec);
						}
						 bolInvoiceDTO.setCollections(collectionDTOSet);
					 }
					 bolInvoiceDTOList.add(bolInvoiceDTO);
					 
				}
				 boLDTO.setBolInvoices(bolInvoiceDTOList);
			}
			bolDtoList.add(boLDTO);
		}
		return bolDtoList;
	}

	@Override
	public Set<BolInvoiceDTO> bolInvoiceDTOSet(Set<BolInvoice> bolInvoiceList) {
		Set<BolInvoiceDTO> bolInvDTOSet = new HashSet<BolInvoiceDTO>();
		if(bolInvoiceList != null) {
			 for (BolInvoice bolInvoice : bolInvoiceList) {
				 BolInvoiceDTO bolInvoiceDTO = new BolInvoiceDTO();
				 bolInvoiceDTO.setInvoiceNumber(bolInvoice.getInvoiceNumber());
				 bolInvoiceDTO.setEncrInvoiceNumber(EncryptionUtil.encrypt(bolInvoice.getInvoiceNumber()));
				 bolInvoiceDTO.setInvoiceValue(bolInvoice.getInvoiceValue());
				 bolInvoiceDTO.setCurrency(bolInvoice.getCurrency());
				 bolInvoiceDTO.setStatus(bolInvoice.getStatus());
				 bolInvoiceDTO.setInvoiceValidityDate(bolInvoice.getInvoiceValidityDate());
				 bolInvoiceDTO.setInvoiceDate(bolInvoice.getInvoiceDate());
				 if(null != bolInvoice.getPath()) {
					 bolInvoiceDTO.setPath(EncryptionUtil.encrypt(bolInvoice.getPath()));
				 }
				 bolInvoiceDTO.setCreatedBy(bolInvoice.getCreatedBy());
				 bolInvoiceDTO.setCreatedDate(bolInvoice.getCreatedDate());
				 bolInvoiceDTO.setModifiedBy(bolInvoice.getModifiedBy());
				 bolInvoiceDTO.setModifiedDate(bolInvoice.getModifiedDate());
				 
				 if(bolInvoice.getInvoiceType() != null) {
					 InvoiceTypeDTO invoiceTypeDTO = new InvoiceTypeDTO();
					 InvoiceType invoiceType = bolInvoice.getInvoiceType();
					 invoiceTypeDTO.setInvoiceTypeId(invoiceType.getInvoiceTypeId());
					 invoiceTypeDTO.setInvoiceTypeName(invoiceType.getInvoiceTypeName());
					 bolInvoiceDTO.setInvoiceType(invoiceTypeDTO);
				 }
				 if(bolInvoice.getCollections() != null) {
					 Set<CollectionDTO> collectionDTOSet = new HashSet<CollectionDTO>();
					 for (Collection collection : bolInvoice.getCollections()) {
						 CollectionDTO collec = new CollectionDTO();
						 collec.setCollectionType(collection.getCollectionType());
						 collec.setAmount(collection.getAmount());
						 collec.setInvoiceNumber(collection.getInvoiceNumber());
						 collec.setStatus(collection.getStatus());		
						 collectionDTOSet.add(collec);
						//Credit
						 if(collection.getCredits()!= null) {
							 for (Credit credit : collection.getCredits()) {
								// CreditDTO creditDTO = new CreditDTO();
								 
							 } 
						 }
						 //PaymentOfProof
						 if(collection.getPaymentOfProofs()!= null) {
							 Set<PaymentOfProofDTO> paymentOfProofDTOList = new HashSet<PaymentOfProofDTO>();
							 for (PaymentOfProof poProof : collection.getPaymentOfProofs()) {
								 PaymentOfProofDTO paymentOfProofDTO = new PaymentOfProofDTO();
								 paymentOfProofDTO.setEncrPaymentOfProofId(EncryptionUtil.encrypt(poProof.getPaymentOfProofId().toString()));
								 paymentOfProofDTO.setEncrReference(EncryptionUtil.encrypt(poProof.getReference()));
								 paymentOfProofDTOList.add(paymentOfProofDTO);
							 }
							 collec.setPaymentOfProofs(paymentOfProofDTOList);
						 }
						 //Rosoom
						 if(collection.getRosooms()!= null) {
							 RosoomDTO rosoomDTO = new RosoomDTO();
							 
						 }
					}
					 bolInvoiceDTO.setCollections(collectionDTOSet);
				 }
				 bolInvDTOSet.add(bolInvoiceDTO);
			}
		}
		return bolInvDTOSet;
	}

	@Override
	public InvoiceTypeDTO invoiceTypeDomaintoDTO(InvoiceType invoiceType) {
		InvoiceTypeDTO invoiceTypeDTO = new InvoiceTypeDTO();
		if(invoiceType!=null) {
			invoiceTypeDTO.setInvoiceTypeId(invoiceType.getInvoiceTypeId());
			invoiceTypeDTO.setInvoiceTypeName(invoiceType.getInvoiceTypeName());
		}
		return invoiceTypeDTO;
	}

	@Override
	public DeliveryOrderDTO deliveryOrderDomaintoDTO(DeliveryOrder deliveryOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<DoAuthDocDTO> authDocDomaintoDTOset(Set<DoAuthDoc> doAuthDocs) {
		// TODO Auto-generated method stub
		return null;
	}

}

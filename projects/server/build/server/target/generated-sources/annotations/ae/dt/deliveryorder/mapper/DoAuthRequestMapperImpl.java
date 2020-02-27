package ae.dt.deliveryorder.mapper;

import ae.dt.common.dto.ViewDTO;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.CollectionDTO;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:09+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class DoAuthRequestMapperImpl implements DoAuthRequestMapper {

    @Autowired
    private DeliveryOderMapper deliveryOderMapper;
    @Autowired
    private DoAuthDocsMapper doAuthDocsMapper;
    @Autowired
    private PaymentLogMapper paymentLogMapper;
    @Autowired
    private CollectionsMapper collectionsMapper;
    @Autowired
    private ApprovalLogMapper approvalLogMapper;

    @Override
    public DoAuthRequest dTOtoDoman(DoAuthRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DoAuthRequest doAuthRequest = new DoAuthRequest();

        doAuthRequest.setDoAuthRequestId( getDecryptedId( dto.getDoAuthRequestIdEncr() ) );
        doAuthRequest.setCreatedBy( dto.getCreatedBy() );
        doAuthRequest.setCreatedDate( dto.getCreatedDate() );
        doAuthRequest.setModifiedBy( dto.getModifiedBy() );
        doAuthRequest.setModifiedDate( dto.getModifiedDate() );
        doAuthRequest.setVersion( dto.getVersion() );
        doAuthRequest.setBolContactNumber( dto.getBolContactNumber() );
        doAuthRequest.setBolContactPerson( dto.getBolContactPerson() );
        doAuthRequest.setBolEmail( dto.getBolEmail() );
        doAuthRequest.setBolPartyName( dto.getBolPartyName() );
        doAuthRequest.setDoContactNumber( dto.getDoContactNumber() );
        doAuthRequest.setDoContactPerson( dto.getDoContactPerson() );
        doAuthRequest.setDoEmail( dto.getDoEmail() );
        doAuthRequest.setDoPartyName( dto.getDoPartyName() );
        doAuthRequest.setReqContactNumber( dto.getReqContactNumber() );
        doAuthRequest.setReqContactPerson( dto.getReqContactPerson() );
        doAuthRequest.setReqEmail( dto.getReqEmail() );
        doAuthRequest.setReqPartyName( dto.getReqPartyName() );
        doAuthRequest.setStatus( dto.getStatus() );
        doAuthRequest.setDoRefNo( dto.getDoRefNo() );
        doAuthRequest.setApprovedViewed( dto.getApprovedViewed() );
        doAuthRequest.setViewedBy( dto.getViewedBy() );
        doAuthRequest.setViewedDate( dto.getViewedDate() );
        doAuthRequest.setInitiatorId( dto.getInitiatorId() );
        doAuthRequest.setInitiatorCode( dto.getInitiatorCode() );
        doAuthRequest.setInitiatedBy( dto.getInitiatedBy() );
        doAuthRequest.setInitiatorType( dto.getInitiatorType() );
        doAuthRequest.setDoPartyCode( dto.getDoPartyCode() );
        doAuthRequest.setDoPartyType( dto.getDoPartyType() );
        doAuthRequest.setBlPartyCode( dto.getBlPartyCode() );
        doAuthRequest.setBlPartyType( dto.getBlPartyType() );
        doAuthRequest.setIsReturned( dto.getIsReturned() );
        doAuthRequest.setDeliveryOrder( deliveryOderMapper.dTOtoDoman( dto.getDeliveryOrder() ) );
        doAuthRequest.setDoAuthDocs( doAuthDocsMapper.dtotoDomainset( dto.getDoAuthDocs() ) );
        doAuthRequest.setPaymentLogs( paymentLogMapper.paylogDTOtoDomanset( dto.getPaymentLogs() ) );
        doAuthRequest.setCollections( collectionDTOSetToCollectionSet( dto.getCollections() ) );
        doAuthRequest.setApprovalLog( approvalLogMapper.dTOtoDomanset( dto.getApprovalLog() ) );

        return doAuthRequest;
    }

    @Override
    public DoAuthRequestDTO domaintoDTO(DoAuthRequest destination) {
        if ( destination == null ) {
            return null;
        }

        DoAuthRequestDTO doAuthRequestDTO = new DoAuthRequestDTO();

        doAuthRequestDTO.setDoAuthRequestIdEncr( getEncryptedId( destination.getDoAuthRequestId() ) );
        doAuthRequestDTO.setDoAuthRequestId( destination.getDoAuthRequestId() );
        doAuthRequestDTO.setBolContactNumber( destination.getBolContactNumber() );
        doAuthRequestDTO.setBolContactPerson( destination.getBolContactPerson() );
        doAuthRequestDTO.setBolEmail( destination.getBolEmail() );
        doAuthRequestDTO.setBolPartyName( destination.getBolPartyName() );
        doAuthRequestDTO.setDoContactNumber( destination.getDoContactNumber() );
        doAuthRequestDTO.setDoContactPerson( destination.getDoContactPerson() );
        doAuthRequestDTO.setDoEmail( destination.getDoEmail() );
        doAuthRequestDTO.setDoPartyName( destination.getDoPartyName() );
        doAuthRequestDTO.setReqContactNumber( destination.getReqContactNumber() );
        doAuthRequestDTO.setReqContactPerson( destination.getReqContactPerson() );
        doAuthRequestDTO.setReqEmail( destination.getReqEmail() );
        doAuthRequestDTO.setReqPartyName( destination.getReqPartyName() );
        doAuthRequestDTO.setStatus( destination.getStatus() );
        doAuthRequestDTO.setDoRefNo( destination.getDoRefNo() );
        doAuthRequestDTO.setDoAuthDocs( doAuthDocsMapper.domaintoDTOset( destination.getDoAuthDocs() ) );
        doAuthRequestDTO.setDeliveryOrder( deliveryOderMapper.domaintoDTO( destination.getDeliveryOrder() ) );
        doAuthRequestDTO.setPaymentLogs( paymentLogMapper.paylogDomaintoDTOset( destination.getPaymentLogs() ) );
        doAuthRequestDTO.setCollections( collectionSetToCollectionDTOSet( destination.getCollections() ) );
        doAuthRequestDTO.setApprovalLog( approvalLogMapper.domaintoDTOset( destination.getApprovalLog() ) );
        doAuthRequestDTO.setCreatedBy( destination.getCreatedBy() );
        doAuthRequestDTO.setCreatedDate( destination.getCreatedDate() );
        doAuthRequestDTO.setModifiedBy( destination.getModifiedBy() );
        doAuthRequestDTO.setModifiedDate( destination.getModifiedDate() );
        doAuthRequestDTO.setVersion( (int) destination.getVersion() );
        doAuthRequestDTO.setApprovedViewed( destination.getApprovedViewed() );
        doAuthRequestDTO.setViewedBy( destination.getViewedBy() );
        doAuthRequestDTO.setViewedDate( destination.getViewedDate() );
        doAuthRequestDTO.setInitiatorId( destination.getInitiatorId() );
        doAuthRequestDTO.setInitiatorCode( destination.getInitiatorCode() );
        doAuthRequestDTO.setInitiatorType( destination.getInitiatorType() );
        doAuthRequestDTO.setInitiatedBy( destination.getInitiatedBy() );
        doAuthRequestDTO.setDoPartyCode( destination.getDoPartyCode() );
        doAuthRequestDTO.setDoPartyType( destination.getDoPartyType() );
        doAuthRequestDTO.setBlPartyCode( destination.getBlPartyCode() );
        doAuthRequestDTO.setBlPartyType( destination.getBlPartyType() );
        doAuthRequestDTO.setIsReturned( destination.getIsReturned() );

        return doAuthRequestDTO;
    }

    @Override
    public List<DoAuthRequestDTO> domainlisttoDTO(Page<DoAuthRequest> pagelist) {
        if ( pagelist == null ) {
            return null;
        }

        List<DoAuthRequestDTO> list = new ArrayList<DoAuthRequestDTO>();
        for ( DoAuthRequest doAuthRequest : pagelist ) {
            list.add( domaintoDTO( doAuthRequest ) );
        }

        return list;
    }

    @Override
    public List<ViewDTO> doAuthRequestDomaintoViewDTO(Page<DoAuthRequest> pagelist) {
        if ( pagelist == null ) {
            return null;
        }

        List<ViewDTO> list = new ArrayList<ViewDTO>();
        for ( DoAuthRequest doAuthRequest : pagelist ) {
            list.add( doAuthRequestToViewDTO( doAuthRequest ) );
        }

        return list;
    }

    @Override
    public List<DoAuthRequestDTO> domainlisttoDTOlist(List<DoAuthRequest> domainlist) {
        if ( domainlist == null ) {
            return null;
        }

        List<DoAuthRequestDTO> list = new ArrayList<DoAuthRequestDTO>( domainlist.size() );
        for ( DoAuthRequest doAuthRequest : domainlist ) {
            list.add( domaintoDTO( doAuthRequest ) );
        }

        return list;
    }

    @Override
    public List<DoAuthRequestDTO> dtolisttoDomainlist(List<DoAuthRequest> doAuthRequestList) {
        if ( doAuthRequestList == null ) {
            return null;
        }

        List<DoAuthRequestDTO> list = new ArrayList<DoAuthRequestDTO>( doAuthRequestList.size() );
        for ( DoAuthRequest doAuthRequest : doAuthRequestList ) {
            list.add( domaintoDTO( doAuthRequest ) );
        }

        return list;
    }

    protected Set<Collection> collectionDTOSetToCollectionSet(Set<CollectionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Collection> set1 = new HashSet<Collection>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CollectionDTO collectionDTO : set ) {
            set1.add( collectionsMapper.collectiondTOtoDoman( collectionDTO ) );
        }

        return set1;
    }

    protected Set<CollectionDTO> collectionSetToCollectionDTOSet(Set<Collection> set) {
        if ( set == null ) {
            return null;
        }

        Set<CollectionDTO> set1 = new HashSet<CollectionDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Collection collection : set ) {
            set1.add( collectionsMapper.collectiondomaintoDTO( collection ) );
        }

        return set1;
    }

    protected ViewDTO doAuthRequestToViewDTO(DoAuthRequest doAuthRequest) {
        if ( doAuthRequest == null ) {
            return null;
        }

        ViewDTO viewDTO = new ViewDTO();

        viewDTO.setCreatedBy( doAuthRequest.getCreatedBy() );
        viewDTO.setStatus( doAuthRequest.getStatus() );
        viewDTO.setDoAuthRequestId( doAuthRequest.getDoAuthRequestId() );
        viewDTO.setBolContactNumber( doAuthRequest.getBolContactNumber() );
        viewDTO.setBolContactPerson( doAuthRequest.getBolContactPerson() );
        viewDTO.setBolEmail( doAuthRequest.getBolEmail() );
        viewDTO.setBolPartyName( doAuthRequest.getBolPartyName() );
        viewDTO.setDoContactNumber( doAuthRequest.getDoContactNumber() );
        viewDTO.setDoContactPerson( doAuthRequest.getDoContactPerson() );
        viewDTO.setDoEmail( doAuthRequest.getDoEmail() );
        viewDTO.setDoPartyName( doAuthRequest.getDoPartyName() );
        viewDTO.setReqContactNumber( doAuthRequest.getReqContactNumber() );
        viewDTO.setReqContactPerson( doAuthRequest.getReqContactPerson() );
        viewDTO.setReqEmail( doAuthRequest.getReqEmail() );
        viewDTO.setReqPartyName( doAuthRequest.getReqPartyName() );
        viewDTO.setDoRefNo( doAuthRequest.getDoRefNo() );
        viewDTO.setBlPartyCode( doAuthRequest.getBlPartyCode() );
        viewDTO.setDoPartyCode( doAuthRequest.getDoPartyCode() );
        viewDTO.setDoPartyType( doAuthRequest.getDoPartyType() );
        viewDTO.setBlPartyType( doAuthRequest.getBlPartyType() );
        viewDTO.setIsReturned( doAuthRequest.getIsReturned() );
        viewDTO.setCreatedDate( doAuthRequest.getCreatedDate() );
        viewDTO.setIsValid( doAuthRequest.getIsValid() );
        viewDTO.setModifiedBy( doAuthRequest.getModifiedBy() );
        viewDTO.setModifiedDate( doAuthRequest.getModifiedDate() );
        viewDTO.setIsActive( doAuthRequest.getIsActive() );
        viewDTO.setVersion( (int) doAuthRequest.getVersion() );

        return viewDTO;
    }
}

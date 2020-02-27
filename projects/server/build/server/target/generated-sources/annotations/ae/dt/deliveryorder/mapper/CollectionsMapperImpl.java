package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.ApprovalLog;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolChannel;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.ChannelType;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.Credit;
import ae.dt.deliveryorder.domain.DeliveryOrder;
import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.Document;
import ae.dt.deliveryorder.domain.Initiator;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.domain.RejectionRemark;
import ae.dt.deliveryorder.domain.ReturnRemark;
import ae.dt.deliveryorder.domain.Rosoom;
import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;
import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.domain.ShippingAgentAttributes;
import ae.dt.deliveryorder.dto.ApprovalLogDTO;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import ae.dt.deliveryorder.dto.BolChannelDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.ChannelTypeDTO;
import ae.dt.deliveryorder.dto.CollectionDTO;
import ae.dt.deliveryorder.dto.CreditDTO;
import ae.dt.deliveryorder.dto.DeliveryOrderDTO;
import ae.dt.deliveryorder.dto.DoAuthDocDTO;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.DocumentDTO;
import ae.dt.deliveryorder.dto.InitiatorDTO;
import ae.dt.deliveryorder.dto.InvoiceTypeDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
import ae.dt.deliveryorder.dto.PaymentOfProofDTO;
import ae.dt.deliveryorder.dto.RejectionRemarksDTO;
import ae.dt.deliveryorder.dto.ReturnRemarksDTO;
import ae.dt.deliveryorder.dto.RosoomDTO;
import ae.dt.deliveryorder.dto.SAInitiatorInvoiceTypeDTO;
import ae.dt.deliveryorder.dto.ShippingAgentAttributesDTO;
import ae.dt.deliveryorder.dto.ShippingAgentDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:08+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class CollectionsMapperImpl implements CollectionsMapper {

    @Autowired
    private CreditMapper creditMapper;
    @Autowired
    private RosoomMapper rosoomMapper;
    @Autowired
    private PaymentOfProofMapper paymentOfProofMapper;

    @Override
    public Collection collectiondTOtoDoman(CollectionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Collection collection = new Collection();

        collection.setCreatedBy( dto.getCreatedBy() );
        collection.setCollectionId( dto.getCollectionId() );
        collection.setAmount( dto.getAmount() );
        collection.setCollectionType( dto.getCollectionType() );
        collection.setInvoiceNumber( dto.getInvoiceNumber() );
        collection.setReceiptNo( dto.getReceiptNo() );
        collection.setStatus( dto.getStatus() );
        collection.setTransactionId( dto.getTransactionId() );
        collection.setPayStatus( dto.getPayStatus() );
        collection.setDoAuthRequest( doAuthRequestDTOToDoAuthRequest( dto.getDoAuthRequest() ) );
        collection.setCredits( creditDTOSetToCreditSet( dto.getCredits() ) );
        collection.setPaymentOfProofs( paymentOfProofDTOSetToPaymentOfProofSet( dto.getPaymentOfProofs() ) );
        collection.setRosooms( rosoomDTOSetToRosoomSet( dto.getRosooms() ) );
        collection.setBolInvoice( dto.getBolInvoice() );

        return collection;
    }

    @Override
    public CollectionDTO collectiondomaintoDTO(Collection destination) {
        if ( destination == null ) {
            return null;
        }

        CollectionDTO collectionDTO = new CollectionDTO();

        collectionDTO.setCollectionId( destination.getCollectionId() );
        collectionDTO.setAmount( destination.getAmount() );
        collectionDTO.setCollectionType( destination.getCollectionType() );
        collectionDTO.setInvoiceNumber( destination.getInvoiceNumber() );
        collectionDTO.setReceiptNo( destination.getReceiptNo() );
        collectionDTO.setStatus( destination.getStatus() );
        collectionDTO.setTransactionId( destination.getTransactionId() );
        collectionDTO.setCredits( creditSetToCreditDTOSet( destination.getCredits() ) );
        collectionDTO.setPaymentOfProofs( paymentOfProofSetToPaymentOfProofDTOSet( destination.getPaymentOfProofs() ) );
        collectionDTO.setRosooms( rosoomSetToRosoomDTOSet( destination.getRosooms() ) );
        collectionDTO.setCreatedBy( destination.getCreatedBy() );
        collectionDTO.setPayStatus( destination.getPayStatus() );

        return collectionDTO;
    }

    protected DeliveryOrder deliveryOrderDTOToDeliveryOrder(DeliveryOrderDTO deliveryOrderDTO) {
        if ( deliveryOrderDTO == null ) {
            return null;
        }

        DeliveryOrder deliveryOrder = new DeliveryOrder();

        deliveryOrder.setCreatedBy( deliveryOrderDTO.getCreatedBy() );
        deliveryOrder.setDeliveryOrderId( deliveryOrderDTO.getDeliveryOrderId() );
        deliveryOrder.setDocumentPath( deliveryOrderDTO.getDocumentPath() );
        deliveryOrder.setDoAuthRequest( doAuthRequestDTOToDoAuthRequest( deliveryOrderDTO.getDoAuthRequest() ) );

        return deliveryOrder;
    }

    protected Document documentDTOToDocument(DocumentDTO documentDTO) {
        if ( documentDTO == null ) {
            return null;
        }

        Document document = new Document();

        document.setDocumentId( documentDTO.getDocumentId() );
        document.setDocumentValue( documentDTO.getDocumentValue() );

        return document;
    }

    protected DoAuthDoc doAuthDocDTOToDoAuthDoc(DoAuthDocDTO doAuthDocDTO) {
        if ( doAuthDocDTO == null ) {
            return null;
        }

        DoAuthDoc doAuthDoc = new DoAuthDoc();

        doAuthDoc.setCreatedBy( doAuthDocDTO.getCreatedBy() );
        doAuthDoc.setDoAuthDocsId( doAuthDocDTO.getDoAuthDocsId() );
        doAuthDoc.setDocumentPath( doAuthDocDTO.getDocumentPath() );
        doAuthDoc.setDocumentBean( documentDTOToDocument( doAuthDocDTO.getDocumentBean() ) );
        doAuthDoc.setDoAuthRequest( doAuthRequestDTOToDoAuthRequest( doAuthDocDTO.getDoAuthRequest() ) );

        return doAuthDoc;
    }

    protected Set<DoAuthDoc> doAuthDocDTOSetToDoAuthDocSet(Set<DoAuthDocDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<DoAuthDoc> set1 = new HashSet<DoAuthDoc>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DoAuthDocDTO doAuthDocDTO : set ) {
            set1.add( doAuthDocDTOToDoAuthDoc( doAuthDocDTO ) );
        }

        return set1;
    }

    protected BolDetail boLDetailDTOToBolDetail(BoLDetailDTO boLDetailDTO) {
        if ( boLDetailDTO == null ) {
            return null;
        }

        BolDetail bolDetail = new BolDetail();

        if ( boLDetailDTO.getIsActive() != null ) {
            bolDetail.setIsActive( boLDetailDTO.getIsActive() );
        }
        bolDetail.setCreatedBy( boLDetailDTO.getCreatedBy() );
        bolDetail.setCreatedDate( boLDetailDTO.getCreatedDate() );
        bolDetail.setIsValid( boLDetailDTO.getIsValid() );
        bolDetail.setModifiedBy( boLDetailDTO.getModifiedBy() );
        bolDetail.setModifiedDate( boLDetailDTO.getModifiedDate() );
        bolDetail.setBolDetailsId( boLDetailDTO.getBolDetailsId() );
        bolDetail.setChannelId( boLDetailDTO.getChannelId() );
        bolDetail.setImporterCode( boLDetailDTO.getImporterCode() );
        bolDetail.setShippingAgentCode( boLDetailDTO.getShippingAgentCode() );
        bolDetail.setShippingAgentName( boLDetailDTO.getShippingAgentName() );
        bolDetail.setVesselEta( boLDetailDTO.getVesselEta() );
        bolDetail.setVesselName( boLDetailDTO.getVesselName() );
        bolDetail.setVoyageNumber( boLDetailDTO.getVoyageNumber() );
        bolDetail.setContainerCount( boLDetailDTO.getContainerCount() );
        bolDetail.setConsigneeName( boLDetailDTO.getConsigneeName() );
        bolDetail.setVesselAta( boLDetailDTO.getVesselAta() );
        bolDetail.setBol( boLDTOToBol( boLDetailDTO.getBol() ) );

        return bolDetail;
    }

    protected Set<BolDetail> boLDetailDTOSetToBolDetailSet(Set<BoLDetailDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolDetail> set1 = new HashSet<BolDetail>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BoLDetailDTO boLDetailDTO : set ) {
            set1.add( boLDetailDTOToBolDetail( boLDetailDTO ) );
        }

        return set1;
    }

    protected ShippingAgentAttributes shippingAgentAttributesDTOToShippingAgentAttributes(ShippingAgentAttributesDTO shippingAgentAttributesDTO) {
        if ( shippingAgentAttributesDTO == null ) {
            return null;
        }

        ShippingAgentAttributes shippingAgentAttributes = new ShippingAgentAttributes();

        if ( shippingAgentAttributesDTO.getIsActive() != null ) {
            shippingAgentAttributes.setIsActive( shippingAgentAttributesDTO.getIsActive() );
        }
        shippingAgentAttributes.setCreatedDate( shippingAgentAttributesDTO.getCreatedDate() );
        shippingAgentAttributes.setIsValid( shippingAgentAttributesDTO.getIsValid() );
        shippingAgentAttributes.setModifiedBy( shippingAgentAttributesDTO.getModifiedBy() );
        shippingAgentAttributes.setModifiedDate( shippingAgentAttributesDTO.getModifiedDate() );
        shippingAgentAttributes.setShippingAgentAttributesId( shippingAgentAttributesDTO.getShippingAgentAttributesId() );
        shippingAgentAttributes.setAttributeName( shippingAgentAttributesDTO.getAttributeName() );
        shippingAgentAttributes.setAttributeValue( shippingAgentAttributesDTO.getAttributeValue() );
        shippingAgentAttributes.setAttributeParam( shippingAgentAttributesDTO.getAttributeParam() );
        shippingAgentAttributes.setShippingAgent( shippingAgentDTOToShippingAgent( shippingAgentAttributesDTO.getShippingAgent() ) );

        return shippingAgentAttributes;
    }

    protected Set<ShippingAgentAttributes> shippingAgentAttributesDTOSetToShippingAgentAttributesSet(Set<ShippingAgentAttributesDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<ShippingAgentAttributes> set1 = new HashSet<ShippingAgentAttributes>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ShippingAgentAttributesDTO shippingAgentAttributesDTO : set ) {
            set1.add( shippingAgentAttributesDTOToShippingAgentAttributes( shippingAgentAttributesDTO ) );
        }

        return set1;
    }

    protected Set<SAInitiatorInvoiceType> sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet(Set<SAInitiatorInvoiceTypeDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<SAInitiatorInvoiceType> set1 = new HashSet<SAInitiatorInvoiceType>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO : set ) {
            set1.add( sAInitiatorInvoiceTypeDTOToSAInitiatorInvoiceType( sAInitiatorInvoiceTypeDTO ) );
        }

        return set1;
    }

    protected ShippingAgent shippingAgentDTOToShippingAgent(ShippingAgentDTO shippingAgentDTO) {
        if ( shippingAgentDTO == null ) {
            return null;
        }

        ShippingAgent shippingAgent = new ShippingAgent();

        if ( shippingAgentDTO.getIsActive() != null ) {
            shippingAgent.setIsActive( shippingAgentDTO.getIsActive() );
        }
        shippingAgent.setCreatedDate( shippingAgentDTO.getCreatedDate() );
        shippingAgent.setIsValid( shippingAgentDTO.getIsValid() );
        shippingAgent.setModifiedBy( shippingAgentDTO.getModifiedBy() );
        shippingAgent.setModifiedDate( shippingAgentDTO.getModifiedDate() );
        shippingAgent.setShippingAgentId( shippingAgentDTO.getShippingAgentId() );
        shippingAgent.setShippingAgentCode( shippingAgentDTO.getShippingAgentCode() );
        shippingAgent.setShippingAgentName( shippingAgentDTO.getShippingAgentName() );
        shippingAgent.setShippingAgentAttributes( shippingAgentAttributesDTOSetToShippingAgentAttributesSet( shippingAgentDTO.getShippingAgentAttributes() ) );
        shippingAgent.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( shippingAgentDTO.getSaInitiatorInvoiceType() ) );

        return shippingAgent;
    }

    protected Initiator initiatorDTOToInitiator(InitiatorDTO initiatorDTO) {
        if ( initiatorDTO == null ) {
            return null;
        }

        Initiator initiator = new Initiator();

        if ( initiatorDTO.getIsActive() != null ) {
            initiator.setIsActive( initiatorDTO.getIsActive() );
        }
        initiator.setCreatedDate( initiatorDTO.getCreatedDate() );
        initiator.setIsValid( initiatorDTO.getIsValid() );
        initiator.setModifiedBy( initiatorDTO.getModifiedBy() );
        initiator.setModifiedDate( initiatorDTO.getModifiedDate() );
        initiator.setInitiatorId( initiatorDTO.getInitiatorId() );
        initiator.setInitiatorName( initiatorDTO.getInitiatorName() );
        initiator.setInitiatorType( initiatorDTO.getInitiatorType() );
        initiator.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( initiatorDTO.getSaInitiatorInvoiceType() ) );

        return initiator;
    }

    protected SAInitiatorInvoiceType sAInitiatorInvoiceTypeDTOToSAInitiatorInvoiceType(SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO) {
        if ( sAInitiatorInvoiceTypeDTO == null ) {
            return null;
        }

        SAInitiatorInvoiceType sAInitiatorInvoiceType = new SAInitiatorInvoiceType();

        if ( sAInitiatorInvoiceTypeDTO.getIsActive() != null ) {
            sAInitiatorInvoiceType.setIsActive( sAInitiatorInvoiceTypeDTO.getIsActive() );
        }
        sAInitiatorInvoiceType.setCreatedDate( sAInitiatorInvoiceTypeDTO.getCreatedDate() );
        sAInitiatorInvoiceType.setIsValid( sAInitiatorInvoiceTypeDTO.getIsValid() );
        sAInitiatorInvoiceType.setModifiedBy( sAInitiatorInvoiceTypeDTO.getModifiedBy() );
        sAInitiatorInvoiceType.setModifiedDate( sAInitiatorInvoiceTypeDTO.getModifiedDate() );
        sAInitiatorInvoiceType.setSaInitiatorInvoiceTypeId( sAInitiatorInvoiceTypeDTO.getSaInitiatorInvoiceTypeId() );
        sAInitiatorInvoiceType.setShippingAgent( shippingAgentDTOToShippingAgent( sAInitiatorInvoiceTypeDTO.getShippingAgent() ) );
        sAInitiatorInvoiceType.setInvoiceType( sAInitiatorInvoiceTypeDTO.getInvoiceType() );
        sAInitiatorInvoiceType.setInitiator( initiatorDTOToInitiator( sAInitiatorInvoiceTypeDTO.getInitiator() ) );

        return sAInitiatorInvoiceType;
    }

    protected Set<BolInvoice> bolInvoiceDTOSetToBolInvoiceSet(Set<BolInvoiceDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolInvoice> set1 = new HashSet<BolInvoice>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolInvoiceDTO bolInvoiceDTO : set ) {
            set1.add( bolInvoiceDTOToBolInvoice( bolInvoiceDTO ) );
        }

        return set1;
    }

    protected InvoiceType invoiceTypeDTOToInvoiceType(InvoiceTypeDTO invoiceTypeDTO) {
        if ( invoiceTypeDTO == null ) {
            return null;
        }

        InvoiceType invoiceType = new InvoiceType();

        if ( invoiceTypeDTO.getIsActive() != null ) {
            invoiceType.setIsActive( invoiceTypeDTO.getIsActive() );
        }
        invoiceType.setCreatedDate( invoiceTypeDTO.getCreatedDate() );
        invoiceType.setIsValid( invoiceTypeDTO.getIsValid() );
        invoiceType.setModifiedBy( invoiceTypeDTO.getModifiedBy() );
        invoiceType.setModifiedDate( invoiceTypeDTO.getModifiedDate() );
        invoiceType.setInvoiceTypeId( invoiceTypeDTO.getInvoiceTypeId() );
        invoiceType.setInvoiceTypeName( invoiceTypeDTO.getInvoiceTypeName() );
        invoiceType.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( invoiceTypeDTO.getSaInitiatorInvoiceType() ) );
        invoiceType.setBolInvoice( bolInvoiceDTOSetToBolInvoiceSet( invoiceTypeDTO.getBolInvoice() ) );

        return invoiceType;
    }

    protected PaymentLog paymentLogDTOToPaymentLog(PaymentLogDTO paymentLogDTO) {
        if ( paymentLogDTO == null ) {
            return null;
        }

        PaymentLog paymentLog = new PaymentLog();

        paymentLog.setPaymentLogId( paymentLogDTO.getPaymentLogId() );
        paymentLog.setAmount( paymentLogDTO.getAmount() );
        paymentLog.setStatus( paymentLogDTO.getStatus() );
        paymentLog.setTransactionId( paymentLogDTO.getTransactionId() );
        paymentLog.setPayStatus( paymentLogDTO.getPayStatus() );
        paymentLog.setDoAuthRequest( doAuthRequestDTOToDoAuthRequest( paymentLogDTO.getDoAuthRequest() ) );
        paymentLog.setCreatedBy( paymentLogDTO.getCreatedBy() );
        paymentLog.setPaidBy( paymentLogDTO.getPaidBy() );
        paymentLog.setInitiatorId( paymentLogDTO.getInitiatorId() );
        paymentLog.setInitiatedBy( paymentLogDTO.getInitiatedBy() );
        paymentLog.setBolInvoice( paymentLogDTO.getBolInvoice() );
        paymentLog.setShippingAgent( paymentLogDTO.getShippingAgent() );

        return paymentLog;
    }

    protected Set<PaymentLog> paymentLogDTOSetToPaymentLogSet(Set<PaymentLogDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentLog> set1 = new HashSet<PaymentLog>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentLogDTO paymentLogDTO : set ) {
            set1.add( paymentLogDTOToPaymentLog( paymentLogDTO ) );
        }

        return set1;
    }

    protected Set<Collection> collectionDTOSetToCollectionSet(Set<CollectionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Collection> set1 = new HashSet<Collection>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CollectionDTO collectionDTO : set ) {
            set1.add( collectiondTOtoDoman( collectionDTO ) );
        }

        return set1;
    }

    protected BolInvoice bolInvoiceDTOToBolInvoice(BolInvoiceDTO bolInvoiceDTO) {
        if ( bolInvoiceDTO == null ) {
            return null;
        }

        BolInvoice bolInvoice = new BolInvoice();

        if ( bolInvoiceDTO.getIsActive() != null ) {
            bolInvoice.setIsActive( bolInvoiceDTO.getIsActive() );
        }
        bolInvoice.setCreatedBy( bolInvoiceDTO.getCreatedBy() );
        bolInvoice.setCreatedDate( bolInvoiceDTO.getCreatedDate() );
        bolInvoice.setIsValid( bolInvoiceDTO.getIsValid() );
        bolInvoice.setModifiedBy( bolInvoiceDTO.getModifiedBy() );
        bolInvoice.setModifiedDate( bolInvoiceDTO.getModifiedDate() );
        bolInvoice.setBolInvoiceId( bolInvoiceDTO.getBolInvoiceId() );
        bolInvoice.setInvoiceDate( bolInvoiceDTO.getInvoiceDate() );
        bolInvoice.setInvoiceNumber( bolInvoiceDTO.getInvoiceNumber() );
        bolInvoice.setInvoiceValue( bolInvoiceDTO.getInvoiceValue() );
        bolInvoice.setPath( bolInvoiceDTO.getPath() );
        bolInvoice.setCurrency( bolInvoiceDTO.getCurrency() );
        bolInvoice.setInvoiceValidityDate( bolInvoiceDTO.getInvoiceValidityDate() );
        bolInvoice.setBol( boLDTOToBol( bolInvoiceDTO.getBol() ) );
        bolInvoice.setInvoiceType( invoiceTypeDTOToInvoiceType( bolInvoiceDTO.getInvoiceType() ) );
        bolInvoice.setPaymentLogs( paymentLogDTOSetToPaymentLogSet( bolInvoiceDTO.getPaymentLogs() ) );
        bolInvoice.setCollections( collectionDTOSetToCollectionSet( bolInvoiceDTO.getCollections() ) );
        bolInvoice.setStatus( bolInvoiceDTO.getStatus() );

        return bolInvoice;
    }

    protected Set<DoAuthRequest> doAuthRequestDTOSetToDoAuthRequestSet(Set<DoAuthRequestDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<DoAuthRequest> set1 = new HashSet<DoAuthRequest>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DoAuthRequestDTO doAuthRequestDTO : set ) {
            set1.add( doAuthRequestDTOToDoAuthRequest( doAuthRequestDTO ) );
        }

        return set1;
    }

    protected ChannelType channelTypeDTOToChannelType(ChannelTypeDTO channelTypeDTO) {
        if ( channelTypeDTO == null ) {
            return null;
        }

        ChannelType channelType = new ChannelType();

        channelType.setChannelTypeId( channelTypeDTO.getChannelTypeId() );
        channelType.setDescription( channelTypeDTO.getDescription() );
        channelType.setValue( channelTypeDTO.getValue() );

        return channelType;
    }

    protected BolChannel bolChannelDTOToBolChannel(BolChannelDTO bolChannelDTO) {
        if ( bolChannelDTO == null ) {
            return null;
        }

        BolChannel bolChannel = new BolChannel();

        if ( bolChannelDTO.getIsActive() != null ) {
            bolChannel.setIsActive( bolChannelDTO.getIsActive() );
        }
        bolChannel.setCreatedBy( bolChannelDTO.getCreatedBy() );
        bolChannel.setCreatedDate( bolChannelDTO.getCreatedDate() );
        bolChannel.setIsValid( bolChannelDTO.getIsValid() );
        bolChannel.setModifiedBy( bolChannelDTO.getModifiedBy() );
        bolChannel.setModifiedDate( bolChannelDTO.getModifiedDate() );
        bolChannel.setChannelId( bolChannelDTO.getChannelId() );
        bolChannel.setReference( bolChannelDTO.getReference() );
        bolChannel.setBol( boLDTOToBol( bolChannelDTO.getBol() ) );
        bolChannel.setChannelType( channelTypeDTOToChannelType( bolChannelDTO.getChannelType() ) );

        return bolChannel;
    }

    protected Set<BolChannel> bolChannelDTOSetToBolChannelSet(Set<BolChannelDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolChannel> set1 = new HashSet<BolChannel>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolChannelDTO bolChannelDTO : set ) {
            set1.add( bolChannelDTOToBolChannel( bolChannelDTO ) );
        }

        return set1;
    }

    protected Bol boLDTOToBol(BoLDTO boLDTO) {
        if ( boLDTO == null ) {
            return null;
        }

        Bol bol = new Bol();

        if ( boLDTO.getIsActive() != null ) {
            bol.setIsActive( boLDTO.getIsActive() );
        }
        bol.setCreatedBy( boLDTO.getCreatedBy() );
        bol.setCreatedDate( boLDTO.getCreatedDate() );
        bol.setIsValid( boLDTO.getIsValid() );
        bol.setModifiedBy( boLDTO.getModifiedBy() );
        bol.setModifiedDate( boLDTO.getModifiedDate() );
        if ( boLDTO.getVersion() != null ) {
            bol.setVersion( boLDTO.getVersion() );
        }
        bol.setBolId( boLDTO.getBolId() );
        bol.setBolNumber( boLDTO.getBolNumber() );
        bol.setBolType( boLDTO.getBolType() );
        bol.setStatus( boLDTO.getStatus() );
        bol.setBolGroupId( boLDTO.getBolGroupId() );
        bol.setBolDetails( boLDetailDTOSetToBolDetailSet( boLDTO.getBolDetails() ) );
        bol.setBolInvoices( bolInvoiceDTOSetToBolInvoiceSet( boLDTO.getBolInvoices() ) );
        bol.setDoAuthRequests( doAuthRequestDTOSetToDoAuthRequestSet( boLDTO.getDoAuthRequests() ) );
        bol.setBolChannels( bolChannelDTOSetToBolChannelSet( boLDTO.getBolChannels() ) );

        return bol;
    }

    protected RejectionRemark rejectionRemarksDTOToRejectionRemark(RejectionRemarksDTO rejectionRemarksDTO) {
        if ( rejectionRemarksDTO == null ) {
            return null;
        }

        RejectionRemark rejectionRemark = new RejectionRemark();

        rejectionRemark.setRejectionRemarksId( rejectionRemarksDTO.getRejectionRemarksId() );
        rejectionRemark.setRemarks( rejectionRemarksDTO.getRemarks() );

        return rejectionRemark;
    }

    protected ReturnRemark returnRemarksDTOToReturnRemark(ReturnRemarksDTO returnRemarksDTO) {
        if ( returnRemarksDTO == null ) {
            return null;
        }

        ReturnRemark returnRemark = new ReturnRemark();

        returnRemark.setReturnRemarksId( returnRemarksDTO.getReturnRemarksId() );
        returnRemark.setRemarks( returnRemarksDTO.getRemarks() );

        return returnRemark;
    }

    protected ApprovalLog approvalLogDTOToApprovalLog(ApprovalLogDTO approvalLogDTO) {
        if ( approvalLogDTO == null ) {
            return null;
        }

        ApprovalLog approvalLog = new ApprovalLog();

        approvalLog.setApprovalLogId( approvalLogDTO.getApprovalLogId() );
        approvalLog.setCreatedBy( approvalLogDTO.getCreatedBy() );
        approvalLog.setCreatedDate( approvalLogDTO.getCreatedDate() );
        approvalLog.setStatus( approvalLogDTO.getStatus() );
        approvalLog.setRejectionRemark( rejectionRemarksDTOToRejectionRemark( approvalLogDTO.getRejectionRemark() ) );
        approvalLog.setReturnRemark( returnRemarksDTOToReturnRemark( approvalLogDTO.getReturnRemark() ) );
        approvalLog.setComments( approvalLogDTO.getComments() );
        approvalLog.setDoAuthRequest( doAuthRequestDTOToDoAuthRequest( approvalLogDTO.getDoAuthRequest() ) );

        return approvalLog;
    }

    protected Set<ApprovalLog> approvalLogDTOSetToApprovalLogSet(Set<ApprovalLogDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<ApprovalLog> set1 = new HashSet<ApprovalLog>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ApprovalLogDTO approvalLogDTO : set ) {
            set1.add( approvalLogDTOToApprovalLog( approvalLogDTO ) );
        }

        return set1;
    }

    protected DoAuthRequest doAuthRequestDTOToDoAuthRequest(DoAuthRequestDTO doAuthRequestDTO) {
        if ( doAuthRequestDTO == null ) {
            return null;
        }

        DoAuthRequest doAuthRequest = new DoAuthRequest();

        doAuthRequest.setCreatedBy( doAuthRequestDTO.getCreatedBy() );
        doAuthRequest.setCreatedDate( doAuthRequestDTO.getCreatedDate() );
        doAuthRequest.setModifiedBy( doAuthRequestDTO.getModifiedBy() );
        doAuthRequest.setModifiedDate( doAuthRequestDTO.getModifiedDate() );
        doAuthRequest.setVersion( doAuthRequestDTO.getVersion() );
        doAuthRequest.setDoAuthRequestId( doAuthRequestDTO.getDoAuthRequestId() );
        doAuthRequest.setBolContactNumber( doAuthRequestDTO.getBolContactNumber() );
        doAuthRequest.setBolContactPerson( doAuthRequestDTO.getBolContactPerson() );
        doAuthRequest.setBolEmail( doAuthRequestDTO.getBolEmail() );
        doAuthRequest.setBolPartyName( doAuthRequestDTO.getBolPartyName() );
        doAuthRequest.setDoContactNumber( doAuthRequestDTO.getDoContactNumber() );
        doAuthRequest.setDoContactPerson( doAuthRequestDTO.getDoContactPerson() );
        doAuthRequest.setDoEmail( doAuthRequestDTO.getDoEmail() );
        doAuthRequest.setDoPartyName( doAuthRequestDTO.getDoPartyName() );
        doAuthRequest.setReqContactNumber( doAuthRequestDTO.getReqContactNumber() );
        doAuthRequest.setReqContactPerson( doAuthRequestDTO.getReqContactPerson() );
        doAuthRequest.setReqEmail( doAuthRequestDTO.getReqEmail() );
        doAuthRequest.setReqPartyName( doAuthRequestDTO.getReqPartyName() );
        doAuthRequest.setStatus( doAuthRequestDTO.getStatus() );
        doAuthRequest.setDoRefNo( doAuthRequestDTO.getDoRefNo() );
        doAuthRequest.setApprovedViewed( doAuthRequestDTO.getApprovedViewed() );
        doAuthRequest.setViewedBy( doAuthRequestDTO.getViewedBy() );
        doAuthRequest.setViewedDate( doAuthRequestDTO.getViewedDate() );
        doAuthRequest.setInitiatorId( doAuthRequestDTO.getInitiatorId() );
        doAuthRequest.setInitiatorCode( doAuthRequestDTO.getInitiatorCode() );
        doAuthRequest.setInitiatedBy( doAuthRequestDTO.getInitiatedBy() );
        doAuthRequest.setInitiatorType( doAuthRequestDTO.getInitiatorType() );
        doAuthRequest.setDoPartyCode( doAuthRequestDTO.getDoPartyCode() );
        doAuthRequest.setDoPartyType( doAuthRequestDTO.getDoPartyType() );
        doAuthRequest.setBlPartyCode( doAuthRequestDTO.getBlPartyCode() );
        doAuthRequest.setBlPartyType( doAuthRequestDTO.getBlPartyType() );
        doAuthRequest.setIsReturned( doAuthRequestDTO.getIsReturned() );
        doAuthRequest.setDeliveryOrder( deliveryOrderDTOToDeliveryOrder( doAuthRequestDTO.getDeliveryOrder() ) );
        doAuthRequest.setDoAuthDocs( doAuthDocDTOSetToDoAuthDocSet( doAuthRequestDTO.getDoAuthDocs() ) );
        doAuthRequest.setBol( boLDTOToBol( doAuthRequestDTO.getBol() ) );
        doAuthRequest.setPaymentLogs( paymentLogDTOSetToPaymentLogSet( doAuthRequestDTO.getPaymentLogs() ) );
        doAuthRequest.setCollections( collectionDTOSetToCollectionSet( doAuthRequestDTO.getCollections() ) );
        doAuthRequest.setApprovalLog( approvalLogDTOSetToApprovalLogSet( doAuthRequestDTO.getApprovalLog() ) );

        return doAuthRequest;
    }

    protected Set<Credit> creditDTOSetToCreditSet(Set<CreditDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Credit> set1 = new HashSet<Credit>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CreditDTO creditDTO : set ) {
            set1.add( creditMapper.creditdTOtoDoman( creditDTO ) );
        }

        return set1;
    }

    protected Set<PaymentOfProof> paymentOfProofDTOSetToPaymentOfProofSet(Set<PaymentOfProofDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentOfProof> set1 = new HashSet<PaymentOfProof>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentOfProofDTO paymentOfProofDTO : set ) {
            set1.add( paymentOfProofMapper.paymentOfProofdTOtoDoman( paymentOfProofDTO ) );
        }

        return set1;
    }

    protected Set<Rosoom> rosoomDTOSetToRosoomSet(Set<RosoomDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Rosoom> set1 = new HashSet<Rosoom>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RosoomDTO rosoomDTO : set ) {
            set1.add( rosoomMapper.rosoomdTOtoDoman( rosoomDTO ) );
        }

        return set1;
    }

    protected Set<CreditDTO> creditSetToCreditDTOSet(Set<Credit> set) {
        if ( set == null ) {
            return null;
        }

        Set<CreditDTO> set1 = new HashSet<CreditDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Credit credit : set ) {
            set1.add( creditMapper.creditdomaintoDTO( credit ) );
        }

        return set1;
    }

    protected Set<PaymentOfProofDTO> paymentOfProofSetToPaymentOfProofDTOSet(Set<PaymentOfProof> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentOfProofDTO> set1 = new HashSet<PaymentOfProofDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentOfProof paymentOfProof : set ) {
            set1.add( paymentOfProofMapper.paymentOfProofdomaintoDTO( paymentOfProof ) );
        }

        return set1;
    }

    protected Set<RosoomDTO> rosoomSetToRosoomDTOSet(Set<Rosoom> set) {
        if ( set == null ) {
            return null;
        }

        Set<RosoomDTO> set1 = new HashSet<RosoomDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Rosoom rosoom : set ) {
            set1.add( rosoomMapper.rosoomdomaintoDTO( rosoom ) );
        }

        return set1;
    }
}

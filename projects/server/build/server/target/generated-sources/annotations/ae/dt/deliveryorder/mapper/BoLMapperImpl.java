package ae.dt.deliveryorder.mapper;

import ae.dt.common.dto.ViewDTO;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.domain.BolChannel;
import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.ChannelType;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import ae.dt.deliveryorder.dto.BolChannelDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.ChannelTypeDTO;
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
    date = "2020-01-29T19:43:07+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class BoLMapperImpl implements BoLMapper {

    @Autowired
    private BolDetailsMapper bolDetailsMapper;
    @Autowired
    private BolInvoiceMapper bolInvoiceMapper;
    @Autowired
    private DoAuthRequestMapper doAuthRequestMapper;

    @Override
    public Bol bolDTOtoDoman(BoLDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Bol bol = new Bol();

        bol.setBolId( getDecryptedBolId( dto.getBolIdStr() ) );
        bol.setBolNumber( getDecryptedBol( dto.getEncBolNumber() ) );
        if ( dto.getIsActive() != null ) {
            bol.setIsActive( dto.getIsActive() );
        }
        bol.setCreatedBy( dto.getCreatedBy() );
        bol.setCreatedDate( dto.getCreatedDate() );
        bol.setIsValid( dto.getIsValid() );
        bol.setModifiedBy( dto.getModifiedBy() );
        bol.setModifiedDate( dto.getModifiedDate() );
        if ( dto.getVersion() != null ) {
            bol.setVersion( dto.getVersion() );
        }
        bol.setBolType( dto.getBolType() );
        bol.setStatus( dto.getStatus() );
        bol.setBolGroupId( dto.getBolGroupId() );
        bol.setBolDetails( boLDetailDTOSetToBolDetailSet( dto.getBolDetails() ) );
        bol.setBolInvoices( bolInvoiceDTOSetToBolInvoiceSet( dto.getBolInvoices() ) );
        bol.setDoAuthRequests( doAuthRequestDTOSetToDoAuthRequestSet( dto.getDoAuthRequests() ) );
        bol.setBolChannels( bolChannelDTOSetToBolChannelSet( dto.getBolChannels() ) );

        return bol;
    }

    @Override
    public BoLDTO bolDomaintoDTO(Bol destination) {
        if ( destination == null ) {
            return null;
        }

        BoLDTO boLDTO = new BoLDTO();

        boLDTO.setEncBolNumber( getEncryptedBol( destination.getBolNumber() ) );
        boLDTO.setBolIdStr( getEncryptedBolId( destination.getBolId() ) );
        boLDTO.setBolNumber( destination.getBolNumber() );
        boLDTO.setBolType( destination.getBolType() );
        boLDTO.setBolChannels( bolChannelSetToBolChannelDTOSet( destination.getBolChannels() ) );
        boLDTO.setBolDetails( bolDetailSetToBoLDetailDTOSet( destination.getBolDetails() ) );
        boLDTO.setBolInvoices( bolDomaintoDTO( destination.getBolInvoices() ) );
        boLDTO.setDoAuthRequests( doAuthRequestSetToDoAuthRequestDTOSet( destination.getDoAuthRequests() ) );
        boLDTO.setCreatedBy( destination.getCreatedBy() );
        boLDTO.setStatus( destination.getStatus() );
        boLDTO.setBolId( destination.getBolId() );
        boLDTO.setBolGroupId( destination.getBolGroupId() );
        boLDTO.setCreatedDate( destination.getCreatedDate() );
        boLDTO.setIsValid( destination.getIsValid() );
        boLDTO.setModifiedBy( destination.getModifiedBy() );
        boLDTO.setModifiedDate( destination.getModifiedDate() );
        boLDTO.setIsActive( destination.getIsActive() );
        boLDTO.setVersion( destination.getVersion() );

        return boLDTO;
    }

    @Override
    public List<BoLDTO> bolDomaintoDTO(List<Bol> bolList) {
        if ( bolList == null ) {
            return null;
        }

        List<BoLDTO> list = new ArrayList<BoLDTO>( bolList.size() );
        for ( Bol bol : bolList ) {
            list.add( bolDomaintoDTO( bol ) );
        }

        return list;
    }

    @Override
    public List<BoLDTO> bolDomaintoDTO(Page<Bol> pagebollist) {
        if ( pagebollist == null ) {
            return null;
        }

        List<BoLDTO> list = new ArrayList<BoLDTO>();
        for ( Bol bol : pagebollist ) {
            list.add( bolDomaintoDTO( bol ) );
        }

        return list;
    }

    @Override
    public List<ViewDTO> bolDomaintoViewDTO(Page<Bol> pagebollist) {
        if ( pagebollist == null ) {
            return null;
        }

        List<ViewDTO> list = new ArrayList<ViewDTO>();
        for ( Bol bol : pagebollist ) {
            list.add( bolToViewDTO( bol ) );
        }

        return list;
    }

    @Override
    public List<ViewDTO> bolDomaintoViewDTOList(List<Bol> bolList) {
        if ( bolList == null ) {
            return null;
        }

        List<ViewDTO> list = new ArrayList<ViewDTO>( bolList.size() );
        for ( Bol bol : bolList ) {
            list.add( bolToViewDTO( bol ) );
        }

        return list;
    }

    @Override
    public Set<BolInvoiceDTO> bolDomaintoDTO(Set<BolInvoice> bolInvoicebyBolNumber) {
        if ( bolInvoicebyBolNumber == null ) {
            return null;
        }

        Set<BolInvoiceDTO> set = new HashSet<BolInvoiceDTO>( Math.max( (int) ( bolInvoicebyBolNumber.size() / .75f ) + 1, 16 ) );
        for ( BolInvoice bolInvoice : bolInvoicebyBolNumber ) {
            set.add( bolInvoiceMapper.bolinvoiceDomaintoDTO( bolInvoice ) );
        }

        return set;
    }

    protected Set<BolDetail> boLDetailDTOSetToBolDetailSet(Set<BoLDetailDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolDetail> set1 = new HashSet<BolDetail>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BoLDetailDTO boLDetailDTO : set ) {
            set1.add( bolDetailsMapper.boldetailDTOtoDoman( boLDetailDTO ) );
        }

        return set1;
    }

    protected Set<BolInvoice> bolInvoiceDTOSetToBolInvoiceSet(Set<BolInvoiceDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolInvoice> set1 = new HashSet<BolInvoice>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolInvoiceDTO bolInvoiceDTO : set ) {
            set1.add( bolInvoiceMapper.bolinvoiceDTOtoDoman( bolInvoiceDTO ) );
        }

        return set1;
    }

    protected Set<DoAuthRequest> doAuthRequestDTOSetToDoAuthRequestSet(Set<DoAuthRequestDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<DoAuthRequest> set1 = new HashSet<DoAuthRequest>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DoAuthRequestDTO doAuthRequestDTO : set ) {
            set1.add( doAuthRequestMapper.dTOtoDoman( doAuthRequestDTO ) );
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
        bolChannel.setBol( bolDTOtoDoman( bolChannelDTO.getBol() ) );
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

    protected ChannelTypeDTO channelTypeToChannelTypeDTO(ChannelType channelType) {
        if ( channelType == null ) {
            return null;
        }

        ChannelTypeDTO channelTypeDTO = new ChannelTypeDTO();

        channelTypeDTO.setChannelTypeId( channelType.getChannelTypeId() );
        channelTypeDTO.setDescription( channelType.getDescription() );
        channelTypeDTO.setValue( channelType.getValue() );

        return channelTypeDTO;
    }

    protected BolChannelDTO bolChannelToBolChannelDTO(BolChannel bolChannel) {
        if ( bolChannel == null ) {
            return null;
        }

        BolChannelDTO bolChannelDTO = new BolChannelDTO();

        bolChannelDTO.setChannelId( bolChannel.getChannelId() );
        bolChannelDTO.setReference( bolChannel.getReference() );
        bolChannelDTO.setChannelType( channelTypeToChannelTypeDTO( bolChannel.getChannelType() ) );
        bolChannelDTO.setCreatedBy( bolChannel.getCreatedBy() );
        bolChannelDTO.setBol( bolDomaintoDTO( bolChannel.getBol() ) );
        bolChannelDTO.setCreatedDate( bolChannel.getCreatedDate() );
        bolChannelDTO.setIsValid( bolChannel.getIsValid() );
        bolChannelDTO.setModifiedBy( bolChannel.getModifiedBy() );
        bolChannelDTO.setModifiedDate( bolChannel.getModifiedDate() );
        bolChannelDTO.setIsActive( bolChannel.getIsActive() );

        return bolChannelDTO;
    }

    protected Set<BolChannelDTO> bolChannelSetToBolChannelDTOSet(Set<BolChannel> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolChannelDTO> set1 = new HashSet<BolChannelDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolChannel bolChannel : set ) {
            set1.add( bolChannelToBolChannelDTO( bolChannel ) );
        }

        return set1;
    }

    protected Set<BoLDetailDTO> bolDetailSetToBoLDetailDTOSet(Set<BolDetail> set) {
        if ( set == null ) {
            return null;
        }

        Set<BoLDetailDTO> set1 = new HashSet<BoLDetailDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolDetail bolDetail : set ) {
            set1.add( bolDetailsMapper.boldetailDomaintoDTO( bolDetail ) );
        }

        return set1;
    }

    protected Set<DoAuthRequestDTO> doAuthRequestSetToDoAuthRequestDTOSet(Set<DoAuthRequest> set) {
        if ( set == null ) {
            return null;
        }

        Set<DoAuthRequestDTO> set1 = new HashSet<DoAuthRequestDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DoAuthRequest doAuthRequest : set ) {
            set1.add( doAuthRequestMapper.domaintoDTO( doAuthRequest ) );
        }

        return set1;
    }

    protected ViewDTO bolToViewDTO(Bol bol) {
        if ( bol == null ) {
            return null;
        }

        ViewDTO viewDTO = new ViewDTO();

        viewDTO.setBolNumber( bol.getBolNumber() );
        viewDTO.setBolType( bol.getBolType() );
        viewDTO.setCreatedBy( bol.getCreatedBy() );
        viewDTO.setStatus( bol.getStatus() );
        viewDTO.setBolId( bol.getBolId() );
        viewDTO.setCreatedDate( bol.getCreatedDate() );
        viewDTO.setIsValid( bol.getIsValid() );
        viewDTO.setModifiedBy( bol.getModifiedBy() );
        viewDTO.setModifiedDate( bol.getModifiedDate() );
        viewDTO.setIsActive( bol.getIsActive() );
        viewDTO.setVersion( (int) bol.getVersion() );

        return viewDTO;
    }
}

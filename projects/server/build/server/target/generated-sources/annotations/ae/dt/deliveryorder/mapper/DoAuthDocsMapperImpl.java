package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.dto.DoAuthDocDTO;
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
public class DoAuthDocsMapperImpl implements DoAuthDocsMapper {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public DoAuthDoc dTOtoDoman(DoAuthDocDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DoAuthDoc doAuthDoc = new DoAuthDoc();

        doAuthDoc.setCreatedBy( dto.getCreatedBy() );
        doAuthDoc.setDoAuthDocsId( dto.getDoAuthDocsId() );
        doAuthDoc.setDocumentPath( dto.getDocumentPath() );
        doAuthDoc.setDocumentBean( documentMapper.dTOtoDoman( dto.getDocumentBean() ) );

        return doAuthDoc;
    }

    @Override
    public DoAuthDocDTO domaintoDTO(DoAuthDoc destination) {
        if ( destination == null ) {
            return null;
        }

        DoAuthDocDTO doAuthDocDTO = new DoAuthDocDTO();

        doAuthDocDTO.setDoAuthDocsId( destination.getDoAuthDocsId() );
        doAuthDocDTO.setDocumentPath( destination.getDocumentPath() );
        doAuthDocDTO.setDocumentBean( documentMapper.domaintoDTO( destination.getDocumentBean() ) );
        doAuthDocDTO.setCreatedBy( destination.getCreatedBy() );

        return doAuthDocDTO;
    }

    @Override
    public Set<DoAuthDocDTO> domaintoDTOset(Set<DoAuthDoc> dTOset) {
        if ( dTOset == null ) {
            return null;
        }

        Set<DoAuthDocDTO> set = new HashSet<DoAuthDocDTO>( Math.max( (int) ( dTOset.size() / .75f ) + 1, 16 ) );
        for ( DoAuthDoc doAuthDoc : dTOset ) {
            set.add( domaintoDTO( doAuthDoc ) );
        }

        return set;
    }

    @Override
    public Set<DoAuthDoc> dtotoDomainset(Set<DoAuthDocDTO> dTOset) {
        if ( dTOset == null ) {
            return null;
        }

        Set<DoAuthDoc> set = new HashSet<DoAuthDoc>( Math.max( (int) ( dTOset.size() / .75f ) + 1, 16 ) );
        for ( DoAuthDocDTO doAuthDocDTO : dTOset ) {
            set.add( dTOtoDoman( doAuthDocDTO ) );
        }

        return set;
    }
}

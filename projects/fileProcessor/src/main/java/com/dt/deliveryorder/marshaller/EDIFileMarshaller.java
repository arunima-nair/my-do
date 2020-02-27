package com.dt.deliveryorder.marshaller;

import com.dt.deliveryorder.dto.BOL;
import com.dt.deliveryorder.parser.EdiFileParser;
import com.dt.deliveryorder.util.EdiUtils;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dt.deliveryorder.util.Utils.convertObjToJsonString;

@Component
public class EDIFileMarshaller extends BaseMarshaller {

    private static Map<String, String> ediMap = new HashMap();

    static {
        ediMap.put("HAPAG-LLOYD-IFTFCC-D-11A", "com.dt.deliveryorder.parser.hal.IFTFCCHLD11A");
        ediMap.put("HAPAG-LLOYD-IFTMAN-D-96B", "com.dt.deliveryorder.parser.hal.IFTMANHLD96B");
    }

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        /*
          Not Used
         */
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        File f = getFileFromExchange(exchange);
        String className = ediMap.get(getCustomFileInfo(f));
        Constructor c = Class.forName(className).getConstructor(File.class);
        EdiFileParser ediFileParser = (EdiFileParser) c.newInstance(f);
        Object obj = ediFileParser.parse();
        List<BOL> bolList = null;
        if (obj instanceof Map) {
            Map<String, BOL> resultMap = (Map) obj;
            bolList = resultMap.values().stream().collect(Collectors.toList());

        } else {
            bolList = (List<BOL>) obj;
        }
        bolList.stream().forEach( bol -> {
                    bol.setProcessedFilePath(super.getProcessedFilePath(exchange));
                    bol.setUploadType(super.getUploadType(exchange));
                    bol.setShippingAgentCode(super.getShippingAgentCode(exchange));
        }  );
        String jsonInString = convertObjToJsonString(bolList);
//        updateFileNameInHeader(exchange);
        return jsonInString;
    }


    private String getCustomFileInfo(File f) {
        return EdiUtils.getEdiDetails(f, EdiUtils.EDI_DELIMITER).getEdiText();
    }


}

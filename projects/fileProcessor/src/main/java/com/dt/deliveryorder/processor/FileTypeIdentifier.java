package com.dt.deliveryorder.processor;

import com.dt.deliveryorder.util.EdiUtils;
import com.dt.deliveryorder.util.InpFileType;
import org.apache.camel.Exchange;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.dt.deliveryorder.util.Utils.getRandomNumber;

@Component
public class FileTypeIdentifier extends BaseProcessor {

    @Override
    public void process(Exchange exchange) throws Exception {
        File file = exchange.getIn().getBody(File.class);
        String type = new Tika().detect(file);
        if (type.contains("text/plain")) {
            if (EdiUtils.isUnEdiFACT(exchange.getIn().getBody(File.class))) {
                exchange.getIn().setHeader("FILE_TYPE", InpFileType.EDI);
            } else {
                exchange.getIn().setHeader("FILE_TYPE", InpFileType.TXT);
            }
        } else if (type.contains("text/csv")) {
            exchange.getIn().setHeader("FILE_TYPE", InpFileType.CSV);
        } else if (type.contains("vnd.ms-excel"))
            exchange.getIn().setHeader("FILE_TYPE", InpFileType.XLS);
        else if (type.contains("vnd.openxmlformats"))
            exchange.getIn().setHeader("FILE_TYPE", InpFileType.XLSX);
        else
            throw new RuntimeException("UnknownFile Type " +type);
    }



}

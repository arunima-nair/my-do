package com.dt.deliveryorder.util;

import com.dt.deliveryorder.dto.BolConfDTO;
import com.dt.deliveryorder.dto.MappingDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileMappingUtil {
   private  HashMap<String, BolConfDTO> bolConfMapper = new HashMap<>();

    @Value("${mappingFile.file.path}")
    private String mappingFilePath;

    private void loadFile() {
         if (bolConfMapper.size() > 0)
              return;
        try {

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<MappingDTO> mappingList = mapper.readValue(ResourceUtils.getFile(mappingFilePath),   new TypeReference<List<MappingDTO>>(){});
            mappingList.forEach(item ->{
                bolConfMapper.put(item.getLineCode(),item.getConf());
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Map<String,String> getLineMap(String lineCode){
        loadFile();
        ObjectMapper oMapper = new ObjectMapper();
        return oMapper.convertValue(bolConfMapper.get(lineCode), Map.class);
    }
}

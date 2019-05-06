package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.dto.DataFunctionDTO;
import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.repository.DataFunctionRepository;

@Service
public class DataFunctionService {

    @Autowired
    private DataFunctionRepository repository;

    public List<DataFunction> findAll() {
        return repository.findAll();
    }

    public DataFunction findOne(Long id) {
        return repository.findOne(id);
    }

    public DataFunction save(DataFunctionDTO dfDTO) {
        DataFunction df = createDF(dfDTO);
        return save(df);
    }
    
    public DataFunction save(DataFunction df) {
        return repository.saveAndFlush(df);
    }

    private DataFunction createDF(DataFunctionDTO dfDTO) {
        DataFunction df = new DataFunction();
        if (dfDTO.getType().equals(DataFunction.TYPE_ILF)) {
            df = DataFunction.createILF(dfDTO.getName());
        }
        if (dfDTO.getType().equals(DataFunction.TYPE_EIF)) {
            df = DataFunction.createEIF(dfDTO.getName());
        }
        df.setRecordElementTypes(dfDTO.getRecordElementTypes());
        df.setDataElementTypes(dfDTO.getDataElementTypes());
        df.setProject(dfDTO.getProject());
        df.setUserStory(dfDTO.getUserStory());
        
        return df;
    }
    
    private DataFunctionDTO createDTO(DataFunction df) {
        DataFunctionDTO dto = new DataFunctionDTO();
        dto.setId(df.getId());
        dto.setName(df.getName());
        dto.setType(df.getType());
        dto.setRecordElementTypes(df.getRecordElementTypes());
        dto.setDataElementTypes(df.getDataElementTypes());
        dto.setProject(df.getProject());
        dto.setUserStory(df.getUserStory());
        
        return dto;
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}

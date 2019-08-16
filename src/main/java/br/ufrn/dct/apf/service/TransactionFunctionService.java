package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.dto.DataFunctionDTO;
import br.ufrn.dct.apf.dto.TransactionFunctionDTO;
import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.model.TransactionFunction;
import br.ufrn.dct.apf.repository.DataFunctionRepository;
import br.ufrn.dct.apf.repository.TransactionFunctionRepository;

@Service
public class TransactionFunctionService extends AbstractService {

    @Autowired
    private TransactionFunctionRepository repository;

    public List<TransactionFunction> findAll() {
        return repository.findAll();
    }

    public TransactionFunction findOne(Long id) {
        return repository.findOne(id);
    }

    public TransactionFunction save(TransactionFunctionDTO tfDTO) {
        TransactionFunction tf = createTF(tfDTO);
        return save(tf);
    }
    
    public TransactionFunction save(TransactionFunction df) {
        return repository.saveAndFlush(df);
    }

    private TransactionFunction createTF(TransactionFunctionDTO tfDTO) {
        TransactionFunction tf = new TransactionFunction();
        if (tfDTO.getType().equals(TransactionFunction.TYPE_EI)) {
            tf = TransactionFunction.createEI(tfDTO.getName());
        }
        if (tfDTO.getType().equals(TransactionFunction.TYPE_EO)) {
            tf = TransactionFunction.createEO(tfDTO.getName());
        }
        if (tfDTO.getType().equals(TransactionFunction.TYPE_EQ)) {
            tf = TransactionFunction.createEQ(tfDTO.getName());
        }
        tf.setFileTypeReferenced(tfDTO.getFileTypeReferenced());
        tf.setDataElementTypes(tfDTO.getDataElementTypes());
        tf.setProject(tfDTO.getProject());
        tf.setUserStory(tfDTO.getUserStory());
        
        return tf;
    }
    
    private TransactionFunctionDTO createDTO(TransactionFunction tf) {
        TransactionFunctionDTO dto = new TransactionFunctionDTO();
        dto.setId(tf.getId());
        dto.setName(tf.getName());
        dto.setType(tf.getType());
        dto.setFileTypeReferenced(tf.getFileTypeReferenced());
        dto.setDataElementTypes(tf.getDataElementTypes());
        dto.setProject(tf.getProject());
        dto.setUserStory(tf.getUserStory());
        
        return dto;
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}

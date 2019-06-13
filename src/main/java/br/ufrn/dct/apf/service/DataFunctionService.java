package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.DataFunction;
import br.ufrn.dct.apf.repository.DataFunctionRepository;

@Service
public class DataFunctionService extends AbstractService {

    @Autowired
    private DataFunctionRepository repository;

    public List<DataFunction> findAll() {
        return repository.findAll();
    }

    public DataFunction findOne(Long id) {
        return repository.findOne(id);
    }
    
    public DataFunction save(DataFunction df) {
        return repository.saveAndFlush(df);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}

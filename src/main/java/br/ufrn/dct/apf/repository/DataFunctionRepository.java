package br.ufrn.dct.apf.repository;

import br.ufrn.dct.apf.model.DataFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dataFunctionRepository")
public interface DataFunctionRepository extends JpaRepository<DataFunction, Long> {

    List<DataFunction> findByProject(Long projectId);
}

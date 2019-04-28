package br.ufrn.dct.apf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.DataFunction;

@Repository("dataFunctionRepository")
public interface DataFunctionRepository extends JpaRepository<DataFunction, Long> {

}

package br.ufrn.dct.apf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.TransactionFunction;

@Repository("transactionFunctionRepository")
public interface TransactionFunctionRepository extends JpaRepository<TransactionFunction, Long> {

}

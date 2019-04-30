package br.ufrn.dct.apf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.Attribution;

@Repository("attributionRepository")
public interface AttributionRepository extends JpaRepository<Attribution, Integer> {

    Attribution findByName(String attribName);

}

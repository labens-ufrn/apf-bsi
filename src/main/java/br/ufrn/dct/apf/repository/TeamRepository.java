package br.ufrn.dct.apf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.Team;

@Repository("teamRepository")
public interface TeamRepository extends JpaRepository<Team, Long> {

}

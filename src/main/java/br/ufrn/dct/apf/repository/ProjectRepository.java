package br.ufrn.dct.apf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.Project;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Long> {

}

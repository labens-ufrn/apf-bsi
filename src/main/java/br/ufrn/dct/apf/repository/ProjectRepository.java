package br.ufrn.dct.apf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.Project;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM project p, member m where p.project_id = m.project_id and m.user_id = :userId", nativeQuery=true)
    List<Project> findByUserId(@Param("userId") Long userId);

    List<Project> findByNameContaining(String name);

    List<Project> findByTeamUserIdAndNameContaining(Long userId, String name);

    List<Project> findByIsPrivateFalse();

}

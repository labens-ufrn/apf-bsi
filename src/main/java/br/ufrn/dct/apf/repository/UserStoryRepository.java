package br.ufrn.dct.apf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.UserStory;

@Repository("userStoryRepository")
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

}

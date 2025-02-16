package kr.ac.chosun.devossian.domain.tags.tag.repository;

import kr.ac.chosun.devossian.domain.tags.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("SELECT COUNT(p) FROM PostTag p WHERE p.tag.id = :tagId")
    long countByTagId(@Param("tagId") Long tagId);

}

package kr.ac.chosun.devossian.domain.tags.post_tag_mapping.repository;

import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    Optional<List<PostTag>> findAllByUuid(String uuid);
    void deleteAllByUuid(String uuid);
}

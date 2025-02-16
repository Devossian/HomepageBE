package kr.ac.chosun.devossian.domain.tags.post_tag_mapping.service;

import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.domain.PostTag;
import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.repository.PostTagRepository;
import kr.ac.chosun.devossian.domain.tags.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostTagService {
    private final PostTagRepository postTagRepository;

    @Transactional
    public void add(Tag tag, String uuid) {
        postTagRepository.save(new PostTag(tag, uuid));
    }

    @Transactional
    public void addAll(List<PostTag> postTags){
        postTagRepository.saveAll(postTags);
    }


    @Transactional
    public List<PostTag> deleteAllByUuid(String uuid) {
        List<PostTag> postTags = postTagRepository.findAllByUuid(uuid).orElse(Collections.emptyList());

        postTagRepository.deleteAllByUuid(uuid);

        return postTags;
    }

    public Optional<List<PostTag>> findAllByUuid(String uuid) {
        return postTagRepository.findAllByUuid(uuid);
    }

    @Transactional
    public void deleteAll(List<PostTag> toDelete) {
        postTagRepository.deleteAll(toDelete);
    }
}

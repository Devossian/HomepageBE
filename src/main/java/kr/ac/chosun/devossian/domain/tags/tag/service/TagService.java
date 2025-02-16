package kr.ac.chosun.devossian.domain.tags.tag.service;

import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.domain.PostTag;
import kr.ac.chosun.devossian.domain.tags.tag.domain.Tag;
import kr.ac.chosun.devossian.domain.tags.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Tag add(String name){
        return tagRepository.save(new Tag(name));
    }


    @Transactional
    public Tag findByNameOrElseCreate(String name){
        return tagRepository.findByName(name)
                .orElseGet(() -> add(name));
    }

    @Transactional
    public List<PostTag> findTags(List<String> tags, String uuid) {
        List<PostTag> addPostTags = new ArrayList<>();
        for(String tagName : tags){
            Tag tag = findByNameOrElseCreate(tagName);
            addPostTags.add(new PostTag(tag, uuid));
        }
        return addPostTags;
    }

    @Transactional
    public void deleteUnusedTags(List<PostTag> postTags) {
        List<Tag> tagsToDelete = new ArrayList<>();
        for(PostTag postTag: postTags) {
            if (tagRepository.countByTagId(postTag.getId()) == 0){
                tagsToDelete.add(postTag.getTag());
            }
        }
        tagRepository.deleteAll(tagsToDelete);
    }


}

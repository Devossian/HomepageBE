package kr.ac.chosun.devossian.domain.tags;

import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.domain.PostTag;
import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.service.PostTagService;
import kr.ac.chosun.devossian.domain.tags.tag.domain.Tag;
import kr.ac.chosun.devossian.domain.tags.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagManager {

    private final TagService tagService;
    private final PostTagService postTagService;


    /**
     * 게시글에 태그를 추가합니다.
     * @param tags 추가할 태그 이름 리스트 (ex, "Spring", "DevOps" .. )
     * @param uuid 태그를 추가할 게시글 uuid
     */
    @Transactional
    public void addTags(List<String> tags, String uuid){
        List<PostTag> addPostTags = tagService.findTags(tags, uuid);
        postTagService.addAll(addPostTags);
    }


    /**
     * uuid로 태그를 조회합니다
     * @param uuid 태그를 조회할 게시글 uuid
     * @return 태그 이름 리스트 (ex, "Spring", "DevOps" ..)
     */
    public List<String> getTags(String uuid){
        List<String> result = new ArrayList<>();

        // 1. uuid로 postTag 조회
        List<PostTag> postTags = postTagService.findAllByUuid(uuid)
                .orElse(Collections.emptyList());

        // 2. 태그 이름을 리스트에 추가
        for(PostTag postTag : postTags)
            result.add(postTag.getTag().getName());

        return result;
    }


    /**
     * 기존 게시글의 태그를 변경합니다
     * @param tags 변경할 태그 이름 리스트
     * @param uuid 태그를 변경할 게시글 uuid
     */
    @Transactional
    public void updateTags(List<String> tags, String uuid){
        // 1. uuid로 postTag 조회
        List<PostTag> postTags = postTagService.findAllByUuid(uuid).orElse(Collections.emptyList());

        Set<String> existingTags = postTags.stream()
                                        .map(postTag -> postTag.getTag().getName())
                                        .collect(Collectors.toSet());

        Set<String> newTagSet = new HashSet<>(tags);

        // 2. 기존 태그에서 삭제될 태그 조회
        List<PostTag> tagsToDelete = postTags.stream()
                                        .filter(tagPost -> !newTagSet.contains(tagPost.getTag().getName()))
                                        .toList();

        // 3. 태그 삭제
        if(!tagsToDelete.isEmpty())
            postTagService.deleteAll(tagsToDelete);

        tagService.deleteUnusedTags(postTags);

        // 4. 새롭게 추가될 태그 결정
        List<String> tagsToAdd = newTagSet.stream()
                                        .filter(tag -> !existingTags.contains(tag))
                                        .toList();

        // 5. 새로운 PostTag 추가
        List<PostTag> addPostTags = tagService.findTags(tagsToAdd, uuid);
        postTagService.addAll(addPostTags);
    }

    /**
     * 게시글과 관련된 태그를 삭제합니다.
     * @param uuid
     */
    @Transactional
    public void deleteTags(String uuid){

        // 1. uuid로 postTag 삭제
        List<PostTag> postTags = postTagService.deleteAllByUuid(uuid);

        // 2. 삭제된 태그가 더 이상 참조되지 않는 경우 정리
        tagService.deleteUnusedTags(postTags);
    }




}

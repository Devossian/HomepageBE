package kr.ac.chosun.devossian.domain.tags;

import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.domain.PostTag;
import kr.ac.chosun.devossian.domain.tags.post_tag_mapping.service.PostTagService;
import kr.ac.chosun.devossian.domain.tags.tag.domain.Tag;
import kr.ac.chosun.devossian.domain.tags.tag.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TagManagerTest {

    @Mock
    private TagService tagService;

    @Mock
    private PostTagService postTagService;

    @InjectMocks
    private TagManager tagManager;

    private String uuid = "123-uuid";
    private List<String> tags = Arrays.asList("Spring", "DevOps");

    @BeforeEach
    public void setUp() {
        // 테스트 전 초기화가 필요한 데이터나 설정
    }

    @Test
    public void testAddTags() {
        // given
        List<PostTag> postTags = Arrays.asList(new PostTag(), new PostTag());

        // postTag List mocking
        when(tagService.findTags(tags, uuid)).thenReturn(postTags);

        // when
        tagManager.addTags(tags, uuid);

        // then
        verify(postTagService, times(1)).addAll(postTags);
    }

    @Test
    public void testGetTags() {
        // given
        PostTag postTag = new PostTag(new Tag(1L, "Spring"), uuid);
        List<PostTag> postTags = Collections.singletonList(postTag);
        when(postTagService.findAllByUuid(uuid)).thenReturn(Optional.of(postTags));

        // when
        List<String> result = tagManager.getTags(uuid);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spring", result.get(0));
    }

    @Test
    public void testUpdateTags() {
        // given
        List<String> newTags = Arrays.asList("Java", "Spring Boot"); // 갱신할 태그
        List<PostTag> postTags = Arrays.asList(new PostTag(new Tag("Spring"), uuid)); // 기존 태그

        when(postTagService.findAllByUuid(uuid)).thenReturn(Optional.of(postTags)); // 기존 태그 검색 -> postTags 반환
        when(tagService.findTags(Arrays.asList("Java", "Spring Boot"), uuid)).thenReturn(Arrays.asList(new PostTag()));

        // when
        tagManager.updateTags(newTags, uuid);

        // then
        verify(postTagService, times(1)).deleteAll(anyList());
        verify(postTagService, times(1)).addAll(anyList());
    }

    @Test
    public void testDeleteTags() {
        // given
        List<PostTag> postTags = Arrays.asList(new PostTag());
        when(postTagService.deleteAllByUuid(uuid)).thenReturn(postTags);

        // when
        tagManager.deleteTags(uuid);

        // then
        verify(postTagService, times(1)).deleteAllByUuid(uuid);
        verify(tagService, times(1)).deleteUnusedTags(postTags);
    }

    @Test
    public void testAddTags_Exception() {
        // given
        when(tagService.findTags(tags, uuid)).thenThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(RuntimeException.class, () -> {
            tagManager.addTags(tags, uuid);
        });
    }
}
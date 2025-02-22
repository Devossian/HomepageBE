package kr.ac.chosun.devossian.domain.maintenance.service;

import kr.ac.chosun.devossian.domain.maintenance.domain.Maintenance;
import kr.ac.chosun.devossian.domain.maintenance.domain.Type;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceCreateRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceResponseDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceSearchRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceUpdateRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.repository.MaintenanceRepository;
import kr.ac.chosun.devossian.domain.tags.TagManager;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.global.error.ErrorCodeMessage;
import kr.ac.chosun.devossian.global.error.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final TagManager tagManager;


    @Transactional
    public MaintenanceResponseDto create(MaintenanceCreateRequestDto dto, User user) {
        // 1. 글 생성
        Maintenance post = MaintenanceCreateRequestDto.toEntity(dto, user);
        Maintenance result = maintenanceRepository.save(post);

        String uuid = post.getUuid();

        // 2. 태그 추가
        tagManager.addTags(dto.getTags(), uuid);

        // 3. 이미지 처리 로직
        // TODO 이미지 모듈 및 업로드 로직 확정 후 추가

        // 4. 태그와 함께 DTO로 응답
        List<String> tags = tagManager.getTags(uuid);
        return MaintenanceResponseDto.toDto(result, tags);
    }

    public MaintenanceResponseDto findById(Long id) {
        Maintenance post = maintenanceRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException(ErrorCodeMessage.POST_NOT_FOUND));

        List<String> tags = tagManager.getTags(post.getUuid());
        return MaintenanceResponseDto.toDto(post, tags);
    }

    @Transactional
    public MaintenanceResponseDto update(MaintenanceUpdateRequestDto dto, Long id) {
        Maintenance post = maintenanceRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException(ErrorCodeMessage.POST_NOT_FOUND));

        post.updateTitle(dto.getTitle());
        post.updateContent(dto.getContent());

        // update tags
        tagManager.updateTags(dto.getTags(), post.getUuid());

        // 이미지 처리 로직
        // TODO 이미지 모듈 및 업로드 로직 확정 후 추가

        List<String> tags = tagManager.getTags(post.getUuid());
        return MaintenanceResponseDto.toDto(post, tags);
    }

    @Transactional
    public void deleteById(Long id) {
        Maintenance post = maintenanceRepository.findById(id).orElseThrow(() ->
            new PostNotFoundException(ErrorCodeMessage.POST_NOT_FOUND));

        tagManager.deleteTags(post.getUuid());
        maintenanceRepository.deleteById(id);
    }

    public Page<MaintenanceResponseDto> getList(MaintenanceSearchRequestDto dto, Pageable pageable) {
        Page<Maintenance> list = getListByType(dto, pageable);

        return list.map(maintenance -> {
            List<String> tags = tagManager.getTags(maintenance.getUuid());
            return MaintenanceResponseDto.toDto(maintenance, tags);
        });
    }

    private Page<Maintenance> getListByType(MaintenanceSearchRequestDto dto, Pageable pageable){
        if(dto.getType() == Type.title)
            return maintenanceRepository.findByTitleContaining(dto.getContent(), pageable);

        if(dto.getType() == Type.tag)
            return maintenanceRepository.findByTag(dto.getContent(), pageable);

        return maintenanceRepository.findAllByOrderByIdAsc(pageable);
    }
}

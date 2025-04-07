package com.example.demo.free.service;

import com.example.demo.free.dto.FreeDto;
import com.example.demo.free.entity.Free;
import com.example.demo.free.repository.FreeRepository;
import com.example.demo.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//생성자를 자동으로 생성해줌(final, not null, 필드들을 매개변수로 사용하는 생성자 자동생성), Lombok의 기능
@RequiredArgsConstructor
@Service
public class FreeService {
    private final FreeRepository freeRepository;
    public void create(FreeDto dto, SiteUser author)
    {
        Free post = new Free();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(author);
        freeRepository.save(post);
    }

    public void update(Long id, FreeDto dto)
    {
        Free post = getPostId(id);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        //post.setAuthor(author);
        freeRepository.save(post);
    }
    public void delete(Long id)
    {
        freeRepository.deleteById(id);
    }

    public List<Free> findAll(){
        return freeRepository.findAll();
    }

    public Free findById(Long id)
    {
        return freeRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }
    public Free getPostId(Long id) {
        return freeRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }
}

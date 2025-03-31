package com.example.demo.free;

import com.example.demo.user.SiteUser;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//생성자를 자동으로 생성해줌(final, not null, 필드들을 매개변수로 사용하는 생성자 자동생성), Lombok의 기능
@RequiredArgsConstructor
@Service
public class FreeService {
    private final FreeRepository freeRepository;
    public void createPost(String title, String content, SiteUser author)
    {
        Free post = new Free();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        freeRepository.save(post);
    }

    public void modifyPost(Long id, String title, String content, SiteUser author)
    {
        Free post = getPostId(id);
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        freeRepository.save(post);
    }
    public void deletePost(Long id)
    {
        Free post = getPostId(id);
        //freeRepository.delete(post);
        freeRepository.deleteById(id);
    }

    public List<Free> getAllPosts(){
        return freeRepository.findAll();
    }

    public Free getPostId(Long id) {
        return freeRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }
}

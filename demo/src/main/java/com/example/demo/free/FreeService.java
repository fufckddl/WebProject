package com.example.demo.free;

import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//생성자를 자동으로 생성해줌(final, not null, 필드들을 매개변수로 사용하는 생성자 자동생성), Lombok의 기능
@RequiredArgsConstructor
@Service
public class FreeService {
    private final FreeRepository freeRepository;
    public void createPost(String title, String content)
    {
        Free post = new Free();
        post.setTitle(title);
        post.setContent(content);
        freeRepository.save(post);
    }

}

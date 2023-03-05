package sauprojects.sauvest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.PostDTO;
import sauprojects.sauvest.entity.Post;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.repository.PostRepository;
import sauprojects.sauvest.service.PostService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final UserService userService;

	@Override
	public Post findPostById(Long postId) {
		
		Optional<Post> postOptional = postRepository.findById(postId);
		
		Post post = postOptional.orElseThrow(() -> new RuntimeException(String.format("Post with id '%s' not found.", postId)));
		
		return post;
	}
	
	@Override
	public PostDTO getPostById(Long postId) {
		return PostDTO.buildPostDTO(this.findPostById(postId));
	}
	
	@Override
	public List<PostDTO> getAllPosts() {
		return postRepository.findAll().stream().map(PostDTO::buildPostDTO).toList();
	}

	@Override
	public PostDTO savePost(PostDTO postDTO) {
		postRepository.save(this.buildPost(postDTO));
		return postDTO;
	}

	@Override
	public PostDTO savePost(Post post) {
		return PostDTO.buildPostDTO(postRepository.save(post));
	}

	@Override
	public Post buildPost(PostDTO postDTO) {
		return Post.builder()
					   .user(userService.findUserByUsername(postDTO.getUsername()))
					   .content(postDTO.getContent())
					   .imgPath(postDTO.getImgPath())
				   .build();
	}

	@Override
	public List<PostDTO> getAllPostsByUsername(String username) {
		User user = userService.findUserByUsername(username);
		return postRepository.findByUser(user).stream().map(PostDTO::buildPostDTO).toList();
	}
	
	@Override
	public void deletePostById(Long postId) {
		
		postRepository.deleteById(postId);
	}

}

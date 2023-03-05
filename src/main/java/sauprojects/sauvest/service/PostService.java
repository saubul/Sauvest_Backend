package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.PostDTO;
import sauprojects.sauvest.entity.Post;

public interface PostService {
	
	PostDTO getPostById(Long postId);
	
	Post findPostById(Long postId);
	
	PostDTO savePost(PostDTO postDTO);
	
	PostDTO savePost(Post post);
	
	Post buildPost(PostDTO postDTO);
	
	List<PostDTO> getAllPosts();
	
	List<PostDTO> getAllPostsByUsername(String username);
	
	void deletePostById(Long postId);
	
}

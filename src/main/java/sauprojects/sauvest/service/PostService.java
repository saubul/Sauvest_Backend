package sauprojects.sauvest.service;

import sauprojects.sauvest.dto.PostDTO;
import sauprojects.sauvest.entity.Post;

public interface PostService {
	
	Post findPostById(Long postId);
	
	PostDTO savePost(PostDTO postDTO);
	
	PostDTO savePost(Post post);
	
	Post buildPost(PostDTO postDTO);
	
}

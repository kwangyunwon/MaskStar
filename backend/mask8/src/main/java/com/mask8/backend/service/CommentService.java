package com.mask8.backend.service;

import java.util.HashMap;
import java.util.List;

import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.CommentLikeVO;
import com.mask8.backend.domain.CommentVO;

public interface CommentService {
	boolean insertComment(CommentVO commentVO);
	boolean updateComment(CommentVO commentVO);
	boolean deleteComment(int commentIdx);
	List<CommentVO> selectAllComments(HashMap map);
	
	boolean insertCommentLike(CommentLikeVO commentLikeVO);
	boolean deleteCommentLike(CommentLikeVO commentLikeVO);
	CommentLikeVO checkCommentLike(CommentLikeVO commentLikeVO); 
	boolean updateCommentLike(int commentIdx);
	int commentCnt(int boardIdx);
	List<Integer> commentLikeList(HashMap<String, Integer> map);
}

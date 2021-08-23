package com.mask8.backend.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.CommentLikeVO;
import com.mask8.backend.domain.CommentVO;
import com.mask8.backend.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge = 6000)
@Api(tags = { "Mask8" })
@RequestMapping("comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@ApiOperation(value = "댓글을 작성.", response = CommentVO.class)
	@PostMapping("insertcomment")
	public ResponseEntity<CommentVO> insertComment(@RequestBody CommentVO commentVO) {
		boolean result = commentService.insertComment(commentVO);
		if (!result)
			return new ResponseEntity<CommentVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<CommentVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "댓글을 수정한다.", response = CommentVO.class)
	@PutMapping("updatecomment")
	public ResponseEntity<CommentVO> updateComment(@RequestBody CommentVO commentVO) {
		boolean result = commentService.updateComment(commentVO);
		if (!result)
			return new ResponseEntity<CommentVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<CommentVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "댓글을 삭제한다.", response = CommentVO.class)
	@PutMapping("deletecomment/{commentIdx}")
	public ResponseEntity<CommentVO> deleteComment(@PathVariable int commentIdx) {
		boolean result = commentService.deleteComment(commentIdx);
		if (!result)
			return new ResponseEntity<CommentVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<CommentVO>(HttpStatus.OK);
	}

	/*
	 * 아래 기능은 Board에 가야될듯 함 GetMapping("boardDetail/{boardIdx}") 같은 명칭으로 바꿔서 댓글,
	 * 게시글에 대한 호출 한번에 하면좋을듯
	 */
	@ApiOperation(value = "게시글 번호로 댓글을 가져온다.", response = CommentVO.class)
	@GetMapping("selectallcomments")
	public ResponseEntity<List<CommentVO>> selectAllComments(@Param(value = "boardIdx") int boardIdx, @Param(value = "limit") int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardIdx", boardIdx);
		map.put("limit", limit);
		List<CommentVO> comments = commentService.selectAllComments(map);
		if (comments == null)
			return new ResponseEntity<List<CommentVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<CommentVO>>(comments, HttpStatus.OK);
	}

	@ApiOperation(value = "해당 댓글 좋아요 추가", response = CommentLikeVO.class)
	@PostMapping("insertcommentlike")
	public ResponseEntity<CommentLikeVO> insertCommentLike(@RequestBody CommentLikeVO commentLikeVO) {
		CommentLikeVO checkCommentLike = commentService.checkCommentLike(commentLikeVO);
		if (checkCommentLike == null) {
			commentService.insertCommentLike(commentLikeVO);
			commentService.updateCommentLike(commentLikeVO.getCommentIdx());
			return new ResponseEntity<CommentLikeVO>(HttpStatus.OK);
		}
		else {
			commentService.deleteCommentLike(commentLikeVO);
			commentService.updateCommentLike(commentLikeVO.getCommentIdx());
			return new ResponseEntity<CommentLikeVO>(HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "댓글의 개수를 반환.", response = Integer.class)
	@GetMapping("commentcnt/{boardIdx}")
	public ResponseEntity<Integer> commentCnt(@PathVariable int boardIdx) {
		int cnt = commentService.commentCnt(boardIdx);
				
		return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요 리스트를 가져온다.", response = Integer.class)
	@GetMapping("commentlikelist")
	public ResponseEntity<List<Integer>> commentLikeList(@RequestParam(value = "boardIdx") int boardIdx, @RequestParam(value = "userIdx") int userIdx) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardIdx", boardIdx);
		map.put("userIdx", userIdx);
		List<Integer> likeList = commentService.commentLikeList(map);
		if (likeList == null)
			return new ResponseEntity<List<Integer>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Integer>>(likeList, HttpStatus.OK);
	}
}

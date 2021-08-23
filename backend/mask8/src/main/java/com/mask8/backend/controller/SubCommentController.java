package com.mask8.backend.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.CommentLikeVO;
import com.mask8.backend.domain.CommentVO;
import com.mask8.backend.domain.SubCommentVO;
import com.mask8.backend.service.SubCommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge = 6000)
@Api(tags = { "Mask8" })
@RequestMapping("subcomment")
public class SubCommentController {

	@Autowired
	private SubCommentService subCommentService;

	@ApiOperation(value = "대댓글을 작성.", response = SubCommentVO.class)
	@PostMapping("insertsubcomment")
	public ResponseEntity<SubCommentVO> insertSubComment(@RequestBody SubCommentVO subCommentVO) {
		boolean result = subCommentService.insertSubComment(subCommentVO);
		if (!result)
			return new ResponseEntity<SubCommentVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<SubCommentVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "대댓글을 수정한다.", response = SubCommentVO.class)
	@PutMapping("updatesubcomment")
	public ResponseEntity<SubCommentVO> updateSubComment(@RequestBody SubCommentVO subCommentVO) {
		boolean result = subCommentService.updateSubComment(subCommentVO);
		if (!result)
			return new ResponseEntity<SubCommentVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<SubCommentVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "대댓글을 삭제한다.", response = SubCommentVO.class)
	@PutMapping("deletesubcomment/{subCommentIdx}")
	public ResponseEntity<SubCommentVO> deleteSubComment(@PathVariable int subCommentIdx) {
		boolean result = subCommentService.deleteSubComment(subCommentIdx);
		if (!result)
			return new ResponseEntity<SubCommentVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<SubCommentVO>(HttpStatus.OK);
	}

	/*
	 * 아래 기능은 Board에 가야될듯 함 GetMapping("boardDetail/{boardIdx}") 같은 명칭으로 바꿔서 댓글,
	 * 게시글에 대한 호출 한번에 하면좋을듯
	 */
	@ApiOperation(value = "boardIdx로 대댓글을 가져온다.", response = SubCommentVO.class)
	@GetMapping("selectallsubcomments/{boardIdx}")
	public ResponseEntity<List<SubCommentVO>> selectAllSubComments(@PathVariable int boardIdx) {
		List<SubCommentVO> comments = subCommentService.selectAllSubComments(boardIdx);
		if (comments == null)
			return new ResponseEntity<List<SubCommentVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<SubCommentVO>>(comments, HttpStatus.OK);
	}
}

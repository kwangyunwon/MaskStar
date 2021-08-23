package com.mask8.backend.controller;

import java.util.List;
import java.util.Map;

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
import com.mask8.backend.domain.BoardVO;
import com.mask8.backend.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Mask8 Board" })
@RequestMapping("board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@ApiOperation(value = "게시글을 작성한다.", response = BoardVO.class)
	@PostMapping("createboard")
	public ResponseEntity<BoardVO> createBoard(@RequestBody BoardVO board) {
		boolean result = boardService.createBoard(board);
		if (!result)
			return new ResponseEntity<BoardVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<BoardVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "게시글을 수정한다.", response = BoardVO.class)
	@PutMapping("updateboard")
	public ResponseEntity<BoardVO> updateBoard(@RequestBody BoardVO board) {
		boolean result = boardService.updateBoard(board);
		if (!result)
			return new ResponseEntity<BoardVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<BoardVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "게시글을 삭제한다.", response = BoardVO.class)
	@DeleteMapping("deleteboard/{boardIdx}")
	public ResponseEntity<BoardVO> deleteBoard(@PathVariable int boardIdx) {
		boolean result = boardService.deleteBoard(boardIdx);
		if (!result)
			return new ResponseEntity<BoardVO>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<BoardVO>(HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 번호로 게시글의 정보를 가져온다.", response = BoardVO.class)
	@GetMapping("selectboardbyid/{boardIdx}")
	public ResponseEntity<BoardVO> selectBoardById(@PathVariable int boardIdx) {
		BoardVO board = boardService.selectBoardById(boardIdx);
		if (board == null)
			return new ResponseEntity<BoardVO>(HttpStatus.NO_CONTENT);
		boardService.updateCount(boardIdx);
		return new ResponseEntity<BoardVO>(board, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 좋아요를 누른다.", response = BoardLikeVO.class)
	@PostMapping("addlike")
	public ResponseEntity<BoardLikeVO> addLike(@RequestBody BoardLikeVO boardLikeVO) {
		BoardLikeVO checkLike = boardService.checkLike(boardLikeVO);
		if (checkLike == null) {
			boardService.addLike(boardLikeVO);
			boardService.updateLike(boardLikeVO.getBoardIdx());
			return new ResponseEntity<BoardLikeVO>(HttpStatus.OK);
		} else {
			boardService.deleteLike(boardLikeVO);
			boardService.updateLike(boardLikeVO.getBoardIdx());
			return new ResponseEntity<BoardLikeVO>(HttpStatus.OK);
		}
	}

	@ApiOperation(value = "좋아요를 눌렀는지 확인한다.", response = BoardLikeVO.class)
	@GetMapping("checklike")
	public ResponseEntity<Integer> checkLike(@RequestParam int userIdx, @RequestParam int boardIdx) {

		BoardLikeVO boardLikeVO = new BoardLikeVO(userIdx, boardIdx);
		BoardLikeVO checkLike = boardService.checkLike(boardLikeVO);
		if (checkLike == null)
			return new ResponseEntity<Integer>(0, HttpStatus.OK);
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}

	@ApiOperation(value = "한 주 동안 업로드된 영상중 가장 추천수가 많은 10개를 보여준다", response = BoardVO.class)
	@GetMapping("toprankall")
	public ResponseEntity<List<BoardVO>> selectBoardByLike() throws Exception {
		List<BoardVO> boards = boardService.selectBoardByLike();
		if (boards.isEmpty())
			return new ResponseEntity<List<BoardVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BoardVO>>(boards, HttpStatus.OK);

	}

	@ApiOperation(value = "장르별로 한 주 동안 업로드된 영상중 가장 추천수가 많은 10개를 보여준다", response = BoardVO.class)
	@GetMapping("toprankgenre/{genre}")
	public ResponseEntity<List<BoardVO>> selectBoardByGenre(@PathVariable int genre) throws Exception {
		List<BoardVO> boards = boardService.selectBoardByGenre(genre);
		if (boards.isEmpty())
			return new ResponseEntity<List<BoardVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BoardVO>>(boards, HttpStatus.OK);

	}

	@ApiOperation(value = "장르별 영상을 주전순,최신순으로 전부 보여준다", response = BoardVO.class)
	@GetMapping("selectgenre")
	public ResponseEntity<List<BoardVO>> selectGenre(@RequestParam Map<String, Object> paramMap) throws Exception {
		int start = Integer.parseInt((String) paramMap.get("start"));
		paramMap.put("start",start);
		List<BoardVO> boards = boardService.selectGenre(paramMap);
		if (boards.isEmpty())
			return new ResponseEntity<List<BoardVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BoardVO>>(boards, HttpStatus.OK);

	}

	@ApiOperation(value = "유저의 닉네임으로 영상을 검색한다", response = BoardVO.class)
	@GetMapping("searchnickname")
	public ResponseEntity<List<BoardVO>> searchBoardByNickname(@RequestParam Map<String, Object> paramMap) throws Exception {
		int start = Integer.parseInt((String) paramMap.get("start"));
		paramMap.put("start",start);
		List<BoardVO> boards = boardService.searchBoardByNickname(paramMap);
		if (boards.isEmpty())
			return new ResponseEntity<List<BoardVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BoardVO>>(boards, HttpStatus.OK);

	}

	@ApiOperation(value = "노래 제목으로 영상을 검색한다", response = BoardVO.class)
	@GetMapping("searchtitle")
	public ResponseEntity<List<BoardVO>> searchBoardByTitle(@RequestParam Map<String, Object> paramMap) throws Exception {
		int start = Integer.parseInt((String) paramMap.get("start"));
		paramMap.put("start",start);
		List<BoardVO> boards = boardService.searchBoardByTitle(paramMap);
		if (boards.isEmpty())
			return new ResponseEntity<List<BoardVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BoardVO>>(boards, HttpStatus.OK);

	}

	@ApiOperation(value = "가수이름으로 영상을 검색한다", response = BoardVO.class)
	@GetMapping("searchsinger")
	public ResponseEntity<List<BoardVO>> searchBoardBySinger(@RequestParam Map<String, Object> paramMap) throws Exception {
		int start = Integer.parseInt((String) paramMap.get("start"));
		paramMap.put("start",start);
		List<BoardVO> boards = boardService.searchBoardBySinger(paramMap);
		if (boards.isEmpty())
			return new ResponseEntity<List<BoardVO>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<BoardVO>>(boards, HttpStatus.OK);

	}

}

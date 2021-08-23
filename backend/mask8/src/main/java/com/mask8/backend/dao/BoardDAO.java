package com.mask8.backend.dao;


import java.util.List;
import java.util.Map;

import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.BoardVO;

public interface BoardDAO {
	boolean createBoard(BoardVO boardVO);
	boolean updateBoard(BoardVO boardVO);
	boolean deleteBoard(int boardIdx);
	BoardVO selectBoardById(int boardIdx);
	boolean addLike(BoardLikeVO boardLikeVO);
	boolean deleteLike(BoardLikeVO boardLikeVO);
	boolean updateLike(int boardIdx);
	BoardLikeVO checkLike(BoardLikeVO boardLikeVO);
	boolean updateCount(int boardIdx);
	public List<BoardVO> selectBoardByLike();
	public List<BoardVO> selectBoardByGenre(int genre);
	public List<BoardVO> selectGenre(Map<String, Object> paramMap);
	public List<BoardVO> searchBoardByNickname(Map<String, Object> paramMap);
	public List<BoardVO> searchBoardByTitle(Map<String, Object> paramMap);
	public List<BoardVO> searchBoardBySinger(Map<String, Object> paramMap);
	
}
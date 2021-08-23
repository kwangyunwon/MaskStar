package com.mask8.backend.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mask8.backend.dao.BoardDAO;
import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.BoardVO;
import com.mask8.backend.service.BoardService;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public boolean createBoard(BoardVO boardVO) {
		return boardDAO.createBoard(boardVO);
	}

	@Override
	public boolean updateBoard(BoardVO boardVO) {
		return boardDAO.updateBoard(boardVO);
	}

	@Override
	public boolean deleteBoard(int boardIdx) {
		return boardDAO.deleteBoard(boardIdx);
	}

	@Override
	public BoardVO selectBoardById(int boardIdx) {
		return boardDAO.selectBoardById(boardIdx);
	}

	@Override
	public boolean addLike(BoardLikeVO boardLikeVO) {
		return boardDAO.addLike(boardLikeVO);
	}

	@Override
	public boolean deleteLike(BoardLikeVO boardLikeVO) {
		return boardDAO.deleteLike(boardLikeVO);
	}
	
	@Override
	public boolean updateLike(int boardIdx) {
		return boardDAO.updateLike(boardIdx);
	}

	@Override
	public BoardLikeVO checkLike(BoardLikeVO boardLikeVO) {
		return boardDAO.checkLike(boardLikeVO);
	}
	
	@Override
	public List<BoardVO> selectBoardByLike() {
		return boardDAO.selectBoardByLike();
	}

	@Override
	public List<BoardVO> selectBoardByGenre(int genre) {
		return boardDAO.selectBoardByGenre(genre);
	}

	@Override
	public List<BoardVO> selectGenre(Map<String, Object> paramMap) {
		return boardDAO.selectGenre(paramMap);
	}

	@Override
	public List<BoardVO> searchBoardByNickname(Map<String, Object> paramMap) {
		return boardDAO.searchBoardByNickname(paramMap);
	}

	@Override
	public List<BoardVO> searchBoardByTitle(Map<String, Object> paramMap) {
		return boardDAO.searchBoardByTitle(paramMap);
	}

	@Override
	public List<BoardVO> searchBoardBySinger(Map<String, Object> paramMap) {
		return boardDAO.searchBoardBySinger(paramMap);
	}

	@Override
	public boolean updateCount(int boardIdx) {
		return boardDAO.updateCount(boardIdx);
	}

	

}

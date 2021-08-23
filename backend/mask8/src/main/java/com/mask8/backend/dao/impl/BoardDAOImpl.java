package com.mask8.backend.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mask8.backend.dao.BoardDAO;
import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.BoardVO;


@Repository
public class BoardDAOImpl implements BoardDAO{

	
	private String ns = "boardMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean createBoard(BoardVO boardVO) {
		int result = sqlSession.insert(ns + "insertBoard", boardVO);
		if(result > 0) return true;
		return false;
	}

	@Override
	public boolean updateBoard(BoardVO boardVO) {
		int result = sqlSession.update(ns + "updateBoard", boardVO);
		if(result > 0) return true;
		return false;
	}

	@Override
	public boolean deleteBoard(int boardIdx) {
		int result = sqlSession.delete(ns + "deleteBoard", boardIdx);
		if(result > 0) return true;
		return false;
	}

	@Override
	public BoardVO selectBoardById(int boardIdx) {
		return sqlSession.selectOne(ns + "boardDetail", boardIdx);
	}

	@Override
	public boolean addLike(BoardLikeVO boardLikeVO) {
		int result = sqlSession.insert(ns + "insertBoardLike", boardLikeVO);
		if(result > 0) return true;
		return false;
	}

	@Override
	public boolean deleteLike(BoardLikeVO boardLikeVO) {
		int result = sqlSession.delete(ns + "deleteBoardLike", boardLikeVO);
		if(result > 0) return true;
		return false;
	}
	
	@Override
	public boolean updateLike(int boardIdx) {
		int result = sqlSession.update(ns + "updateLike", boardIdx);
		if(result > 0) return true;
		return false;
	}

	@Override
	public BoardLikeVO checkLike(BoardLikeVO boardLikeVO) {
		return sqlSession.selectOne(ns + "checkLike", boardLikeVO);
		}

	
	@Override
	public List<BoardVO> selectBoardByLike() {
		return sqlSession.selectList(ns+"topRankAll");
	}

	@Override
	public List<BoardVO> selectBoardByGenre(int genre) {
		return sqlSession.selectList(ns+"topRankGenre",genre);
	}

	@Override
	public List<BoardVO> selectGenre(Map<String, Object> paramMap) {
		return sqlSession.selectList(ns+"selectGenre",paramMap);
	}

	@Override
	public List<BoardVO> searchBoardByNickname(Map<String, Object> paramMap) {
		return sqlSession.selectList(ns+"searchNickname",paramMap);
	}

	@Override
	public List<BoardVO> searchBoardByTitle(Map<String, Object> paramMap) {
		return sqlSession.selectList(ns+"searchTitle",paramMap);
	}

	@Override
	public List<BoardVO> searchBoardBySinger(Map<String, Object> paramMap) {
		return sqlSession.selectList(ns+"searchSinger",paramMap);
	}

	@Override
	public boolean updateCount(int boardIdx) {
		int result = sqlSession.update(ns + "updateCount", boardIdx);
		if(result > 0) return true;
		return false;
	}

}

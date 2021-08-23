package com.mask8.backend.domain;

public class BoardLikeVO {
	private int boardLikeIdx;
	private int userIdx;
	private int boardIdx;
	
	private AuthVO authVO;
	private BoardVO boardVO;
	
	public BoardLikeVO() {}
	
	public BoardLikeVO(int boardLikeIdx, int userIdx, int boardIdx) {
		super();
		this.boardLikeIdx = boardLikeIdx;
		this.userIdx = userIdx;
		this.boardIdx = boardIdx;
	}

	public BoardLikeVO( int userIdx, int boardIdx) {
		super();
		this.userIdx = userIdx;
		this.boardIdx = boardIdx;
	}
	
	public int getBoardLikeIdx() {
		return boardLikeIdx;
	}

	public void setBoardLikeIdx(int boardLikeIdx) {
		this.boardLikeIdx = boardLikeIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	@Override
	public String toString() {
		return "BoardLikeVO [boardLikeIdx=" + boardLikeIdx + ", userIdx=" + userIdx + ", boardIdx=" + boardIdx + "]";
	}
	
}

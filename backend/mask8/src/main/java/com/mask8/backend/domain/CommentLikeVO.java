package com.mask8.backend.domain;

public class CommentLikeVO {
	private int commentLikeIdx;
	private int commentIdx;
	private int userIdx;
	private int boardIdx;

	private AuthVO authVO;
	private CommentVO commentVO;

	public CommentLikeVO() {
		super();
	}
	
	

	public CommentLikeVO(int commentLikeIdx, int commentIdx, int userIdx, int boardIdx, AuthVO authVO, CommentVO commentVO) {
		super();
		this.commentLikeIdx = commentLikeIdx;
		this.commentIdx = commentIdx;
		this.userIdx = userIdx;
		this.boardIdx = boardIdx;
		this.authVO = authVO;
		this.commentVO = commentVO;
	}
	public CommentLikeVO(int commentIdx, int userIdx, int boardIdx) {
		super();
		this.commentIdx = commentIdx;
		this.userIdx = userIdx;
		this.boardIdx = boardIdx;
	}

	public int getCommentLikeIdx() {
		return commentLikeIdx;
	}

	public void setCommentLikeIdx(int commentLikeIdx) {
		this.commentLikeIdx = commentLikeIdx;
	}

	public int getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(int commentIdx) {
		this.commentIdx = commentIdx;
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

	public AuthVO getAuthVO() {
		return authVO;
	}

	public void setAuthVO(AuthVO authVO) {
		this.authVO = authVO;
	}

	public CommentVO getCommentVO() {
		return commentVO;
	}

	public void setCommentVO(CommentVO commentVO) {
		this.commentVO = commentVO;
	}

	@Override
	public String toString() {
		return "CommentLikeVO [commentLikeIdx=" + commentLikeIdx + ", commentIdx=" + commentIdx + ", userIdx=" + userIdx
				+ ", boardIdx=" + boardIdx + ", authVO=" + authVO + ", commentVO=" + commentVO + "]";
	}

}

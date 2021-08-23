package com.mask8.backend.domain;

public class CommentVO {
	private int commentIdx;
	private int userIdx;
	private String commentContent;
	private String uploadDate;
	private String updateDate;
	private int commentLike;
	private int boardIdx;
	private int commentDelete;
	
	private AuthVO authVO;

	public CommentVO() {
		super();
	}

	public CommentVO(int commentIdx, int userIdx, String commentContent, String uploadDate, String updateDate,
			int commentLike, int boardIdx, int commentDelete, AuthVO authVO) {
		super();
		this.commentIdx = commentIdx;
		this.userIdx = userIdx;
		this.commentContent = commentContent;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		this.commentLike = commentLike;
		this.boardIdx = boardIdx;
		this.commentDelete = commentDelete;
		this.authVO = authVO;
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

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getCommentLike() {
		return commentLike;
	}

	public void setCommentLike(int commentLike) {
		this.commentLike = commentLike;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public int getCommentDelete() {
		return commentDelete;
	}

	public void setCommentDelete(int commentDelete) {
		this.commentDelete = commentDelete;
	}

	public AuthVO getAuthVO() {
		return authVO;
	}

	public void setAuthVO(AuthVO authVO) {
		this.authVO = authVO;
	}

	@Override
	public String toString() {
		return "CommentVO [commentIdx=" + commentIdx + ", userIdx=" + userIdx + ", commentContent=" + commentContent
				+ ", uploadDate=" + uploadDate + ", updateDate=" + updateDate + ", commentLike=" + commentLike
				+ ", boardIdx=" + boardIdx + ", commentDelete=" + commentDelete + ", authVO=" + authVO + "]";
	}
	
	
}

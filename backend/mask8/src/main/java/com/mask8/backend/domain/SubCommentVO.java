package com.mask8.backend.domain;

public class SubCommentVO {
	private int subCommentIdx;
	private int userIdx;
	private int commentIdx;
	private int boardIdx;
	private String subCommentContent;
	private String uploadDate;
	private String updateDate;
	private int subCommentDelete;

	private AuthVO authVO;

	public SubCommentVO() {
		super();
	}

	public SubCommentVO(int subCommentIdx, int userIdx, int commentIdx, int boardIdx, String subCommentContent,
			String uploadDate, String updateDate, int subCommentDelete, AuthVO authVO) {
		super();
		this.subCommentIdx = subCommentIdx;
		this.userIdx = userIdx;
		this.commentIdx = commentIdx;
		this.boardIdx = boardIdx;
		this.subCommentContent = subCommentContent;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		
		this.subCommentDelete = subCommentDelete;
		this.authVO = authVO;
	}

	public int getSubCommentIdx() {
		return subCommentIdx;
	}

	public void setSubCommentIdx(int subCommentIdx) {
		this.subCommentIdx = subCommentIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(int commentIdx) {
		this.commentIdx = commentIdx;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getSubCommentContent() {
		return subCommentContent;
	}

	public void setSubCommentContent(String subCommentContent) {
		this.subCommentContent = subCommentContent;
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

	public int getSubCommentDelete() {
		return subCommentDelete;
	}

	public void setSubCommentDelete(int subCommentDelete) {
		this.subCommentDelete = subCommentDelete;
	}

	public AuthVO getAuthVO() {
		return authVO;
	}

	public void setAuthVO(AuthVO authVO) {
		this.authVO = authVO;
	}

	@Override
	public String toString() {
		return "SubCommentVO [subCommentIdx=" + subCommentIdx + ", userIdx=" + userIdx + ", commentIdx=" + commentIdx
				+ ", boardIdx=" + boardIdx + ", subCommentContent=" + subCommentContent + ", uploadDate=" + uploadDate
				+ ", updateDate=" + updateDate + ", subCommentDelete=" + subCommentDelete + ", authVO=" + authVO + "]";
	}

}

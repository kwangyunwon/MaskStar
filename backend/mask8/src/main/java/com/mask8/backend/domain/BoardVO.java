package com.mask8.backend.domain;

public class BoardVO {
	private int boardIdx;
	private String boardVideo;
	private String boardTitle;
	private String boardSinger;
	private int boardGenre;
	private int boardCount;
	private int boardLike;
	private String boardContent;
	private String boardLink;
	private String uploadDate;
	private String updateDate;

	private AuthVO authVO;
	private int userIdx;

	public BoardVO() {
	}

	public BoardVO(int boardIdx, String boardVideo, String boardTitle, String boardSinger, int boardGenre,
			String boardLink, String uploadDate, AuthVO authVO, int userIdx) {
		super();
		this.boardIdx = boardIdx;
		this.boardVideo = boardVideo;
		this.boardTitle = boardTitle;
		this.boardSinger = boardSinger;
		this.boardGenre = boardGenre;
		this.boardLink = boardLink;
		this.uploadDate = uploadDate;
		this.authVO = authVO;
		this.userIdx = userIdx;
	}

	public BoardVO(int boardIdx, String boardVideo, String boardTitle, String boardSinger, int boardGenre,
			String boardContent, String boardLink, String uploadDate, AuthVO authVO, int userIdx) {
		super();
		this.boardIdx = boardIdx;
		this.boardVideo = boardVideo;
		this.boardTitle = boardTitle;
		this.boardSinger = boardSinger;
		this.boardGenre = boardGenre;
		this.boardContent = boardContent;
		this.boardLink = boardLink;
		this.uploadDate = uploadDate;
		this.authVO = authVO;
		this.userIdx = userIdx;
	}

	public BoardVO(int boardIdx, String boardVideo, String boardTitle, String boardSinger, int boardGenre,
			int boardCount, int boardLike, String boardContent, String boardLink, String uploadDate, AuthVO authVO,
			int userIdx) {
		super();
		this.boardIdx = boardIdx;
		this.boardVideo = boardVideo;
		this.boardTitle = boardTitle;
		this.boardSinger = boardSinger;
		this.boardGenre = boardGenre;
		this.boardCount = boardCount;
		this.boardLike = boardLike;
		this.boardContent = boardContent;
		this.boardLink = boardLink;
		this.uploadDate = uploadDate;
		this.authVO = authVO;
		this.userIdx = userIdx;
	}

	public BoardVO(int boardIdx, String boardVideo, String boardTitle, String boardSinger, int boardGenre,
			int boardCount, int boardLike, String boardContent, String boardLink, String uploadDate, String updateDate,
			AuthVO authVO, int userIdx) {
		super();
		this.boardIdx = boardIdx;
		this.boardVideo = boardVideo;
		this.boardTitle = boardTitle;
		this.boardSinger = boardSinger;
		this.boardGenre = boardGenre;
		this.boardCount = boardCount;
		this.boardLike = boardLike;
		this.boardContent = boardContent;
		this.boardLink = boardLink;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		this.authVO = authVO;
		this.userIdx = userIdx;
	}

	public BoardVO(int boardIdx, String boardVideo, String boardTitle, String boardSinger, int boardGenre,
			String boardContent, String boardLink, String uploadDate, int userIdx) {
		super();
		this.boardIdx = boardIdx;
		this.boardVideo = boardVideo;
		this.boardTitle = boardTitle;
		this.boardSinger = boardSinger;
		this.boardGenre = boardGenre;
		this.boardContent = boardContent;
		this.boardLink = boardLink;
		this.uploadDate = uploadDate;
		this.userIdx = userIdx;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getBoardVideo() {
		return boardVideo;
	}

	public void setBoardVideo(String boardVideo) {
		this.boardVideo = boardVideo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardSinger() {
		return boardSinger;
	}

	public void setBoardSinger(String boardSinger) {
		this.boardSinger = boardSinger;
	}

	public int getBoardGenre() {
		return boardGenre;
	}

	public void setBoardGenre(int boardGenre) {
		this.boardGenre = boardGenre;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public int getBoardLike() {
		return boardLike;
	}

	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardLink() {
		return boardLink;
	}

	public void setBoardLink(String boardLink) {
		this.boardLink = boardLink;
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

	public AuthVO getAuthVO() {
		return authVO;
	}

	public void setAuthVO(AuthVO authVO) {
		this.authVO = authVO;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	@Override
	public String toString() {
		return "BoardVO [boardIdx=" + boardIdx + ", boardVideo=" + boardVideo + ", boardTitle=" + boardTitle
				+ ", boardSinger=" + boardSinger + ", boardGenre=" + boardGenre + ", boardCount=" + boardCount
				+ ", boardLike=" + boardLike + ", boardContent=" + boardContent + ", boardLink=" + boardLink
				+ ", uploadDate=" + uploadDate + ", updateDate=" + updateDate + ", userVO=" + authVO + ", userIdx="
				+ userIdx + "]";
	}

}

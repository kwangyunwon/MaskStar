package com.mask8.backend.dao;

import java.util.List;

import com.mask8.backend.domain.SubCommentVO;

public interface SubCommentDAO {
	boolean insertSubComment(SubCommentVO subCommentVO);
	boolean updateSubComment(SubCommentVO subCommentVO);
	boolean deleteSubComment(int subCommentIdx);
	List<SubCommentVO> selectAllSubComments(int commentIdx);
}

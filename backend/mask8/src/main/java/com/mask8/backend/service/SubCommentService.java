package com.mask8.backend.service;

import java.util.List;

import com.mask8.backend.domain.SubCommentVO;

public interface SubCommentService {

	boolean insertSubComment(SubCommentVO subCommentVO);

	boolean updateSubComment(SubCommentVO subCommentVO);

	boolean deleteSubComment(int subCommentIdx);

	List<SubCommentVO> selectAllSubComments(int commentIdx);

}

package com.mask8.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mask8.backend.dao.SubCommentDAO;
import com.mask8.backend.domain.SubCommentVO;
import com.mask8.backend.service.SubCommentService;

@Service
@Transactional
public class SubCommentServiceImpl implements SubCommentService {

	@Autowired
	private SubCommentDAO subCommentDAO;

	@Override
	public boolean insertSubComment(SubCommentVO subCommentVO) {
		return subCommentDAO.insertSubComment(subCommentVO);
	}

	@Override
	public boolean updateSubComment(SubCommentVO subCommentVO) {
		return subCommentDAO.updateSubComment(subCommentVO);
	}

	@Override
	public boolean deleteSubComment(int subCommentIdx) {
		return subCommentDAO.deleteSubComment(subCommentIdx);
	}

	@Override
	public List<SubCommentVO> selectAllSubComments(int commentIdx) {
		return subCommentDAO.selectAllSubComments(commentIdx);
	}

}

package com.samil.dc.service.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcSupportDAO;
import com.samil.dc.domain.UserSearchListBean;
import com.samil.dc.util.Constants;

/**
 * 임직원 검색
 *
 */
public class DcUserSearchServiceWorker extends AbstractServiceWorker {

	public DcUserSearchServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 사번/이름 둘 중에 하나는 값이 있어야 함
		String empNo = ServiceHelper.getParameter(request, "empno");
		String empNm = ServiceHelper.getParameter(request, "empnm");
		if (StringUtils.isBlank(empNo) && StringUtils.isBlank(empNm)) {
			validationResult = new ValidationResult(Constants.ErrorCode.EMPTY_PARAMETER, Constants.ErrorMessage.EMPTY_PARAMETER);
			validationResult.setErrorParameterAndValue("empno or empnm", "");
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		// 파라미터 조합
		String empNo = ServiceHelper.getParameter(request, "empno");
		String empNm = ServiceHelper.getParameter(request, "empnm");

		// 임직원 목록 검색
		DcSupportDAO supportDAO = new DcSupportDAO(connection);
		List<UserSearchListBean> list = new ArrayList<UserSearchListBean>();
		try {
			list = supportDAO.sqlSelectUserSearchList(empNo, empNm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);

		return data;
	}
}

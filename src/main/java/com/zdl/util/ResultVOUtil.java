package com.zdl.util;

import com.zdl.VO.ResultVO;
/**
 * 进一步处理返回的视图
 * @author DELL
 *
 */
public class ResultVOUtil {
	//成功
	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO();
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}

	public static ResultVO success() {
		return success(null);
	}

	// 失败
	public static ResultVO error(Integer code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}

}

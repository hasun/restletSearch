package com.mycompany.restlet.search.model;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 결과를 담는 객체로 HashMap의 형태로 저장
 * 
 * @author 윤용승 e-mail: ysyun@saltlux.com
 * @since 2011. 10. 22.
 */
public class ResultMap extends HashMap<String, Object> implements Map<String, Object>
{
	/**
	 * 자동 생성된 ID
	 */
	private static final long serialVersionUID = -6736255739069656880L;

	/**
	 * 결과를 XML 스트링 값으로 반환
	 * 
	 * @return
	 */
	public String toXMLString()
	{
		if (entrySet() == null)
		{
			return "";
		}

		XStream xstream = new XStream(new DomDriver("UTF-8"));

		return xstream.toXML(this);
	}
}

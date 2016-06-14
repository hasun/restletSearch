package com.mycompany.restlet.search.model;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ����� ��� ��ü�� HashMap�� ���·� ����
 * 
 * @author ����� e-mail: ysyun@saltlux.com
 * @since 2011. 10. 22.
 */
public class ResultMap extends HashMap<String, Object> implements Map<String, Object>
{
	/**
	 * �ڵ� ������ ID
	 */
	private static final long serialVersionUID = -6736255739069656880L;

	/**
	 * ����� XML ��Ʈ�� ������ ��ȯ
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

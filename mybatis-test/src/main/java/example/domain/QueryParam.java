/*
 * Copyright 1999-2014 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package example.domain;


/**
 * ��QueryParam.java��ʵ��������ͨ�õĲ�ѯ�࣬������ҳ������������Ĳ���
 * @author xueliang.cxl 2017��1��22�� ����10:46:51
 */
public class QueryParam  {

	private static final long  serialVersionUID	 = 3936618265149996330L;

	private static final int   DEFAULT_PAGE_SIZE = 10;

	public static final String ASC				 = "ASC";
	public static final String DESC				 = "DESC";

	/** ҳ��С-Ĭ��10�� */
	private int				   pageSize			 = DEFAULT_PAGE_SIZE;

	/** ��ǰҳ�� */
	private int				   pageStart		 = 1;

	/** �����ֶ� */
	private String			   orderBy;

	/** ������ */
//	@Pattern(regexp = "^(ASC|DESC)$", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String			   orderDirection	 = DESC;
	
	/** group�ֶ� */
    private String             groupBy;

	public int getPageStart() {
		if (pageStart < 1) {
			return 1;
		}
		return pageStart;
	}

	public QueryParam setPageStart(int pageStart) {
		this.pageStart = pageStart;
		return this;
	}

	public int getPageSize() {
		if (pageSize < 1) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public QueryParam setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
		return this;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public QueryParam setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public QueryParam setOrderDirection(String orderDirection) {
		if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
			this.orderDirection = orderDirection;
		}
		return this;
	}

	/**
	 * This method use for dal layer.
	 *
	 * limit #offset# #pageSize#
	 *
	 * @return
	 */
	public int getOffset() {
		return (getPageStart() - 1) * getPageSize();
	}

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

}

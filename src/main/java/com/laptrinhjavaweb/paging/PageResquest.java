package com.laptrinhjavaweb.paging;

public class PageResquest implements Pageable {
	private Integer page;
	private Integer limit;

	public PageResquest(Integer page, Integer limit) {
		super();
		this.page = page;
		this.limit = limit;
	}

	@Override
	public Integer getPage() {
		return this.page;
	}

	@Override
	public Integer getOffset() {
		return (this.page - 1) * this.limit;
	}

	@Override
	public Integer getLimit() {
		return this.limit;
	}

}

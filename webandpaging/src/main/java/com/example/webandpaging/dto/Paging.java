package com.example.webandpaging.dto;


public record Paging(
		int pageNumber,
		int pageSize,
		int totalPage,
		boolean hasPrevious,
		boolean hasNext) {

	public Paging(int pageNumber, int pageSize, int totalPage,
				  boolean hasPrevious, boolean hasNext) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.hasPrevious =  hasPrevious;
		this.hasNext = hasNext;
	}

}

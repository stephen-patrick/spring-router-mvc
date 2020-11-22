package com.stephenenright.spring.router.mvc.constraint;

import java.util.Arrays;
import java.util.List;

import com.stephenenright.spring.router.mvc.RouteDetail;
import com.stephenenright.spring.router.mvc.Http.HttpRequestWrapper;

public class DomainEqualsConstraint implements RouteConstraint {

	private List<String> domainsToMatch;

	public DomainEqualsConstraint(String... domainsToMatch) {
		this(Arrays.asList(domainsToMatch));
	}

	public DomainEqualsConstraint(List<String> domainsToMatch) {
		this.domainsToMatch = domainsToMatch;
	}

	public boolean match(HttpRequestWrapper request, RouteDetail route) {
		for (String domain : domainsToMatch) {
			if (domain.equalsIgnoreCase(request.getHost())) {
				return true;
			}

		}
		return false;
	}

}

package com.spell.checker.service;

import java.util.List;

public interface ISpellCheckService {
	public List<String> doSearch(final String toSearch);
}

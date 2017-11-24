package com.spell.checker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class FuzzySearchService extends AbstractSpellCheckService {
	private static String DefaultSampleDataFilePath = "../PhoneticSearch/src/main/resources/sample_data.txt";
	private final List<String> sampledataArray;
	private final int limit;

	public FuzzySearchService(final String sampleDataFilePath, final int limit) throws IOException {
		final Path path = Paths.get(!empty(sampleDataFilePath) ? sampleDataFilePath : DefaultSampleDataFilePath);
		this.sampledataArray = Files.readAllLines(path);
		this.limit = limit;
	}

	@Override
	public List<String> doSearch(final String toSearch) {
		List<ExtractedResult> results = FuzzySearch.extractTop(toSearch, sampledataArray, this.limit);
		if (null != results && !results.isEmpty()) {
			return results.stream().map(r -> r.getString()).collect(Collectors.toList());
		}
		return null;
	}

}

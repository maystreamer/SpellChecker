package com.spell.checker.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneSpellCheckService extends AbstractSpellCheckService {
	private int suggestionsNumber = 1; // Change this value to get multiple Results
	private IndexWriterConfig config;
	private final File sampleDatafile;
	private final Directory directory;
	private static String DefaultDirectoryPath = "../PhoneticSearch/src/main/resources/lucene_dir/";
	private static String DefaultSampleDataFilePath = "../PhoneticSearch/src/main/resources/sample_data.txt";

	public LuceneSpellCheckService(final String directoryPath, final String sampleDataFilePath, final int suggestionsNumber) throws IOException {
		final File file = new File(!empty(directoryPath) ? directoryPath : DefaultDirectoryPath);
		this.directory = FSDirectory.open(file);
		this.sampleDatafile = new File(!empty(sampleDataFilePath) ? sampleDataFilePath : DefaultSampleDataFilePath);
		this.suggestionsNumber = suggestionsNumber;
	}

	@Override
	public List<String> doSearch(final String toSearch) {
		List<String> suggestedWords = null;
		final SpellChecker spellChecker;
		try {
			spellChecker = new SpellChecker(directory);
			spellChecker.indexDictionary(new PlainTextDictionary(this.sampleDatafile), getConfig(), false);
			final String[] suggestions = spellChecker.suggestSimilar(toSearch, suggestionsNumber);
			if (suggestions != null && suggestions.length > 0) {
				suggestedWords = new ArrayList<String>();
				for (String word : suggestions) {
					suggestedWords.add(word);
				}
			}
		} catch (IOException ix) {
			ix.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return suggestedWords;
	}

	public IndexWriterConfig getConfig() {
		if (null == config) {
			return new IndexWriterConfig(Version.LUCENE_36, new WhitespaceAnalyzer(Version.LUCENE_36));
		}
		return config;
	}

	public void setConfig(IndexWriterConfig config) {
		this.config = config;
	}
}
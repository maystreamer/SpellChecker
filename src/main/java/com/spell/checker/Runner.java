package com.spell.checker;

import java.io.IOException;
import java.util.List;

import com.spell.checker.service.FuzzySearchService;
import com.spell.checker.service.ISpellCheckService;
import com.spell.checker.service.LuceneSpellCheckService;

public class Runner {

	public static void main(String[] args) {
		// you can try with eyecloud.com, ipcloud.com
		//for eyecloud.com : Lucene is the clear winner
		final String wordToSearch = "jcloud.com";
		ISpellCheckService service = null;
		List<String> response = null;
		try {
			/**
			 * here we are using Lucene to search suggested words 
			 * passing null to add default directory and sample data file 
			 * passing 1 as suggestion number. 
			 * You can increase the value for multiple results
			 */
			System.out.println(" =========== START Search using Lucene =========== ");
			service = new LuceneSpellCheckService(null, null, 1);
			response = service.doSearch(wordToSearch);
			doPrint(response, wordToSearch);
			System.out.println(" =========== END Search using Lucene   =========== ");

			/**
			 * here we are using fuzzyWuzzy to search suggested words 
			 * passing null to add default sample data file
			 * passing 3 as limit to search for
			 */
			System.out.println("");
			System.out.println("");
			System.out.println(" =========== START Search using FuzzyWuzzy =========== ");
			service = new FuzzySearchService(null, 1);
			response = service.doSearch(wordToSearch);
			doPrint(response, wordToSearch);
			System.out.println(" =========== END Search using FuzzyWuzzy   =========== ");

		} catch (IOException ix) {
			ix.printStackTrace();
		}
	}

	private static void doPrint(final List<String> results, final String wordToSearch) {
		if (null != results && !results.isEmpty()) {
			for (String data : results) {
				System.out.println("Did you mean -> " + data);
			}
		} else {
			System.out.println("No suggestions found for word:" + wordToSearch);
		}
	}

}

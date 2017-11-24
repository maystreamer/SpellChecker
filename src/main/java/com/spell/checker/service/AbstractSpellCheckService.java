package com.spell.checker.service;

public abstract class AbstractSpellCheckService implements ISpellCheckService {

	
	//TODO: Util class or use apache lang 3
	public static boolean empty(final String s) {
		return s == null || s.trim().isEmpty();
	}
}

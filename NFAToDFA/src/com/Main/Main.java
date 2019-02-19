package com.Main;

import java.io.*;

import com.Logic.NFAToDFAConverter;

public class Main {
	private static NFAToDFAConverter nfaToDfaConverter = new NFAToDFAConverter();

	public static void main(String[] args) throws IOException {
		System.out.println("Converting NFA to DFA");
		nfaToDfaConverter.convertNFAToDFA();
	}
}

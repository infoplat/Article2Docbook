package com.suyuening.article.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public final class FileUtil {
	private FileUtil() {
	}

	public static List<String> readFile2List(String inputFile) {
		checkNotNull(inputFile, "inputFile cant't be null");
		File file = new File(inputFile);
		try {
			return Files.readLines(file, Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeFileFromList(List<String> fileLines,
			String outputFile) {
		checkNotNull(fileLines, "fileLines cant't be null");
		checkNotNull(outputFile, "outputFile cant't be null");
		File file = new File(outputFile);
		file.delete();
		try {
			file.createNewFile();
			for (String line : fileLines) {
				Files.append(String.format("%s\r\n", line), file, Charsets.UTF_8);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

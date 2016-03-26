package io.vedder.ml.markov.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Utils {
	public static void writeToFile(String filePath, String contents) {
		try {
			File file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contents);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readFile(String filePath) {
		List<String> lines = new LinkedList<>();
		try {
			Files.lines(new File(filePath).toPath()).forEach(l -> lines.add(l + "\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}

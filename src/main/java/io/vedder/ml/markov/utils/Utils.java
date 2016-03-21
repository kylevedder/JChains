package io.vedder.ml.markov.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}

package a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(TTT.class);
		logger.error("error is {}, {}", "dddd", "eeee", new Exception("xxxxxxxxxxxxxxxxxxxxxxxx"));
	}

}

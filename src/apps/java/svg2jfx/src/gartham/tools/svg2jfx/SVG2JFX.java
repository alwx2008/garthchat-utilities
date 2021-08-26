package gartham.tools.svg2jfx;

import org.alixia.javalibrary.streams.CharacterStream;
import org.alixia.javalibrary.streams.PeekableCharacterStream;

public class SVG2JFX {

	private static PeekableCharacterStream in;

	private static void parseWhitespace() {
		while (in.peek() != -1 && (Character.isWhitespace(in.peek()) || in.peek() == ','))
			in.next();
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			in = PeekableCharacterStream.from(CharacterStream.from(args[0]));// Set up the CharStream object.
			while (in.peek() != -1) {

			}
		} else {
			System.out.println("Invalid number of arguments. Please provide the SVG Path as a single argument.");
		}
	}

	private static String parsePathElement() {

	}

}

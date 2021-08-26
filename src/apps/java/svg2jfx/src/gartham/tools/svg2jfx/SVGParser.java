package gartham.tools.svg2jfx;

import zeale.mouse.utils.BufferedParser;
import zeale.mouse.utils.CharacterParser;
import zeale.mouse.utils.Parser;

public class SVGParser extends BufferedParser<PathElement> {

	private final CharacterParser in;

	public SVGParser(CharacterParser in) {
		this.in = in;
	}

	public SVGParser(Parser<Character> in) {
		this(CharacterParser.from(in));
	}

	/**
	 * Parses the next {@link PathElement}, returning <code>null</code> if the end
	 * of input has been reached or an error if there is otherwise malformed input.
	 */
	@Override
	protected PathElement read() {
		in.parseWhitespace();
		switch (in.pk()) {
		case -1:
			return null;
		default:
			throw new IllegalArgumentException("Unexpected value: " + in.nxt());
		}
//		return null;
	}

}

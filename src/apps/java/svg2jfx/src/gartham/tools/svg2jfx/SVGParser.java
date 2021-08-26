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

	@Override
	protected PathElement read() {
		return null;
	}

}

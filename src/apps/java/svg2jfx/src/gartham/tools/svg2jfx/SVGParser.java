package gartham.tools.svg2jfx;

import java.text.DecimalFormat;

import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import zeale.mouse.utils.BufferedParser;
import zeale.mouse.utils.CharacterParser;
import zeale.mouse.utils.Parser;

public class SVGParser extends BufferedParser<PathElement> {

	private static final DecimalFormat df = new DecimalFormat("#.################################");

	private String varname = "p";

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

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
		int c;
		switch (c = in.nxt()) {
		case -1:
			return null;
		case 'M':
		case 'm': {

			in.parseWhitespace();
			double val = parseValue();
			parseSeparator();
			double val2 = parseValue();

			String v1 = df.format(val), v2 = df.format(val2);
			return new PathElement() {
				@Override
				public String toSVGPart() {
					return c + v1 + "," + v2;
				}

				@Override
				public String toJavaFXStatement() {
					return c == 'M'
							? "{\n\tMoveTo moveTo = new MoveTo(" + v1 + ", " + v2
									+ ");\n\tmoveTo.setAbsolute(true);\n\t" + varname + ".add(moveTo);\n}"
							: varname + ".add(new MoveTo(" + v1 + ", " + v2 + "));";
				}

				@Override
				public javafx.scene.shape.PathElement toJavaFX() {
					MoveTo m = new MoveTo(val, val2);
					if (c == 'M')
						m.setAbsolute(true);
					return m;
				}
			};
		}

		case 'L':
		case 'l': {
			in.parseWhitespace();
			double val = parseValue();
			parseSeparator();
			double val2 = parseValue();

			String v1 = df.format(val), v2 = df.format(val2);
			return new PathElement() {

				@Override
				public String toSVGPart() {
					return c + v1 + ',' + v2;
				}

				@Override
				public String toJavaFXStatement() {
					return c == 'L'
							? "{\n\tLineTo lineTo = new LineTo(" + v1 + ", " + v2
									+ ");\n\tlineTo.setAbsolute(true);\n\t" + varname + ".add(lineTo);\n}"
							: varname + ".add(new LineTo(" + v1 + ", " + v2 + "));";
				}

				@Override
				public javafx.scene.shape.PathElement toJavaFX() {
					LineTo lt = new LineTo(val, val2);
					if (c == 'L')
						lt.setAbsolute(true);
					return lt;
				}
			};
		}

		case 'H':
		case 'h':
			in.parseWhitespace();
			double val = parseValue();

			String v = df.format(val);
			return new PathElement() {

				@Override
				public String toSVGPart() {
					return c + v;
				}

				@Override
				public String toJavaFXStatement() {
					return c == 'H'
							? "{\n\tHLineTo hLineTo = new LineTo(" + v + ");\n\thLineTo.setAbsolute(true);\n\t"
									+ varname + ".add(hLineTo);\n}"
							: varname + ".add(new HLineTo(" + v + "));";
				}

				@Override
				public javafx.scene.shape.PathElement toJavaFX() {
					HLineTo hlt = new HLineTo(val);
					if (c == 'H')
						hlt.setAbsolute(true);
					return hlt;
				}
			};
		default:
			throw new IllegalArgumentException("Unexpected value: " + in.nxt());
		}
//		return null;
	}

	private void parseSeparator() {
		in.parseWhitespace();
		if (in.pk() == ',')
			in.nxt();
		in.parseWhitespace();
	}

	private double parseValue() {
		in.parseWhitespace();
		String str = "";
		if (in.pk() == '-') {
			str += '-';
			in.nxt();
		}
		str += in.clct(Character::isDigit);
		if (in.pk() == '.') {
			str += '.';
			in.nxt();
			str += in.clct(Character::isDigit);
		} else if (str.isEmpty())
			throw new RuntimeException("Malformed input to SVGParser.");
		return Double.parseDouble(str);

	}

}

#include "BacktrackLexer.h"

std::string t::BacktrackLexer::tokenNames[] = {"n/a","EOF","NMAE","[","]",",","="};

const std::string& t::Token::toString() {
	return std::string("type: ").append(BacktrackLexer::tokenNames[type]).append(" value: ").append(value);
}


bool t::BacktrackLexer::match(int i) {
	if (token->type == i)
		return true;
	else {
		std::cout << "expecting " + tokenNames[i] << "; found " + tokenNames[token->type] << std::endl;
		return false;
	}
}

void t::BacktrackLexer::advance() {
	p++;
	if (p >= input.length()) ch = EOF;
	else ch = input.at(p);
}

void t::BacktrackLexer::consume() {
	advance();
}

t::Token* t::BacktrackLexer::nextToken() {
	while (ch != EOF) {
		if (ch == '\t' || ch == '\n' || ch == ' ' || ch == '\r')
			consume();
		else if (ch == '[') {
			consume();
			return new Token(LBRACK, tokenNames[LBRACK]);
		}
		else if (ch == ']') {
			consume();
			return new Token(RBRACK, tokenNames[RBRACK]);
		}
		else if (ch == ',') {
			consume();
			return new Token(COMMA, tokenNames[COMMA]);
		}
		else if (ch == '=') {
			consume();
			return new Token(EQUALS, tokenNames[EQUALS]);
		}
		else if (isLetter()) {
			return name();
		}
		else {
			std::cout << "invalid character " << ch << std::endl;
			break;
		}
	}
	return new Token(EOF_TYPE, tokenNames[EOF_TYPE]);
}

bool t::BacktrackLexer::isLetter() {
	return ((ch >= 'a'&&ch <= 'z') || (ch >= 'A'&&ch <= 'Z'));
}

t::Token* t::BacktrackLexer::name() {
	std::string str;
	while (isLetter()) {
		str += ch;
		consume();
	}
		
	return new Token(NAME, str);
}
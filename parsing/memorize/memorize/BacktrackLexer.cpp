#include "BacktrackLexer.h"
#include <iostream>
const std::string tina::BacktrackLexer::tokenNames[] = { "n/a","EOF","COMMA","LBRACK","RBRACK","EQUALS","NAME" };

void tina::Token::toString() {
	std::cout << "Type: " << BacktrackLexer::tokenNames[type] << " -> Value: " << value << std::endl;
}

void tina::BacktrackLexer::consume() {
	p++;
	if (p >= input.length()) ch = EOF;
	else ch = input.at(p);
}

tina::Token* tina::BacktrackLexer::nextToken() {
	while (ch != EOF) {
		if (ch == '\n' || ch == '\t' || ch == ' ' || ch == '\r')
			consume();
		else if (ch == ',') {
			consume();
			return new Token(BacktrackLexer::COMMA, tokenNames[BacktrackLexer::COMMA]);
		}
		else if (ch == '[') {
			consume();
			return new Token(BacktrackLexer::LBRACK, tokenNames[BacktrackLexer::LBRACK]);
		}
		else if (ch == ']') {
			consume();
			return new Token(BacktrackLexer::RBRACK, tokenNames[BacktrackLexer::RBRACK]);
		}
		else if (ch == '=') {
			consume();
			return new Token(BacktrackLexer::EQUALS, tokenNames[BacktrackLexer::EQUALS]);
		}
		else if (isLetter())
			return name();

	}
	return new Token(BacktrackLexer::EOF_TYPE, tokenNames[BacktrackLexer::EOF_TYPE]);
}

bool tina::BacktrackLexer::isLetter() {
	if ((ch >= 'a'&&ch <= 'z') || (ch >= 'A'&&ch <= 'Z'))
		return true;
	return false;
}

tina::Token* tina::BacktrackLexer::name() {
	std::string str;
	while (isLetter()) {
		str += ch;
		consume();
	}
	return new Token(BacktrackLexer::NAME, str);
}
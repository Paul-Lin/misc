#include "lexer.h"

std::string& tina::Token::toString() {
	std::string tname(Lexer::tokenNames[type]);
	return std::string("<").append(text).append(",").append(tname).append(">");
}

void tina::Lexer::advance() {
	i++;
	if (i >= input.length()) c = EOF;
	else c = input.at(i);
}

void tina::Lexer::consume() {
	advance();
}

void tina::Lexer::match(char x) {
	if (c == x)consume();
	else throw std::runtime_error::runtime_error(std::string("excepting " + c).append("; found" + c).c_str());
}

std::string tina::Lexer::tokenNames[] = {"n/a","<EOF>","NAME",",","[","]","="};

bool tina::Lexer::isLetter() {
	return (c >= 'a'&&c <= 'z') || (c >= 'A'&&c <= 'Z');
}

tina::Token* tina::Lexer::nextToken() {
	while (c != EOF) {
		if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			advance();
		}
		else if(c==','){
			consume();
			return new  Token(COMMA, std::string(","));
		}
		else if (c == '[') {
			consume();
			return new Token(LBRACK, std::string("["));
		}
		else if (c == ']') {
			consume();
			return new Token(RBRACK, std::string("]"));
		}
		else if (c == '=') {
			consume();
			return new Token(EQUALS, std::string("="));
		}
		else {
			if (isLetter()) return name();
			throw std::runtime_error::runtime_error("invalid character: " + c);
		}

	}
	return new Token(EOF_TYPE, std::string("EOF"));
}

tina::Token* tina::Lexer::name() {
	std::string str;
	while (isLetter()){
		str.append(""+c);
		if (isLetter())
			consume();
		else throw std::runtime_error::runtime_error("expecting LETTER; found " + c);
	}
	return new Token(NAME, str);
}
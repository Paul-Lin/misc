#include "list_lexer.h"

void tina::Lexer::consume() {
	this->p++;
	if (p >= this->input.length())
		this->c = EOF;
	else
		this->c = this->input.at(p);
}

void tina::Lexer::match(const char& x) {
	if (this->c == x)
		consume();
	else
		throw std::string("expecting " + x).append("; found "+this->c);
}

const std::string tina::ListLexer::tokenNames[] = {"n/a","<EOF>","NAME","COMMA","LBRACK","RBRACK"};



std::string tina::ListLexer::getTokenName(int x) {
	return tokenNames[x];
}

bool tina::ListLexer::isLetter() {
	return this->c >= 'a'&&this->c <= 'z' || this->c >= 'A'&&this->c <= 'Z';
}

tina::Token tina::ListLexer::nextToken() {
	while (this->c != EOF) {
		if (this->c == '[') {
			consume();
			return Token(LBRACK, tokenNames[LBRACK]);
		}
		else if (this->c == ']') {
			consume();
			return Token(RBRACK, tokenNames[RBRACK]);
		}
		else if (this->c == ' '||this->c =='\n'||this->c=='\t'||this->c=='\r') {
			consume();
			continue;
		}
		else if (this->c == ',') {
			consume();
			return Token(COMMA, tokenNames[COMMA]);
		}
		else {
			if (isLetter()) {
				std::string str;
				do {
					str+=c;
					consume();
				} while (isLetter());
				return Token(NAME,str);
			}
			throw std::string("invalid character: " + c);
				
		}
			
	}
	return Token(EOF_TYPE, tokenNames[EOF_TYPE]);
}
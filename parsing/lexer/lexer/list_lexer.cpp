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

const std::string tina::ListLexer::tokenNames[] = {"n/a","<EOF>","NAME","COMMA","LBRACK","RBRACK","EQUALS"};



std::string tina::ListLexer::getTokenName(int x) {
	return tokenNames[x];
}

bool tina::ListLexer::isLetter() {
	return this->c >= 'a'&&this->c <= 'z' || this->c >= 'A'&&this->c <= 'Z';
}

tina::Token* tina::ListLexer::nextToken() {
	while (this->c != EOF) {
		if (this->c == '[') {
			consume();
			return new Token(LBRACK, tokenNames[LBRACK]);
		}
		else if (this->c == ']') {
			consume();
			return new Token(RBRACK, tokenNames[RBRACK]);
		}
		else if (this->c == ' '||this->c =='\n'||this->c=='\t'||this->c=='\r') {
			consume();
			continue;
		}
		else if (this->c == ',') {
			consume();
			return new Token(COMMA, tokenNames[COMMA]);
		}
		else if (this->c == '=') {
			consume();
			return new Token(EQUALS, tokenNames[EQUALS]);
		}
		else {
			if (isLetter()) {
				std::string str;
				do {
					str+=c;
					consume();
				} while (isLetter());
				return new Token(NAME,str);
			}
			throw "invalid character: " + c;
				
		}
			
	}
	return new Token(EOF_TYPE, tokenNames[EOF_TYPE]);
}

void tina::ListParser::match(int x) {
	if (LA(1) == x)
		consume();
	else
		throw "expecting " + lexer->getTokenName(x) + "; found " + LT(1)->toString();
}

void tina::Parser::consume() {
	lookahead[peek] = lexer->nextToken();
	peek = (peek + 1) % k;
}

void tina::ListParser::list() {
	match(ListLexer::LBRACK);
	elements();
	match(ListLexer::RBRACK);
}

void tina::ListParser::elements() {
	element();
	while (LA(1) == ListLexer::COMMA) {
		match(ListLexer::COMMA);
		element();
	}
}

void tina::ListParser::element() {
	if (LA(1) == ListLexer::NAME&&LA(2) == ListLexer::EQUALS) {
		match(ListLexer::NAME);
		match(ListLexer::EQUALS);
		match(ListLexer::NAME);
	}
	else if (LA(1) == ListLexer::NAME) {
		match(ListLexer::NAME);
	}
	else if (LA(1) == ListLexer::LBRACK)
		list();
	else
		throw "expecting name or list; found " + LT(1)->toString();
}

tina::Token* tina::ListParser::LT(int i) {
	return lookahead[(peek + i - 1) % k];
}

int tina::ListParser::LA(int i) {
	return LT(i)->type;
}


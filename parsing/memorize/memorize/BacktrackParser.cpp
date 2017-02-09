#include "BacktrackParser.h"

tina::BacktrackParser::BacktrackParser(tina::BacktrackLexer *lex):lexer(lex) {
	lookahead = new std::vector<Token*>();
	markers = new std::vector<int>();
	sync(1);
}

void tina::BacktrackParser::stat() {

}

void tina::BacktrackParser::list() {
	match(BacktrackLexer::LBRACK);
	match(BacktrackLexer::RBRACK);
}

int tina::BacktrackParser::mark() {
	markers->push_back(marker);
	return marker;
}

void tina::BacktrackParser::sync(int index) {
	int t1 = marker + index - 1;
	int t2 = lookahead->size() - 1;
	if (t1 > t2) {
		int n = t1 - t2;
		fill(index);
	}
}

void tina::BacktrackParser::fill(int index) {
	for (int i = 1; i <= index; i++)
		lookahead->push_back(lexer->nextToken());
}

tina::Token* tina::BacktrackParser::token(int index) {
	sync(index);
	return lookahead->at(marker + index - index);
}
void tina::BacktrackParser::release() {
	marker = markers->back();
	markers->pop_back();
}

int tina::BacktrackParser::type(int type) {
	
}

bool tina::BacktrackParser::match(int type) {
	if (lookahead->at[type]->type == type) {
		consume();
		return true;
	}
	return false;
}

void tina::BacktrackParser::consume() {

}
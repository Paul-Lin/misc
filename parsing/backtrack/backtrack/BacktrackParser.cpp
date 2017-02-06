#include "BacktrackParser.h"

t::BacktrackParser::BacktrackParser(BacktrackLexer* le) :lexer(le) {
	markers = new std::vector<int>();
	lookahead = new std::vector<Token*>();
	sync(1);
}

void t::BacktrackParser::mark() {
	markers->push_back(p);
	
}

void t::BacktrackParser::release() {
	p = markers->back();
	markers->pop_back();
}

t::Token* t::BacktrackParser::LT(int i) {
	sync(i);
	return lookahead->at(p+i-1);
}

int t::BacktrackParser::LA(int i) {
	return LT(i)->type;
}

void t::BacktrackParser::sync(int i) {
	int t1 = p + i - 1;
	int t2 = lookahead->size() - 1;
	if (t1 > t2) {
		int n = t1 - t2;
		for (int i = 1; i <= n; i++) {
			lookahead->push_back(lexer->nextToken());
		}
	}
}

void t::BacktrackParser::consume() {
	p++;
	// ·ÇÍÆ¶Ï×´Ì¬£¬
	if (p == lookahead->size()&&markers->size()<=0) {
		p = 0;
		lookahead->clear();
	}
	sync(1);
}

void t::BacktrackParser::stat() {
	if (specluate_rule_1()) {
		assign();
		match(BacktrackLexer::EOF_TYPE);
		std::cout << "like [a,b]=[c,d]" << std::endl;
	}
}
bool t::BacktrackParser::speculate_rule_2() {
	bool success = true;

	return success;
}

bool t::BacktrackParser::specluate_rule_1() {
	bool success = true;
	mark();
	success = assign() && match(BacktrackLexer::EOF_TYPE);
	release();
	return success;
}
bool t::BacktrackParser::list() {
	return match(BacktrackLexer::LBRACK) && elements() && match(BacktrackLexer::RBRACK);
}

bool t::BacktrackParser::elements() {
	bool success = true;
	success &= element();
	while (LA(1) == BacktrackLexer::COMMA) {
		success&=match(BacktrackLexer::COMMA);
		success&=element();
	}
	return success;
}

bool t::BacktrackParser::element() {

	if (LA(1) == BacktrackLexer::NAME&&LA(2) == BacktrackLexer::EQUALS) {
		return match(BacktrackLexer::NAME) && match(BacktrackLexer::EQUALS) && match(BacktrackLexer::NAME);
	}
	else if (LA(1) == BacktrackLexer::NAME) {
		return match(BacktrackLexer::NAME);
	}
	else if (LA(1) == BacktrackLexer::LBRACK) {
		return list();
	}
	else {
		std::cout << "invalid token " << LT(1)->toString() << std::endl;
		return false;
	}

}
bool t::BacktrackParser::assign() {
	return list() && match(BacktrackLexer::EQUALS) && list();
}

bool t::BacktrackParser::match(int i) {
	if (LA(1) == i) {
		consume();
		return true;
	}
	else {
		std::cout << "expecting " << BacktrackLexer::tokenNames[i] << "; found " << LT(i)->value << std::endl;
		return false;
	}
}
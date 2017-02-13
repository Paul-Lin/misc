#include "MemoryParser.h"

tina::MemoryParser::MemoryParser(BacktrackLexer* input) :lexer(input) { 
	lookahead = new std::vector<Token*>();
	markers = new std::vector<int>();
	sync(1); 
}

void tina::MemoryParser::sync(int index) {
	int t1 = marker + index - 1;
	int t2 = lookahead->size() - 1;
	if (t1 > t2) {
		int t = t1 - t2;
		fill(t);
	}
}

void tina::MemoryParser::fill(int size) {
	for (int i = 1; i <= size; i++) {
		lookahead->push_back(lexer->nextToken());
	}
}

tina::Token* tina::MemoryParser::token(int index) {
	sync(index);
	return lookahead->at(marker + index - 1);
}

int tina::MemoryParser::type(int index) {
	return token(index)->type;
}

bool tina::MemoryParser::match(int ty) {
	if (type(1) == ty) {
		consume();
		return true;
	}
	return false;
}

void tina::MemoryParser::consume() {
	marker++;
	if (marker == lookahead->size() && !(markers->size() > 0)) {
		marker = 0;
		lookahead->clear();
		markers->clear();
	}
	sync(1);
}

void tina::MemoryParser::stat() {
	if (speculate_stat_alt1()) {

	}
	else if (speculate_stat_alt2()) {

	}
	else {
		std::cout << "parsed stat failed" << std::endl;
	}
}

void tina::MemoryParser::mark() {
	markers->push_back(marker);
}

void tina::MemoryParser::release() {
	marker = markers->back();
	markers->pop_back();
}

bool tina::MemoryParser::speculate_stat_alt1() {
	std::cout << "attempt alternative 1" << std::endl;
	bool success = true;
	mark();
	success=list();
	release();
	return success;
}

bool tina::MemoryParser::speculate_stat_alt2() {
	std::cout << "attempt alternative 2" << std::endl;
	bool success = true;
	mark();
	success=assign();
	
	release();
	return success;
}

bool tina::MemoryParser::list() {
	bool success = true;
	int startTokenIndex = marker;
	if (markers->size() > 0 && alreadyParsedRule()) {
		std::cout << "已经解析过了，不再进行解析" << std::endl;
		return true;
	}
	success = _list();
	if (markers->size() > 0)
		memorize(startTokenIndex, success);
	return false;
}

void tina::MemoryParser::memorize(int startTokenIndex,bool failed) {
	int stopTokenIndex = failed ? FAILED : marker;
	memory->insert(std::make_pair(startTokenIndex, stopTokenIndex));
}

bool tina::MemoryParser::_list() {
	return match(BacktrackLexer::LBRACK) && elements() && match(BacktrackLexer::RBRACK);
}

bool tina::MemoryParser::elements() {
	bool success = true;
	success&=element();
	while (match(BacktrackLexer::COMMA)) {
		success&=element();
	}
	return success;
}

bool tina::MemoryParser::element() {
	if (type(1) == BacktrackLexer::NAME&&type(2) == BacktrackLexer::EQUALS) {
		return match(BacktrackLexer::NAME) && match(BacktrackLexer::EQUALS) && match(BacktrackLexer::NAME);
	}
	else if (type(1) == BacktrackLexer::NAME) {
		return match(BacktrackLexer::NAME);
	}
	else if (type(1) == BacktrackLexer::LBRACK) {
		return list();
	}
	else {
		return false;
	}
		
}
bool tina::MemoryParser::alreadyParsedRule() {
	std::map<int, int> memorize = *memory;
	bool success = true;
	int tmp = memorize[marker];
	if (tmp == NULL||tmp==FAILED)
		return false;
	std::cout << "转换列表当前在 【" << marker 
		<< "】;\n 将跳往此位置 【" << tmp << "】:" 
		<< lookahead->at(tmp)->value 
		<< std::endl;
	marker = tmp;
	return true;
}

bool tina::MemoryParser::assign() {
	return list() && match(BacktrackLexer::EQUALS) && list();
}

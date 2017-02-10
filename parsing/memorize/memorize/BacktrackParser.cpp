#include "BacktrackParser.h"

tina::BacktrackParser::BacktrackParser(tina::BacktrackLexer *lex) :lexer(lex) {
	lookahead = new std::vector<Token*>();
	markers = new std::vector<int>();
	sync(1);
}

void tina::BacktrackParser::stat() {

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

int tina::BacktrackParser::type(int index) {
	return token(index)->type;
}

/*
	记录其解析结果
*/
bool tina::BacktrackParser::list() {
	bool isSuccess = true;
	int startTokenIndex = marker;
	if (markers->size() > 0 && alreadyParsedRule(listMemo))
		return;
	if(!_list()){
		isSuccess = false;
	}
	if (markers->size() > 0)
		memorize(list_memo, startTokenIndex, !isSuccess);
}


bool tina::BacktrackParser::_list() {
	std::cout << "parse list rule at token index: " << std::endl;
	return match(BacktrackLexer::LBRACK) && elements() && match(BacktrackLexer::RBRACK);
}

bool tina::BacktrackParser::elements() {
	bool flag = true;
	flag &= element();
	while (match(BacktrackLexer::COMMA)) {
		flag &= element();
		match(BacktrackLexer::COMMA);
	}
	return flag;
}

bool tina::BacktrackParser::element() {
	return false;
}

bool tina::BacktrackParser::match(int index) {
	if (type(1) == index) {
		consume();
		return true;
	}
	return false;
}

void tina::BacktrackParser::consume() {
	marker++;
	if (marker == lookahead->size() && !(markers->size() > 0)) {
		marker = 0;
		lookahead->clear();

	}
	sync(1);
}

/*
	回溯时，记录解析的中间结果
	如果解析失败，则要记录下来
	如果解析成功，则下次在这对同一个规则进行解析可以跳过
	要记录该跳到哪里
*/
void tina::BacktrackParser::memorize(std::map<int, int>* memorization, int startTokenIndex, bool failed) {
	// 如果成功，就记录规则末尾的词法单元的下一个位置
	int stopTokenIndex = failed ? FAILED : marker;
	memorization->insert(std::make_pair(startTokenIndex, stopTokenIndex));
}

/*
	在当前位置上解析过这个规则吗？
	如果查不到相关记录，那么没解析过。
	如果返回值是FAILED，那么上次解析失败。
	如果返回值大于等于0，这是词法单元缓冲区的下标，表示上次解析成功。
	方法有副作用： 如果不用重新解析，则它会自动将缓冲区的下标向前移，以避免重新解析
*/
bool tina::BacktrackParser::alreadyParsedRule(std::map<int, int>* memorization) {
	int memoi = memorization->at(marker);
	if (memoi == NULL)return false;
	std::cout << "parser list before at index "
		<< marker
		<< "; skip ahead to token index "
		<< memoi << ": "
		<< lookahead->at(memoi)->value
		<< std::endl;
	if (memoi == FAILED)
		return false;
	marker = memoi;
	return true;
}
#include "BacktrackParser.h"

tina::BacktrackParser::BacktrackParser(tina::BacktrackLexer *lex) :lexer(lex) {
	lookahead = new std::vector<Token*>();
	markers = new std::vector<int>();
	sync(1);
}

void tina::BacktrackParser::stat() {
	if (speculate_stat_alt1()) {
		list();
		match(BacktrackLexer::EOF_TYPE);
	}
	else if (speculate_stat_alt2()) {
		assign();
		match(BacktrackLexer::EOF_TYPE);
		std::cout << "speculate alt2 successful" << std::endl;
	}
	else {
		std::cout << "no viable alt exception" << std::endl;
	}
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

bool tina::BacktrackParser::speculate_stat_alt1() {
	std::cout << "attempt alternative 1" << std::endl;
	bool success = true;
	mark();
	success &= list();
	success &= match(BacktrackLexer::EOF_TYPE);
	release();
	return success;
}

bool tina::BacktrackParser::speculate_stat_alt2() {
	std::cout << "attempt alternative 2" << std::endl;
	bool success = true;
	mark();
	success &= assign();
	success &= match(BacktrackLexer::EOF_TYPE);
	release();
	return success;
}

bool tina::BacktrackParser::assign() {
	return list()&&match(BacktrackLexer::EQUALS)&&list();
}
/*
	��¼��������
*/
bool tina::BacktrackParser::list() {
	bool isSuccess = true;
	int startTokenIndex = marker;
	if (markers->size() > 0 && alreadyParsedRule(listMemo))
		isSuccess = true;
	if(!_list()){
		isSuccess = false;
	}
	if (markers->size() > 0)
		memorize(listMemo, startTokenIndex, !isSuccess);
	return isSuccess;
}


bool tina::BacktrackParser::_list() {
	std::cout << "parse list rule at token index: "<<marker<< std::endl;
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
	if (type(1) == BacktrackLexer::NAME&&type(2) == BacktrackLexer::EQUALS) {
		return match(BacktrackLexer::NAME) && match(BacktrackLexer::EQUALS) && match(BacktrackLexer::NAME);
	}
	else if (type(1) == BacktrackLexer::NAME)
		return match(BacktrackLexer::NAME);
	else if (type(1) == BacktrackLexer::LBRACK)
		return list();
	else
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
		listMemo->clear();
	}
	sync(1);
}

/*
	����ʱ����¼�������м���
	�������ʧ�ܣ���Ҫ��¼����
	��������ɹ������´������ͬһ��������н�����������
	Ҫ��¼����������
*/
void tina::BacktrackParser::memorize(std::map<int, int>* memorization, int startTokenIndex, bool failed) {
	// ����ɹ����ͼ�¼����ĩβ�Ĵʷ���Ԫ����һ��λ��
	int stopTokenIndex = failed ? FAILED : marker;
	memorization->insert(std::make_pair(startTokenIndex, stopTokenIndex));
}

/*
	�ڵ�ǰλ���Ͻ��������������
	����鲻����ؼ�¼����ôû��������
	�������ֵ��FAILED����ô�ϴν���ʧ�ܡ�
	�������ֵ���ڵ���0�����Ǵʷ���Ԫ���������±꣬��ʾ�ϴν����ɹ���
	�����и����ã� ����������½������������Զ������������±���ǰ�ƣ��Ա������½���
*/
bool tina::BacktrackParser::alreadyParsedRule(std::map<int, int>* memorization) {
	std::map<int, int> mmap = *memorization;
	int memoi = mmap[marker];
	if (memoi == NULL)return false;
	std::cout << "parser list before at index "
		<< marker
		<< ";\n skip ahead to token index "
		<< memoi << ": "
		<< lookahead->at(memoi)->value
		<< std::endl;
	if (memoi == FAILED)
		return false;
	marker = memoi;
	return true;
}
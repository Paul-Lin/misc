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
	��¼��������
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
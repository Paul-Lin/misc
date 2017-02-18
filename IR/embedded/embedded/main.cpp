#include "hetero.h"
#include "node.h"

tina::IntNode* I(int i) {
	return new tina::IntNode(new tina::Token(tina::Token::INT, std::string(""+i)));
}

int main() {
	std::vector<tina::StatNode*> *stats = new std::vector<tina::StatNode*>();
	tina::AddNode *a = new tina::AddNode(new tina::IntNode(new tina::Token(tina::Token::INT, "3")), new tina::Token(tina::Token::PLUS), new tina::IntNode(new tina::Token(tina::Token::INT, "4")));
	tina::VarNode *x = new tina::VarNode(new tina::Token(tina::Token::ID, "x"));
	tina::AssignNode *assign = new tina::AssignNode(x, new tina::Token(tina::Token::ASSIGN, "="), a);
	stats->push_back(assign);
	for (tina::StatNode* stat : *stats) {
		stat->print();
	}
	system("pause");
}
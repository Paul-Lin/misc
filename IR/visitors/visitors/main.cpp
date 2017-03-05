#include "visitors.h"

int main() {
	tina::AddNode *a=new tina::AddNode(
		new tina::IntNode(new tina::Token(tina::Token::INT, new std::string("3"))),
		new tina::Token(tina::Token::PLUS), 
		new tina::IntNode(new tina::Token(tina::Token::INT,new std::string("3"))));
	tina::VarNode *x = new tina::VarNode(
		new tina::Token(tina::Token::ID, new std::string("x"))
	);
	tina::AssignNode *assign = new tina::AssignNode(x, new tina::Token(tina::Token::ASSIGN, new std::string("=")), a);
	tina::VecMathVisitor *visitor = new tina::VecMathVisitor();
	visitor->visit(assign);
	system("pause");
}
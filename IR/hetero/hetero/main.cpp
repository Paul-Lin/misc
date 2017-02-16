#include "node.h"

int main() {
	tina::Token* plus = new tina::Token(tina::Token::PLUS, "+");
	tina::Token* one = new tina::Token(tina::Token::INT, "1");
	tina::Token* two = new tina::Token(tina::Token::INT, "2");
	tina::ExprNode* root = new tina::AddNode(new tina::IntNode(one), plus, new tina::IntNode(two));
	std::cout << root->toStringTree() << std::endl;
	system("pause");
}
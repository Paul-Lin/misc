#include "ast.h"
#include "node.h"
#include <stdlib.h>

void func() {
	tina::Ast* root = new tina::Ast(new tina::Token(tina::Token::PLUS, "+"));
	tina::Ast* left = new tina::Ast(new tina::Token(tina::Token::INT, "1"));
	tina::Ast* right = new tina::Ast(new tina::Token(tina::Token::INT, "2"));
	root->addChild(left);
	root->addChild(right);
	std::cout << root->toStringTree() << std::endl;
}

void func1() {
	tina::Token* plus = new tina::Token(tina::Token::PLUS, "+");
	tina::Token* one = new tina::Token(tina::Token::INT, "1");
	tina::Token* two = new tina::Token(tina::Token::INT, "2");
	tina::ExprNode* root = new tina::AddNode(new tina::IntNode(one), plus, new tina::IntNode(two));
	std::cout << root->toStringTree() << std::endl;
}

int main() {
	func1();
	system("pause");
}
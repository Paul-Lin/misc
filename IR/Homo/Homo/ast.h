#ifndef _AST_H
#define _AST_H
#include <iostream>
#include <vector>
#include <string>

namespace tina {
	struct Token {
		const static int INVALID_TOKEN_TYPE = 0;
		const static int PLUS = 1;
		const static int INT = 2;

		int type;
		std::string text;
		Token(int ty,std::string txt):type(ty),text(txt){}
		Token(int ty):type(ty){}
		std::string toString();
	};
	class Ast {
	public:
		Ast(){}
		Ast(Token* to):token(to){ }
		Ast(int tokenType):token(new Token(tokenType)){}
		int getNodeType();
		void addChild(Ast* t);
		bool isNil();
		std::string toString();
		std::string toStringTree();
	private:
		Token *token;
		std::vector<Ast*> *children;
	};
}
#endif // !_AST_H


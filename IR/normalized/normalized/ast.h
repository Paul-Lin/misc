#ifndef _AST_H
#define _AST_H
#include <iostream>
#include <vector>
#include <string>

namespace tina {
	struct Token {
		const static int INVALID_TYPE = -1;
		const static int PLUS = 1;
		const static int INT = 2;
		Token(){}
		Token(const int ty,const std::string& txt):type(ty),text(txt){}
		int type;
		std::string text;
		std::string toString() { return text; }
	};
	class Ast {
	public:
		Ast();
		Ast(Token* token);
		virtual std::string toString();
		std::string toStringTree();
		bool isNil();
		void addChild(Ast *ast);
	protected:
		Token* token;
		std::vector<Ast*> *list;
	};
}
#endif 

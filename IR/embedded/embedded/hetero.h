#ifndef _HETERO_H
#define _HETERO_H
#include <iostream>
#include <vector>
#include <string>

namespace tina {
	struct Token {
		const static int PLUS = 0;
		const static int INT = 1;
		const static int ID = 2;
		const static int ASSIGN = 3;
		int type;
		std::string text;
		Token(int t):type(t){}
		Token(const int t,const std::string& str):type(t),text(str){}
		std::string toString() { return text; }
	};
	class HeteroAst {
	public:
		Token* token;
		HeteroAst():token(nullptr){}
		HeteroAst(Token* tk):token(tk){}
		~HeteroAst() { delete token; }
		std::string toString() { return token->toString(); }
	};
}
#endif // !_HETERO_H

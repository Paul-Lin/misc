#ifndef _HETERO_H
#define _HETERO_H
#include <iostream>
#include <vector>
#include <string>

namespace tina {
	struct Token {
		int type;
		std::string text;
		Token(int t):type(t){}
		Token(int t,std::string& str):type(t),text(str){}
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
